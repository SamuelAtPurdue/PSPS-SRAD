package com.psps;

/**
 * Created by Samuel Hild on 10/4/2018.
 */
public class DataFormatter{
    private DataFormatter(){}

    /**
     * @return new JsonFormatter Instance.
     */
    public static JsonFormatter getJsonFormatter(){
        return new JsonFormatter();
    }
}
