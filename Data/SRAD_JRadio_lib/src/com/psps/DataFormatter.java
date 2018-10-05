package com.psps;

/**
 * Created by Samuel Hild on 10/4/2018.
 * A static factory class for the Formatter subclasses.
 */
public class DataFormatter{
    private DataFormatter(){}

    /**
     * @return new JsonFormatter Instance.
     */
    public static JsonFormatter getJsonFormatter(){
        return new JsonFormatter();
    }

    /**
     * @return new ErrorDataFormatter Instance
     */
    public static ErrorDataFormatter getErrorDataFormatter(){return new ErrorDataFormatter();}

    /**
     * @return new FlightDataFormatter Instance
     */
    public static FlightDataFormatter getFlightDataFormatter(){return new FlightDataFormatter();}

    /**
     * @return new CommandDataFormatter Instance
     */
    public static CommandDataFormatter getCommandDataFormatter(){return new CommandDataFormatter();}

    /**
     * @return new StatusDataFormatter Instance
     */
    public static StatusDataFormatter getStatusDataFormatter(){return new StatusDataFormatter();}
}
