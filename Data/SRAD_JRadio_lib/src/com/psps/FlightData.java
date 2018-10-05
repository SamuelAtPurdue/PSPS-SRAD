package com.psps;

/**
 * Created by Samuel Hild on 10/1/2018.
 * A data structure for flight data.
 * TODO Refactor
 */
class FlightData {
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

    //Object Overrides
    @Override
    public String toString(){
        return String.format("%s: %d %s",datatype,value,units);
    }
    @Override
    public boolean equals(Object comparevalue){
        return (this.toString().equals(comparevalue.toString()));
    }

    //accessors
    String getType() {
        return datatype;
    }

    short getValue() {
        return value;
    }

    String getUnits() {
        return units;
    }
}
