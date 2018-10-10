package com.psps;

/**
 * Created by Samuel Hild on 10/2/2018.
 * data structure class for status data.
 */
class StatusData implements GenericData{
    private String type;
    private boolean value;

    public StatusData(String type, boolean value){
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString(){
        return String.format("\"%s\":\"%b\"",type,value);
    }
    @Override
    public boolean equals(Object compare){
        return (this.toString().equals(compare.toString()));
    }

    public String getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }
}
