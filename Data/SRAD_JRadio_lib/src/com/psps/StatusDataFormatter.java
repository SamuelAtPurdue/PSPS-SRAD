package com.psps;

import java.util.ArrayList;

/**
 * Created by Samuel Hild on 10/4/2018.
 */
class StatusDataFormatter implements Formatter {

    private String formattedData = "";
    private ArrayList<StatusData> formattedDataStructure = new ArrayList<>();

    @Override
    public void pack(Object... dataIn) throws InvalidDataTypeException {
        if (dataIn instanceof Integer[])
             for (int i = 0; i < dataIn.length; i++)
                 formattedDataStructure.add(parseStatus(i, (Integer) dataIn[i]));
        else
            throw new InvalidDataTypeException("invalid status data: data not instanceof Integer()");
    }

    private StatusData parseStatus(int index, Integer data) throws InvalidDataTypeException{
        if (data > 1 || data < 0)
            throw new InvalidDataTypeException ("Integer not formatted as boolean value");

        String typename = DataSequenceLookup.lookupStatusDataType(index);

        return new StatusData(typename, (data == 1));
    }

    @Override
    public Object[] unpack() {
        return new Object[0];       //TODO implement
    }

    @Override
    public Object[] getFormattedDataStructure() {
        StatusData[] out = new StatusData[formattedDataStructure.size()];
        return formattedDataStructure.toArray(out);
    }

    @Override
    public String getFormattedData() {
        formattedData = formattedDataStructure.toString();
        return formattedData;
    }

    @Override
    public void clear() {
        formattedData = "";
        formattedDataStructure = new ArrayList<>();
    }
}
