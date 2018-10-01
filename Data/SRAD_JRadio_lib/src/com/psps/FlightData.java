package com.psps;

/**
 * Created by Samuel Hild on 10/1/2018.
 * A data structure for flight data.
 */
public class FlightData {
    private String dataType = "[null]";
    private short value = 0;         //all values are stored in shorts for compatibility.
    private String units = "[null";

    //getter and setter functions by java standards
    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public short getValue() {
        return value;
    }

    public void setValue(short value) {
        this.value = value;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
}
