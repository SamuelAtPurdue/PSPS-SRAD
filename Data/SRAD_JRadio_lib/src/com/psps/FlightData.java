package com.psps;

/**
 * Created by Samuel Hild on 10/1/2018.
 * A data structure for flight data.
 */
public class FlightData {
    private String datatype = "[null]";
    private short value = 0;         //all values are stored in shorts for compatibility.
    private String units = "[null";
    private final int index;

    //Constructor MUST have an index or it will have no data type
    public FlightData(int index, short value) throws InvalidDataTypeException{
        this.value = value;
        this.index = index;
        this.datatype = DataSequenceLookup.lookupFlightDataType(index).getTypeName();
        this.units = DataSequenceLookup.lookupFlightDataType(index).getUnits();
    }
    //overloaded constructor
    public FlightData(int index) throws InvalidDataTypeException{
        this(index,(short) 0);
    }

    //accessors
    String getDatatype() {
        return datatype;
    }

    void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    short getValue() {
        return value;
    }

    void setValue(short value) {
        this.value = value;
    }

    String getUnits() {
        return units;
    }

    void setUnits(String units) {
        this.units = units;
    }
}
