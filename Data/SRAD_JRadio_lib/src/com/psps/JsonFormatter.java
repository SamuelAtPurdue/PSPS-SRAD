package com.psps;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samuel Hild on 10/1/2018.
 * This class will not be instantiated outside the package.
 * any instance should be acquired through the DataFormatter() Factory class.
 * Tested on: 10/5/18
 */
class JsonFormatter implements Formatter {

    private static final String JSON_HEADER = "{";
    private static final String JSON_CLOSER = "}";

    private static final String IS_FLIGHT_DATA = "flight-data";
    private static final String IS_STATUS_DATA = "status-data";

    private String typedata;
    private String formattedData = "";

    /**
     * Packs the data to a json format from a internal data structure.
     * @param dataIn Data in the form of either a FlightData or StatusData data structure
     * @throws InvalidDataTypeException the data structure supplied cannot be parsed to json
     */
    @Override
    public void pack(Object... dataIn) throws InvalidDataTypeException{
        List<String> jsonvalues = new ArrayList<>();
        for (Object data : dataIn){
            jsonvalues.add(parseData(data));
        }
        this.formattedData += typedata + constructJson(jsonvalues);
    }

    //parses data based on the type of data supplied
    //if data type is not in a standardized form then an InvalidDataTypeException will be thrown in runtime
    private String parseData(Object data) throws InvalidDataTypeException{
        if (data instanceof FlightData) {
            typedata = IS_FLIGHT_DATA;                  // set typedata to match the data supplied to be easily appended later to the
            return parseFlightData((FlightData) data);  // data at this point in the program will ALWAYS be an instanceof FlightData as implied but it is still cast to make the compiler happy
        } if (data instanceof StatusData) {
            typedata = IS_STATUS_DATA;                  // set typedata
            return parseStatusData((StatusData) data);  // cast to instanceof StatusData to make the compiler happy
        } else
            throw new InvalidDataTypeException("could not parse data to JSON.");
    }

    //parses FlightData to a json value
    private String parseFlightData(FlightData data){
        String outdata = String.format("\"%s\":{\"value\" : %d, \"units\" : \"%s\"}",data.getType(), data.getValue(), data.getUnits());
        return outdata;
    }

    //parses StatusData to a json value
    private String parseStatusData(StatusData data){
        String outdata = String.format("\"%s\" : %b", data.getType(), data.getValue());
        return outdata;
    }

    //constructs the entire json file
    private String constructJson(List<String> jsonvalues){
        StringBuffer outputbuffer = new StringBuffer(JSON_HEADER);

        for (String value : jsonvalues)
                outputbuffer.append(value + ", ");      // WARNING: an extra comma is appended here to the end of the file
                                                        // this is handled below in endJson()
        return endJson(outputbuffer);
    }

    //appends an end to the json file
    private String endJson(StringBuffer inputJson){
        int indexOfLastComma = inputJson.lastIndexOf(",");      //This is the index of the ending comma, which is followed by an extra space, these are replaced by the end of the file.
        StringBuffer outputJson = inputJson.replace(indexOfLastComma, indexOfLastComma+1, JSON_CLOSER);

        return outputJson.toString();
    }

    @Override
    public String[] unpack() {
        return new String[0];
    }   //TODO implement

    /**
     * @return String formatted json file
     */
    @Override
    public String getFormattedData() {
        return formattedData;
    }
}
