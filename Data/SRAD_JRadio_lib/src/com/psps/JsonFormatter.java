package com.psps;

/**
 * Created by Samuel Hild on 10/1/2018.
 * This class will not be instantiated outside the package.
 * any instance should be acquired through the DataFormatter() Factory class.
 * Tested on: 10/5/18
 */
class JsonFormatter implements Formatter {

    private JSON json = new JSON();

    /**
     * Packs the data to a json format from a internal data structure.
     * @param dataIn Data in the form of either a FlightData or StatusData data structure
     * @throws InvalidDataTypeException the data structure supplied cannot be parsed to json
     */
    @Override
    public void pack(Object... dataIn) throws InvalidDataTypeException{
        if (dataIn instanceof GenericData[])
            for (GenericData data : (GenericData[]) dataIn)
                json.addValue(data.getType(),data.getValue());
        else
            throw new InvalidDataTypeException("could not parse to json: data not an instance of GenericData");
    }

    /**
     * @return String formatted json file
     */
    @Override
    public String getFormattedData() {
        return json.toString();
    }

    @Override
    public void clear(){
        json = new JSON();
    }

    @Override
    public Object[] getFormattedDataStructure() {
        return null;//json;
    }
}
