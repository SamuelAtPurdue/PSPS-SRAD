package com.psps;

/**
 * Created by Samuel Hild on 10/2/2018.
 */
public class CoreTools {
    public static void fatal(String mess){
        System.err.printf("[!!] fatal err: %s%n");
        System.exit(-1);
    }
}
