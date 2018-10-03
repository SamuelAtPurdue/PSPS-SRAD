package com.psps;

/**
 * Created by Samuel Hild on 10/2/2018.
 * A simple Data Structure for internal use only.
 */
class DataType {
    private String typename;
    private String units;

    DataType(String typename, String units){
        this.typename = typename;
        this.units = units;
    }

    //accessors
    public String getTypeName() {
        return typename;
    }

    public void setTypeName(String typeName) {
        this.typename = typeName;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }
}
