package com.psps;

public enum FormatterType{
    FLIGHT(0),ERROR(-1);

    private final int packetCode;

    FormatterType(int code){
        packetCode = code;
    }

}
