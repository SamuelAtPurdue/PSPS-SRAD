package com.psps;

/**
 * Created by Samuel Hild on 9/29/2018.
 * Formatter interface is used to organize the Formatter classes.
 * These functions must be implemented but more functions could be added in the future.
 */
public interface Formatter {

    /**
     * packs incoming data to the Formatter instance.
     * This data is stored as the specified data structure.
     * @param dataIn Data in the form of a string or an array
     */
    void pack(Object... dataIn) throws InvalidDataTypeException;

    /**
     * Unpacks the object to unformatted data
     * @return unformatted data from the data structure stored in the class.
     */
    Object[] unpack();

    /**
     * gets the formatted data structure.
     * @return formatted data structure dependant on instance of the class.
     */
    String getFormattedData();

    /**
     * Clears the data from the formatter class
     */
    void clear();

    /**
     * @return formatted data structure array
     */
    Object[] getFormattedDataStructure();

    /**
     * Simply dumps data to command line for Debugging
     * requires override of toString() for specific implementation
     * @param dumpData data to be dumped to CLI
     */
    static void dump(Formatter dumpData){
        System.out.println(dumpData);
    }
}
