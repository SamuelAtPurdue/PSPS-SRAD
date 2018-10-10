package com.psps;

/**
 * Created by Samuel Hild on 10/1/2018.
 * A data structure for flight data.
 * TODO Refactor
 */
class FlightData {
    private String datatype;
    private float value;         //all values are stored in shorts for compatibility.

    //Constructor MUST have an index or it will have no data type
    public FlightData(String datatype, float value) {
        this.value = value;
        this.datatype = datatype;
    }

    //Object Overrides
    @Override
    public String toString() {
        return String.format("%s: %f", datatype, value);
    }

    @Override
    public boolean equals(Object compareValue) {
        return (this.toString().equals(compareValue.toString()));
    }

    //accessors
    String getType() {
        return datatype;
    }

    float getValue() {
        return value;
    }
}
