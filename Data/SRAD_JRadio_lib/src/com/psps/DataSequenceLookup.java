package com.psps;


/**
 * Created by Samuel Hild on 10/1/2018.
 */
class DataSequenceLookup {

    private static final DataType[] FLIGHT_DATA_SEQUENCE = new DataType[]{new DataType("Altitude", "m")};        //TODO read data from a text file
    private static final String[] STATUS_DATA_SEQUENCE = new String[]{"Altimeter"};

    static{
        //TODO see TODO below
    }

    /**
     * looks up the flight data type and units based on a predefined sequence of data.
     * @param index from the sequence of values received by the radio
     * @return returns the data type and units as a DataType()
     * @throws InvalidDataTypeException The data type is not in the FLIGHT_DATA_SEQUENCE
     */
    static DataType lookupFlightDataType(int index) throws InvalidDataTypeException{
        if (index < FLIGHT_DATA_SEQUENCE.length)
            return FLIGHT_DATA_SEQUENCE[index];

        throw new InvalidDataTypeException("flight data value not in lookup sequence");
    }

    /**
     * looks up the status data type based on a predefined sequence of data.
     * @param index from the sequence of values received by the radio
     * @return returns the data type as a String
     * @throws InvalidDataTypeException The data type is not in the STATUS_DATA_SEQUENCE
     */
    static String lookupStatusDataType(int index) throws InvalidDataTypeException{
        if (index < STATUS_DATA_SEQUENCE.length)
            return STATUS_DATA_SEQUENCE[index];

        throw new InvalidDataTypeException("status data value not in lookup sequence");
    }
    //terrifying, mortifying, petrifying, stupefying code below
    //TODO write
    private static void getSquenceFromFile(){

    }
}