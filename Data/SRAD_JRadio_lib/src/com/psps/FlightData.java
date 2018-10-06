package com.psps;

/**
 * Created by Samuel Hild on 10/1/2018.
 * A data structure for flight data.
 * TODO Refactor
 */
class FlightData {
    private String datatype = "[null]";
    private float value = 0;         //all values are stored in shorts for compatibility.
    private String units = "[null";

    //Constructor MUST have an index or it will have no data type
    public FlightData(String datatype, float value, String units){
        this.value = value;
        this.datatype = datatype;
        this.units = units;
    }

    //Object Overrides
    @Override
    public String toString(){
        return String.format("%s: %f %s", datatype, value, units);
    }
    @Override
    public boolean equals(Object compareValue){
        return (this.toString().equals(compareValue.toString()));
    }

    //accessors
    String getType() {
        return datatype;
    }

    float getValue() {
        return value;
    }

    String getUnits() {
        return units;
    }
}
