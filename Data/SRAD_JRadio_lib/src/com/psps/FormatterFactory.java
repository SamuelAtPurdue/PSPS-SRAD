package com.psps;

enum FormatterType{
    FLIGHT,ERROR
}

public final class FormatterFactory {
    public FormatterFactory(){}

    public Formatter getFormatter(FormatterType type) throws UnknownFormatterException{
        switch(type){
            case FLIGHT:
                return new FlightFormatter();
            case ERROR:
                return new ErrorFormatter();
            default:
                throw new UnknownFormatterException("err: unknown formatter type: " + type.name());
        }
    }
}
