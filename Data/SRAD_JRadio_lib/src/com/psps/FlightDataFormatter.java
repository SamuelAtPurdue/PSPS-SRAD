package com.psps;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samuel Hild on 10/4/2018.
 */
public class FlightDataFormatter implements Formatter{

    private String formattedData = "";
    private ArrayList<FlightData> formattedDataStructure = new ArrayList<>();



    @Override
    public void pack(Object... dataIn) throws InvalidDataTypeException {
        if (dataIn instanceof Float[])
            for (int i =0; i < dataIn.length; i++)
                formattedDataStructure.add(parseFloatToFlightData((float) dataIn[i], i));
        else
            throw new InvalidDataTypeException("flight data not formatted as 32b float");
    }
    private FlightData parseFloatToFlightData(float data, int index) throws InvalidDataTypeException{
        return new FlightData(index, data);
    }

    @Override
    public Object[] unpack() {
        return new Object[0];       //TODO implement
    }

    @Override
    public String getFormattedData() {
        return formattedData;
    }

    public FlightData[] getFormattedDataStructure() {
        FlightData[] output = new FlightData[formattedData.length()];
        return formattedDataStructure.toArray(output);
    }

    @Override
    public void clear(){
        formattedData = "";
        formattedDataStructure = new ArrayList<>();
    }
}
