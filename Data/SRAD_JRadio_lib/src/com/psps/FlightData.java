package com.psps;

/**
 * Created by Samuel Hild on 10/1/2018.
 * A data structure for flight data.
 */
class FlightData implements GenericData{
    private String type;
    private float value;

    //Constructor MUST have an index or it will have no data type
    public FlightData(String dataType, float value) {
        this.value = value;
        this.type = dataType;
    }

    @Override
    public String toString() {
        return String.format("\"%s\":%f", type, value);
    }

    @Override
    public boolean equals(Object compareValue) {
        return (this.toString().equals(compareValue.toString()));
    }

    public String getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }
}
