package com.psps;

/**
 * Created by Samuel Hild on 10/2/2018.
 */
class StatusData {
    private String datatype = "[null]";
    private boolean value = false;
    private final int index;

    private StatusData(int index) throws InvalidDataTypeException{
        this.index = index;
        this.datatype = DataSequenceLookup.lookupStatusDataType(index);
    }

    public StatusData(int index, int boolvalue) throws InvalidDataTypeException{
        this(index);

        if (boolvalue == 1)
            constructStatusData(index, true);
        if (boolvalue == 0)
            constructStatusData(index, false);
        else
            throw new InvalidDataTypeException("malformed status data: binary digit or boolean required");
    }

    public StatusData(int index, boolean boolvalue) throws InvalidDataTypeException{
        this(index);
        constructStatusData(index, boolvalue);
    }

    private void constructStatusData(int index, boolean value) throws InvalidDataTypeException{
        this.value = value;
    }
    //Object Overrides
    @Override
    public String toString(){
        return String.format("%s: %b",datatype,value);
    }
    @Override
    public boolean equals(Object compare){
        return (this.toString().equals(compare.toString()));
    }

    //accessors
    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public boolean isValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    public int getIndex() {
        return index;
    }
}
