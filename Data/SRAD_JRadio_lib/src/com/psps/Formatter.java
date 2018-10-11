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
}
