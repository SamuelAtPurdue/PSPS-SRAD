package com.psps;

/**
 * Created by Samuel Hild on 10/2/2018.
 * data structure class for status data.
 */
class StatusData {
    private String typename = "[null]";
    private boolean value = false;

    public StatusData(String typename, boolean value){
        this.typename = typename;
        this.value = value;
    }

    //Object Overrides
    @Override
    public String toString(){
        return String.format("%s: %b",typename,value);
    }
    @Override
    public boolean equals(Object compare){
        return (this.toString().equals(compare.toString()));
    }

    //accessors
    public String getType() {
        return typename;
    }

    public boolean getValue() {
        return value;
    }
}
