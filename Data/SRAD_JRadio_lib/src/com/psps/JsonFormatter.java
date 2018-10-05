package com.psps;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samuel Hild on 10/1/2018.
 * This class will not be instantiated outside the package.
 * any instance should be acquired through the DataFormatter() class.
 */
class JsonFormatter implements Formatter {

    private static final String JSON_HEADER = "{";
    private static final String JSON_CLOSER = "}";

    private String formattedData = "[DATA NOT FOUND]";

    @Override
    public void pack(Object... dataIn) throws InvalidDataTypeException{
        List<String> jsonvalues = new ArrayList<>();
        for (Object data : dataIn){
            jsonvalues.add(parseData(data));
        }
        this.formattedData = constructJson(jsonvalues);
    }
    private String parseData(Object data) throws InvalidDataTypeException{
        if (data instanceof FlightData) {
            return parseFlightData((FlightData) data);
        } if (data instanceof StatusData) {
            return parseStatusData((StatusData) data);
        } else {
            throw new InvalidDataTypeException("could not parse data to JSON.");
        }
    }
    private String parseFlightData(FlightData data){
        String outdata = String.format("\"%s\":{\"value\" : %d, \"units\" : \"%s\"}",data.getType(), data.getValue(), data.getUnits());
        return outdata;
    }
    private String parseStatusData(StatusData data){
        String outdata = String.format("\"%s\" : %b", data.getType(), data.getValue());
        return outdata;
    }

    private String constructJson(List<String> jsonvalues){
        StringBuffer jsonoutput = new StringBuffer(JSON_HEADER);
        for (String value : jsonvalues) {
                jsonoutput.append(value + ", ");
        }
        String output = jsonoutput.replace(jsonoutput.lastIndexOf(","),jsonoutput.lastIndexOf(",")+1, JSON_CLOSER).toString();      //TODO make this less gross
        return output;
    }

    @Override
    public String[] unpack() {
        return new String[0];
    }

    @Override
    public String getFormattedData() {
        return formattedData;
    }
}
