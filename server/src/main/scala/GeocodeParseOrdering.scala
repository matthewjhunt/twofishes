//  Copyright 2012 Foursquare Labs Inc. All Rights Reserved
package com.foursquare.twofishes

import com.foursquare.twofishes.Identity._
import com.foursquare.twofishes.util.{GeoTools, StoredFeatureId, TwofishesLogger}
import com.foursquare.twofishes.util.Lists.Implicits._
import org.bson.types.ObjectId
import scala.collection.mutable.HashMap
import scalaj.collection.Implicits._

// Comparator for parses, we score by a number of different features
//

object GeocodeParseOrdering {
  type ScoringFunc = (CommonGeocodeRequestParams, Parse[Sorted], GeocodeServingFeature, Seq[FeatureMatch]) => Option[(Int, String)]
}

class GeocodeParseOrdering(
    store: GeocodeStorageReadService,
    req: CommonGeocodeRequestParams,
    logger: TwofishesLogger,
    extraScorers: List[GeocodeParseOrdering.ScoringFunc] = Nil
  ) extends Ordering[Parse[Sorted]] {
  var scoreMap = new scala.collection.mutable.HashMap[String, Int]

  // Higher is better
  def scoreParse(parse: Parse[Sorted]): Int = {
    parse.headOption.map(primaryFeatureMatch => {
      val primaryFeature = primaryFeatureMatch.fmatch
      val rest = parse.drop(1)
      var signal = primaryFeature.scoringFeatures.population

      def modifySignal(value: Int, debug: String) {
        if (req.debug > 0) {
          logger.ifDebug("%s: %s + %s = %s", debug, signal, value, signal + value)
          parse.debugInfo.foreach(_.addToScoreComponents(
            new DebugScoreComponent(debug, value)
          ))
        }
        signal += value
      }

      if (req.debug > 0) {
        logger.ifDebug("Scoring %s", parse)
      }

      // if we have a repeated feature, downweight this like crazy
      // so st petersburg, st petersburg works, but doesn't break new york, ny
      if (parse.hasDupeFeature) {
        modifySignal(-100000000, "downweighting dupe-feature parse")
      }

      if (primaryFeature.feature.geometry.bounds != null) {
        modifySignal(1000, "promoting feature with bounds")
      }

      if (req.woeHint.asScala.has(primaryFeature.feature.woeType)) {
        modifySignal(50000000,
          "woe hint matches %d".format(primaryFeature.feature.woeType.getValue))
      }

      // prefer a more aggressive parse ... bleh
      // this prefers "mt laurel" over the town of "laurel" in "mt" (montana)
      modifySignal(-5000 * parse.length, "parse length boost")

      // Matching country hint is good
      if (Option(req.cc).exists(_ == primaryFeature.feature.cc)) {
        modifySignal(10000, "country code match")
      }

      val attributes = Option(primaryFeature.feature.attributes)
      val scalerank = attributes.flatMap(a => Option(a.scalerank))
      scalerank.foreach(rank => {
        if (rank > 0) {
          // modifySignal((20 - rank) * 1000000, "exponential scale rank increase")
        }
      })

      def distancePenalty(ll: GeocodePoint) {
        val distance = if (primaryFeature.feature.geometry.bounds != null) {
          GeoTools.distanceFromPointToBounds(ll, primaryFeature.feature.geometry.bounds)
        } else {
          GeoTools.getDistance(ll.lat, ll.lng,
            primaryFeature.feature.geometry.center.lat,
            primaryFeature.feature.geometry.center.lng)
        }
        val distancePenalty = (distance.toInt / 100)
        if (distance < 5000) {
          modifySignal(200000, "5km distance BONUS for being %s meters away".format(distance))
        } else {
          modifySignal(-distancePenalty, "distance penalty for being %s meters away".format(distance))
        }
      }

      val llHint = Option(req.llHint)
      val boundsHint = Option(req.bounds)
      if (boundsHint.isDefined) {
        boundsHint.foreach(bounds => {
          // if you're in the bounds and the bounds are some small enough size
          // you get a uniform boost
          val bbox = GeoTools.boundingBoxToS2Rect(bounds)
          // distance in meters of the hypotenuse
          // if it's smaller than looking at 1/4 of new york state, then
          // boost everything in it by a lot
          val bboxContainsCenter =
            GeoTools.boundsContains(bounds, primaryFeature.feature.geometry.center)
          val bboxesIntersect =
            Option(primaryFeature.feature.geometry.bounds).map(fBounds =>
              GeoTools.boundsIntersect(bounds, fBounds)).getOrElse(false)

          if (bbox.lo().getEarthDistance(bbox.hi()) < 200 * 1000 &&
            (bboxContainsCenter || bboxesIntersect)) {
            modifySignal(200000, "200km bbox intersection BONUS")
          } else {
            // fall back to basic distance-from-center logic
            distancePenalty(GeoTools.S2LatLngToPoint(bbox.getCenter))
          }
        })
      } else if (llHint.isDefined) {
        // Penalize far-away things
        llHint.foreach(distancePenalty)
      }

      // manual boost added at indexing time
      if (primaryFeature.scoringFeatures.boost != 0) {
        modifySignal(primaryFeature.scoringFeatures.boost, "manual boost")
      }

      StoredFeatureId.fromLong(primaryFeature.longId).foreach(fid =>
        store.hotfixesBoosts.get(fid).foreach(boost =>
          modifySignal(boost, "hotfix boost"))
      )

      // as a terrible tie break, things in the US > elsewhere
      // meant primarily for zipcodes
      if (primaryFeature.feature.cc == "US") {
        modifySignal(1, "US tie-break")
      }

      // no one likes counties
      if (primaryFeature.feature.cc == "US" && primaryFeature.feature.woeType == YahooWoeType.ADMIN2) {
        modifySignal(-30000, "no one likes counties in the US")
      }

      // In autocomplete mode, prefer "tighter" interpretations
      // That is, prefer "<b>Rego Park</b>, <b>N</b>Y" to
      // <b>Rego Park</b>, NY, <b>N</b>aalagaaffeqatigiit
      //
      // getOrdering returns a smaller # for a smaller thing
      val parentTypes = rest.map(_.fmatch.feature.woeType).sortBy(YahooWoeTypes.getOrdering)
      if (parentTypes.nonEmpty) {
        // if (parentTypes(0) == YahooWoeType.ADMIN2 && req.autocomplete) {
        //   modifySignal( -20, "downweight county matches a lot in autocomplete mode")
        // } else {
        modifySignal( -1 * YahooWoeTypes.getOrdering(parentTypes(0)), "prefer smaller parent interpretation")
      }

      modifySignal( -1 * YahooWoeTypes.getOrdering(primaryFeature.feature.woeType), "prefer smaller interpretation")

      for {
        scorer <- extraScorers
        (value, debugStr) <- scorer(req, parse, primaryFeature, rest)
      } {
        modifySignal(value, debugStr)
      }

      if (req.debug > 0) {
        logger.ifDebug("final score %s", signal)
        parse.debugInfo.foreach(_.setFinalScore(signal))
      }
      signal
    }).getOrElse(0)
  }

  def getScore(p: Parse[Sorted]): Int = {
    val scoreKey = p.map(_.fmatch.longId).mkString(":")
    if (!scoreMap.contains(scoreKey)) {
      scoreMap(scoreKey) = scoreParse(p)
    }

    scoreMap.getOrElse(scoreKey, -1)
  }

  def compare(a: Parse[Sorted], b: Parse[Sorted]): Int = {
    // logger.ifDebug("Scoring %s vs %s".format(printDebugParse(a), printDebugParse(b)))

    for {
      aFeature <- a.headOption
      bFeature <- b.headOption
    } {
      if (aFeature.tokenStart == bFeature.tokenStart &&
          aFeature.tokenEnd == bFeature.tokenEnd &&
          aFeature.fmatch.feature.woeType != YahooWoeType.COUNTRY &&
          bFeature.fmatch.feature.woeType != YahooWoeType.COUNTRY &&
          // if we have a hint that we want one of the types, then let the
          // scoring happen naturally
          !req.woeHint.asScala.has(aFeature.fmatch.feature.woeType) &&
          !req.woeHint.asScala.has(bFeature.fmatch.feature.woeType)
        ) {
        // if a is a parent of b, prefer b
        if (aFeature.fmatch.scoringFeatures.parentIds.asScala.has(bFeature.fmatch.longId) &&
          (aFeature.fmatch.scoringFeatures.population * 1.0 / bFeature.fmatch.scoringFeatures.population) > 0.05
        ) {
          logger.ifDebug("Preferring %s because it's a child of %s", a, b)
          return -1
        }
        // if b is a parent of a, prefer a
        if (bFeature.fmatch.scoringFeatures.parentIds.asScala.has(aFeature.fmatch.longId) &&
           (bFeature.fmatch.scoringFeatures.population * 1.0 / aFeature.fmatch.scoringFeatures.population) > 0.05
          ) {
          logger.ifDebug("Preferring %s because it's a child of %s", a, b)
          return 1
        }
      }
    }

    val scoreA = getScore(a)
    val scoreB = getScore(b)
    if (scoreA == scoreB) {
      (a.headOption.map(_.fmatch.feature.ids.asScala.map(_.toString).hashCode).getOrElse(0).toLong -
        b.headOption.map(_.fmatch.feature.ids.asScala.map(_.toString).hashCode).getOrElse(0).toLong).signum
    } else {
      scoreB - scoreA
    }
  }
}
