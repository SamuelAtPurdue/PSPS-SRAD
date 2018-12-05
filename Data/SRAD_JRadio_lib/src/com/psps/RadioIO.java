package com.psps;

/**
 * Interface: RadioIO
 * This interface is implemented by all the IO Objects for radio communication.
 */
public interface RadioIO {

    /**
     * Opens a connection to the source of data
     * @throws RadioIOException thrown if connection cannot be opened
     */
    void open() throws RadioIOException;

    /**
     * Closes a connection if the transmission is complete.
     * @throws RadioIOException thrown if the connection cannot be closed
     */
    void close() throws RadioIOException;

    /**
     * Reads data from the source of data as raw data formatted as a char array
     * @return raw data from the data source that will need to be formatted
     */
    char read();

    /**
     * Reads data from the source of data as raw data formatted as a String until a newline char is encountered
     * @return raw data from the data source that will need to be formatted
     */
    String readLine();

    /** (FUTURE VERSION)
     * Writes data to the radio. (Required for future versions)
     * @param rawData raw, unformatted data to write to the radio.
     */
    void write(String rawData);
}
