package com.psps;

/**
 * Enum: FormatterType
 * Used to determine the type of formatter to construct from the factory
 *
 */

public final class FormatterFactory {

    /**
     * Factory Method to return an instance of Formatter
     * @param type An enum to describe the type of formatter to be used
     * @return The concrete instance of Formatter
     * @throws UnknownFormatterException Error is thrown if an unknown Formatter instance is requested.
     */
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
