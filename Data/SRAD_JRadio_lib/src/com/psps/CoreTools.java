package com.psps;

/**
 * Created by Samuel Hild on 10/2/2018.
 */
public class CoreTools {
    private static boolean verbose = true;

    public static void fatal(String mess){
        System.err.printf("[!!] fatal err: %s%n");
        System.exit(-1);
    }
    public static void conditionalPrint(String format, String... messages){
        if (verbose)
            System.out.printf(format, messages);
    }
    void setVerbose(boolean verbose){
        this.verbose = verbose;
    }
}
