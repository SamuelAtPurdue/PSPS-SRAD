package com.psps;

/**
 * Created by Samuel Hild on 10/2/2018.
 */
class InvalidDataTypeException extends Exception {
    String mess;
    public InvalidDataTypeException(String mess){
        this.mess = mess;
    }
    @Override
    public String getMessage(){
        return String.format("[!!] err: %s%n", mess);
    }
}
