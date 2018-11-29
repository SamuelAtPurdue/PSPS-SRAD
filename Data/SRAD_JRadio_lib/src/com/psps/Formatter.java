package com.psps;

/**
 * Interface: Formatter
 * The Formatter interface is implemented by all Formatter Objects.
 * Formatter Objects take in raw data, format them to JSON which is then stored in the Formatter.
 * This data can be retrieved by key-value pair or as one whole string.
 */
public interface Formatter {

    /**
     * Unpacks the raw data to the Formatter Object.
     * @param rawData unformatted data to unpack
     */
    void unpack(char []rawData);

    /** (FUTURE VERSION)
     * Packs the data to the formatter (kept for future versions, implement empty)
     * @param formattedData Formatted JSON data as a String
     */
    void pack(String formattedData);

    /**
     * Clears all data from the formatter
     */
    void clear();

    /**
     * gets a value by the key String
     * @param key String to decide which value to return
     * @return value returned by a particular key
     */
    Object getValue(String key);

    /**
     * get the formatted data
     * @return data formatted as a String
     */
    String getFormattedData();

    /** (FUTURE VERSION)
     * get the unformatted data
     * @return unformatted data as a char array
     */
    char []getUnformattedData();
}
