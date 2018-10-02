package com.psps;

/**
 * Created by Samuel Hild on 10/2/2018.
 */
public class StatusData {
    private String datatype = "[null]";
    private boolean value = false;
    private int index;

    private void constructStatusData(int index, boolean value) throws InvalidDataTypeException{
        this.index = index;
        this.value = value;
        this.datatype = DataSequenceLookup.lookupStatusDataType(index);
    }
    public StatusData(int index, int boolvalue) throws InvalidDataTypeException{
        if (boolvalue == 1)
            constructStatusData(index, true);
        if (boolvalue == 0)
            constructStatusData(index, false);
        else
            throw new InvalidDataTypeException("malformed status data: binary digit or boolean required");
    }
    public StatusData(int index, boolean boolvalue) throws InvalidDataTypeException{
        constructStatusData(index, boolvalue);
    }

}
