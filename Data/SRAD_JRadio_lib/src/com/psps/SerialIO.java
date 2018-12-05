package com.psps;

import com.fazecast.jSerialComm.*;

import java.io.IOException;
import java.io.InputStream;

public final class SerialIO implements RadioIO {

    private SerialPort serialPort;
    private InputStream ioStream;

    private final int END_CHAR = 10;
    private final int NULL_CHAR = 0;

    public SerialIO(String port, int baudrate) {
        serialPort = SerialPort.getCommPort(port);
        serialPort.setBaudRate(baudrate);
    }

    @Override
    public void open() throws RadioIOException{
        try {
            serialPort.openPort();
            serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);
            ioStream = serialPort.getInputStream();
        }catch(Exception e){
            throw new RadioIOException(e.getMessage());
        }
    }

    @Override
    public void close() throws RadioIOException {
        try {
            serialPort.closePort();
            ioStream = serialPort.getInputStream();
        } catch (Exception e){
            throw new RadioIOException(e.getMessage());
        }
    }

    @Override
    public String readLine() {
        StringBuffer buffer = new StringBuffer();
        try {
            waitForNext();

            int charBuffer;

            do {
                charBuffer = ioStream.read();
                buffer.append((char) charBuffer);
            }while (charBuffer != END_CHAR);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    @Override
    public char read(){
        return 0x00;
    }

    private void waitForNext() throws Exception {
        try {
            while (ioStream.available() == NULL_CHAR)
                Thread.sleep(20);
        }catch (IOException ioexception){
            throw new Exception("err: failed to get available data.");
        }
    }

    @Override
    public void write(String writedata) {
        System.err.println("[??] warning: method com.psps.SerialIO.write() not yet implemented.");
        System.err.println("[??] method reserved for future versions and does not yet have functionality.");
    }
}
