package com.psps;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samuel Hild on 10/1/2018.
 * DataSequenceLookup is for searching a predetermined data sequence to determine the type of data that has been down linked from the radio
 * Most methods are for internal use only and the class is package access only.
 * Latest Test: 10/2/18
 */
class DataSequenceLookup {

    //Constants
    private static final String FLIGHT_DATA_SEQUENCE_FILE = "flightdatasequence.csv";
    private static final String STATUS_DATA_SEQUENCE_FILE = "statusdatasequence.csv";

    //Incoming Sequences
    private static final String[] FLIGHT_DATA_SEQUENCE;
    private static final String[] STATUS_DATA_SEQUENCE;

    //Default Sequences
    private static final String[] DEFAULT_STATUS_SEQUENCE = new String[]{"signal"}; //TODO add Defaults for status once hardware is chosen

    private static final String[] DEFAULT_FLIGHT_SEQUENCE = new String[]{
            "Altitude (m)", "Pressure (kPa)", "Acceleration (m/s^2)", "Longitude", "Latitude",
            "Pitch Attitude", "Yaw Attitude", "Roll Attitude","Magnetic Heading","Velocity (m/s)","Rate of Climb (m/s)"
    };

    //static initializer sets initial values for FLIGHT_DATA_SEQUENCE and STATUS_DATA_SEQUENCE for future comparison
    static{
        FLIGHT_DATA_SEQUENCE = getSequenceFromFile(FLIGHT_DATA_SEQUENCE_FILE);
        STATUS_DATA_SEQUENCE = getSequenceFromFile(STATUS_DATA_SEQUENCE_FILE);
    }

    /**
     * looks up the flight data type and units based on a predefined sequence of data.
     * @param index from the sequence of values received by the radio
     * @return returns the data type and units as a DataType()
     * @throws InvalidDataTypeException The data type is not in the FLIGHT_DATA_SEQUENCE
     */
    static String lookupFlightDataType(int index) throws InvalidDataTypeException{
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

    //gets the flight data sequence from a file
    private static String[] getSequenceFromFile(String filename){
        BufferedReader reader = openConfigFile(filename);
        return readFile(reader);
    }

    private static String[] readFile(BufferedReader reader){
        try {
            List<String> buffer = new ArrayList<>();
            String nextLine;

            while ((nextLine = reader.readLine()) != null)
                buffer.add(nextLine);

            reader.close();

            String[] output = new String[buffer.size()];
            return buffer.toArray(output);
        }catch (IOException ioexcept){
            CoreTools.fatal(String.format("Failed to read from file%n%s%n", ioexcept));
            return null;    //will never be reached
        }
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
    private static void genStatusConfig() throws IOException{
            FileWriter writer = new FileWriter(STATUS_DATA_SEQUENCE_FILE) ;

            for (String member : DEFAULT_STATUS_SEQUENCE){
                writer.write(String.format("%s%n",member));
            }
            writer.close();
    }
    private static void genFlightConfig()throws IOException{
        FileWriter writer = new FileWriter(FLIGHT_DATA_SEQUENCE_FILE) ;
        for (String member : DEFAULT_FLIGHT_SEQUENCE){
            writer.write(String.format("%s%n", member));
        }
        writer.close();
    }
}