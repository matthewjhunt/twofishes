/**
 * Autogenerated by Thrift
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 */
package com.foursquare.twofishes;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.thrift.*;
import org.apache.thrift.async.*;
import org.apache.thrift.meta_data.*;
import org.apache.thrift.transport.*;
import org.apache.thrift.protocol.*;

// No additional import required for struct/union.

public class GeocodeResponse implements TBase<GeocodeResponse, GeocodeResponse._Fields>, java.io.Serializable, Cloneable {
  private static final TStruct STRUCT_DESC = new TStruct("GeocodeResponse");

  private static final TField INTERPRETATIONS_FIELD_DESC = new TField("interpretations", TType.LIST, (short)1);
  private static final TField DEBUG_LINES_FIELD_DESC = new TField("debugLines", TType.LIST, (short)2);
  private static final TField REQUEST_WKT_GEOMETRY_FIELD_DESC = new TField("requestWktGeometry", TType.STRING, (short)3);

  public List<GeocodeInterpretation> interpretations;
  public List<String> debugLines;
  public String requestWktGeometry;

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements TFieldIdEnum {
    INTERPRETATIONS((short)1, "interpretations"),
    DEBUG_LINES((short)2, "debugLines"),
    REQUEST_WKT_GEOMETRY((short)3, "requestWktGeometry");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // INTERPRETATIONS
          return INTERPRETATIONS;
        case 2: // DEBUG_LINES
          return DEBUG_LINES;
        case 3: // REQUEST_WKT_GEOMETRY
          return REQUEST_WKT_GEOMETRY;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments

  public static final Map<_Fields, FieldMetaData> metaDataMap;
  static {
    Map<_Fields, FieldMetaData> tmpMap = new EnumMap<_Fields, FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.INTERPRETATIONS, new FieldMetaData("interpretations", TFieldRequirementType.DEFAULT, 
        new ListMetaData(TType.LIST, 
            new StructMetaData(TType.STRUCT, GeocodeInterpretation.class))));
    tmpMap.put(_Fields.DEBUG_LINES, new FieldMetaData("debugLines", TFieldRequirementType.OPTIONAL, 
        new ListMetaData(TType.LIST, 
            new FieldValueMetaData(TType.STRING))));
    tmpMap.put(_Fields.REQUEST_WKT_GEOMETRY, new FieldMetaData("requestWktGeometry", TFieldRequirementType.OPTIONAL, 
        new FieldValueMetaData(TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    FieldMetaData.addStructMetaDataMap(GeocodeResponse.class, metaDataMap);
  }

  public GeocodeResponse() {
  }

  public GeocodeResponse(
    List<GeocodeInterpretation> interpretations)
  {
    this();
    this.interpretations = interpretations;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public GeocodeResponse(GeocodeResponse other) {
    if (other.isSetInterpretations()) {
      List<GeocodeInterpretation> __this__interpretations = new ArrayList<GeocodeInterpretation>();
      for (GeocodeInterpretation other_element : other.interpretations) {
        __this__interpretations.add(new GeocodeInterpretation(other_element));
      }
      this.interpretations = __this__interpretations;
    }
    if (other.isSetDebugLines()) {
      List<String> __this__debugLines = new ArrayList<String>();
      for (String other_element : other.debugLines) {
        __this__debugLines.add(other_element);
      }
      this.debugLines = __this__debugLines;
    }
    if (other.isSetRequestWktGeometry()) {
      this.requestWktGeometry = other.requestWktGeometry;
    }
  }

  public GeocodeResponse deepCopy() {
    return new GeocodeResponse(this);
  }

  @Override
  public void clear() {
    this.interpretations = null;
    this.debugLines = null;
    this.requestWktGeometry = null;
  }

  public int getInterpretationsSize() {
    return (this.interpretations == null) ? 0 : this.interpretations.size();
  }

  public java.util.Iterator<GeocodeInterpretation> getInterpretationsIterator() {
    return (this.interpretations == null) ? null : this.interpretations.iterator();
  }

  public void addToInterpretations(GeocodeInterpretation elem) {
    if (this.interpretations == null) {
      this.interpretations = new ArrayList<GeocodeInterpretation>();
    }
    this.interpretations.add(elem);
  }

  public List<GeocodeInterpretation> getInterpretations() {
    return this.interpretations;
  }

  public GeocodeResponse setInterpretations(List<GeocodeInterpretation> interpretations) {
    this.interpretations = interpretations;
    return this;
  }

  public void unsetInterpretations() {
    this.interpretations = null;
  }

  /** Returns true if field interpretations is set (has been asigned a value) and false otherwise */
  public boolean isSetInterpretations() {
    return this.interpretations != null;
  }

  public void setInterpretationsIsSet(boolean value) {
    if (!value) {
      this.interpretations = null;
    }
  }

  public int getDebugLinesSize() {
    return (this.debugLines == null) ? 0 : this.debugLines.size();
  }

  public java.util.Iterator<String> getDebugLinesIterator() {
    return (this.debugLines == null) ? null : this.debugLines.iterator();
  }

  public void addToDebugLines(String elem) {
    if (this.debugLines == null) {
      this.debugLines = new ArrayList<String>();
    }
    this.debugLines.add(elem);
  }

  public List<String> getDebugLines() {
    return this.debugLines;
  }

  public GeocodeResponse setDebugLines(List<String> debugLines) {
    this.debugLines = debugLines;
    return this;
  }

  public void unsetDebugLines() {
    this.debugLines = null;
  }

  /** Returns true if field debugLines is set (has been asigned a value) and false otherwise */
  public boolean isSetDebugLines() {
    return this.debugLines != null;
  }

  public void setDebugLinesIsSet(boolean value) {
    if (!value) {
      this.debugLines = null;
    }
  }

  public String getRequestWktGeometry() {
    return this.requestWktGeometry;
  }

  public GeocodeResponse setRequestWktGeometry(String requestWktGeometry) {
    this.requestWktGeometry = requestWktGeometry;
    return this;
  }

  public void unsetRequestWktGeometry() {
    this.requestWktGeometry = null;
  }

  /** Returns true if field requestWktGeometry is set (has been asigned a value) and false otherwise */
  public boolean isSetRequestWktGeometry() {
    return this.requestWktGeometry != null;
  }

  public void setRequestWktGeometryIsSet(boolean value) {
    if (!value) {
      this.requestWktGeometry = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case INTERPRETATIONS:
      if (value == null) {
        unsetInterpretations();
      } else {
        setInterpretations((List<GeocodeInterpretation>)value);
      }
      break;

    case DEBUG_LINES:
      if (value == null) {
        unsetDebugLines();
      } else {
        setDebugLines((List<String>)value);
      }
      break;

    case REQUEST_WKT_GEOMETRY:
      if (value == null) {
        unsetRequestWktGeometry();
      } else {
        setRequestWktGeometry((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case INTERPRETATIONS:
      return getInterpretations();

    case DEBUG_LINES:
      return getDebugLines();

    case REQUEST_WKT_GEOMETRY:
      return getRequestWktGeometry();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been asigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case INTERPRETATIONS:
      return isSetInterpretations();
    case DEBUG_LINES:
      return isSetDebugLines();
    case REQUEST_WKT_GEOMETRY:
      return isSetRequestWktGeometry();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof GeocodeResponse)
      return this.equals((GeocodeResponse)that);
    return false;
  }

  public boolean equals(GeocodeResponse that) {
    if (that == null)
      return false;

    boolean this_present_interpretations = true && this.isSetInterpretations();
    boolean that_present_interpretations = true && that.isSetInterpretations();
    if (this_present_interpretations || that_present_interpretations) {
      if (!(this_present_interpretations && that_present_interpretations))
        return false;
      if (!this.interpretations.equals(that.interpretations))
        return false;
    }

    boolean this_present_debugLines = true && this.isSetDebugLines();
    boolean that_present_debugLines = true && that.isSetDebugLines();
    if (this_present_debugLines || that_present_debugLines) {
      if (!(this_present_debugLines && that_present_debugLines))
        return false;
      if (!this.debugLines.equals(that.debugLines))
        return false;
    }

    boolean this_present_requestWktGeometry = true && this.isSetRequestWktGeometry();
    boolean that_present_requestWktGeometry = true && that.isSetRequestWktGeometry();
    if (this_present_requestWktGeometry || that_present_requestWktGeometry) {
      if (!(this_present_requestWktGeometry && that_present_requestWktGeometry))
        return false;
      if (!this.requestWktGeometry.equals(that.requestWktGeometry))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(GeocodeResponse other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    GeocodeResponse typedOther = (GeocodeResponse)other;

    lastComparison = Boolean.valueOf(isSetInterpretations()).compareTo(typedOther.isSetInterpretations());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetInterpretations()) {
      lastComparison = TBaseHelper.compareTo(this.interpretations, typedOther.interpretations);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDebugLines()).compareTo(typedOther.isSetDebugLines());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDebugLines()) {
      lastComparison = TBaseHelper.compareTo(this.debugLines, typedOther.debugLines);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetRequestWktGeometry()).compareTo(typedOther.isSetRequestWktGeometry());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetRequestWktGeometry()) {
      lastComparison = TBaseHelper.compareTo(this.requestWktGeometry, typedOther.requestWktGeometry);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(TProtocol iprot) throws TException {
    TField field;
    iprot.readStructBegin();
    while (true)
    {
      field = iprot.readFieldBegin();
      if (field.type == TType.STOP) { 
        break;
      }
      switch (field.id) {
        case 1: // INTERPRETATIONS
          if (field.type == TType.LIST) {
            {
              TList _list40 = iprot.readListBegin();
              this.interpretations = new ArrayList<GeocodeInterpretation>(_list40.size);
              for (int _i41 = 0; _i41 < _list40.size; ++_i41)
              {
                GeocodeInterpretation _elem42;
                _elem42 = new GeocodeInterpretation();
                _elem42.read(iprot);
                this.interpretations.add(_elem42);
              }
              iprot.readListEnd();
            }
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 2: // DEBUG_LINES
          if (field.type == TType.LIST) {
            {
              TList _list43 = iprot.readListBegin();
              this.debugLines = new ArrayList<String>(_list43.size);
              for (int _i44 = 0; _i44 < _list43.size; ++_i44)
              {
                String _elem45;
                _elem45 = iprot.readString();
                this.debugLines.add(_elem45);
              }
              iprot.readListEnd();
            }
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        case 3: // REQUEST_WKT_GEOMETRY
          if (field.type == TType.STRING) {
            this.requestWktGeometry = iprot.readString();
          } else { 
            TProtocolUtil.skip(iprot, field.type);
          }
          break;
        default:
          TProtocolUtil.skip(iprot, field.type);
      }
      iprot.readFieldEnd();
    }
    iprot.readStructEnd();

    // check for required fields of primitive type, which can't be checked in the validate method
    validate();
  }

  public void write(TProtocol oprot) throws TException {
    validate();

    oprot.writeStructBegin(STRUCT_DESC);
    if (this.interpretations != null) {
      oprot.writeFieldBegin(INTERPRETATIONS_FIELD_DESC);
      {
        oprot.writeListBegin(new TList(TType.STRUCT, this.interpretations.size()));
        for (GeocodeInterpretation _iter46 : this.interpretations)
        {
          _iter46.write(oprot);
        }
        oprot.writeListEnd();
      }
      oprot.writeFieldEnd();
    }
    if (this.debugLines != null) {
      if (isSetDebugLines()) {
        oprot.writeFieldBegin(DEBUG_LINES_FIELD_DESC);
        {
          oprot.writeListBegin(new TList(TType.STRING, this.debugLines.size()));
          for (String _iter47 : this.debugLines)
          {
            oprot.writeString(_iter47);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
    }
    if (this.requestWktGeometry != null) {
      if (isSetRequestWktGeometry()) {
        oprot.writeFieldBegin(REQUEST_WKT_GEOMETRY_FIELD_DESC);
        oprot.writeString(this.requestWktGeometry);
        oprot.writeFieldEnd();
      }
    }
    oprot.writeFieldStop();
    oprot.writeStructEnd();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("GeocodeResponse(");
    boolean first = true;

    sb.append("interpretations:");
    if (this.interpretations == null) {
      sb.append("null");
    } else {
      sb.append(this.interpretations);
    }
    first = false;
    if (isSetDebugLines()) {
      if (!first) sb.append(", ");
      sb.append("debugLines:");
      if (this.debugLines == null) {
        sb.append("null");
      } else {
        sb.append(this.debugLines);
      }
      first = false;
    }
    if (isSetRequestWktGeometry()) {
      if (!first) sb.append(", ");
      sb.append("requestWktGeometry:");
      if (this.requestWktGeometry == null) {
        sb.append("null");
      } else {
        sb.append(this.requestWktGeometry);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws TException {
    // check for required fields
  }

}

