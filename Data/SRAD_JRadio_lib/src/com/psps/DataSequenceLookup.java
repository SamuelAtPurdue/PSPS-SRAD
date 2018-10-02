package com.psps;


import java.io.*;

/**
 * Created by Samuel Hild on 10/1/2018.
 */
class DataSequenceLookup {

    private static final String FLIGHT_DATA_SEQUENCE_FILE = "../flightdatasequence.txt";
    private static final String STATUS_DATA_SEQUENCE_FILE = "../statusdatasequence.txt";

    private static final DataType[] FLIGHT_DATA_SEQUENCE;
    private static final String[] STATUS_DATA_SEQUENCE;

    //Defaults
    private static final String[] DEFAULT_STATUS_SEQUENCE = new String[]{};
    private static final DataType[] DEFAULT_FLIGHT_SEQUENCE = new DataType[]{};

    static{
        //TODO see TODO below
        FLIGHT_DATA_SEQUENCE = getFlightDataSequenceFromFile(FLIGHT_DATA_SEQUENCE_FILE);
        STATUS_DATA_SEQUENCE = getStatusDataSequenceFromFile(STATUS_DATA_SEQUENCE_FILE);
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
    private static DataType[] getFlightDataSequenceFromFile(String filename){
        BufferedReader reader = openConfigFile(filename);
        return readDataTypeFromFile(reader);
    }
    private static DataType[] readDataTypeFromFile(BufferedReader reader){
        try {
            StringBuffer rawinputtypes = new StringBuffer();
            StringBuffer rawinputunits = new StringBuffer();
            String nextline;
            while ((nextline = reader.readLine()) != null) {
                rawinputtypes.append(nextline.split(",")[0]+",");
                rawinputunits.append(nextline.split(",")[1]+",");
            }
            reader.close();
            String[] typesarray = rawinputtypes.toString().split(",");
            String[] unitsarray = rawinputunits.toString().split(",");
            DataType[] output = new DataType[typesarray.length-1];
            for (int i = 0; i <output.length; i++)
                output[i] = new DataType(typesarray[i],unitsarray[i]);
            return output;
        }catch (IOException ioexcept){
            CoreTools.fatal(String.format("Failed to read from file%n%s%n", ioexcept));
            return null;
        }
    }


    private static String[] getStatusDataSequenceFromFile(String filename){
        BufferedReader reader = openConfigFile(filename);
        return null;
    }
    private static BufferedReader openConfigFile(String filename, int attempt){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            return reader;
        }catch (FileNotFoundException filenotfound) {
            if (attempt < 3) {
                System.out.printf("File %s not found generating new config file. attempt %d%n", filename,attempt);
                genConfig(filename);
                return openConfigFile(filename, attempt+1);    //recursive call should work if the file is generated properly
            }else
                CoreTools.fatal("failed to read config file attemped 3 times");
            return null;
        }
    }
    private static BufferedReader openConfigFile(String filename){
        return openConfigFile(filename, 0);
    }

    //TODO the genConfig functions can be refactored to be more beautiful as some code is repeated
    private static void genConfig(String filename){
        try {
            if (filename.equals(FLIGHT_DATA_SEQUENCE_FILE))
                genFlightConfig();
            else if (filename.equals(STATUS_DATA_SEQUENCE_FILE))
                genStatusConfig();
            else
                CoreTools.fatal("Failed to select defaults for config file.");
        }catch(IOException ioexception){

        }
    }
    //TODO you will notice these two functions opperate in much the same way and can likely be refactored to reduce code duplication
    private static void genStatusConfig() throws IOException{
            FileWriter writer = new FileWriter(STATUS_DATA_SEQUENCE_FILE) ;

            for (String member : DEFAULT_STATUS_SEQUENCE){
                writer.write(member);
            }
            writer.close();
    }
    private static void genFlightConfig()throws IOException{
        FileWriter writer = new FileWriter(FLIGHT_DATA_SEQUENCE_FILE) ;

        for (DataType member : DEFAULT_FLIGHT_SEQUENCE){
            writer.write(String.format("%s,%s",member.getTypeName(),member.getUnits()));
        }
        writer.close();
    }
}