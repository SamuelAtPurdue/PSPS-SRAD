package com.psps;

/**
 * Created by Samuel Hild on 10/2/2018.
 * A helper class with many common tools used across the program.
 */
public final class CoreTools {
    private static boolean verbose = true;

    /*
     * private default constructor so class cannot be instantiated
     */
    private CoreTools(){}

    /**
     * fatal is used to display fatal errors and exit the program
     * @param mess error message to display
     */
    public static void fatal(String mess){
        System.err.printf("[!!] fatal err: %s%n");
        System.exit(-1);
    }

    /**
     * prints messages if the verbose flag is selected.
     * @param format String Format to print.
     * @param messages Strings to add according to the string format.
     */
    public static void conditionalPrint(String format, String... messages){
        if (verbose)
            System.out.printf(format, messages);
    }

    /**
     * Sets the Verbose flag for the program
     * @param verbose verbose flag
     */
    void setVerbose(boolean verbose){
        this.verbose = verbose;
    }

    /**
     * Replaces Square Brackets with curly braces
     * @param in input string
     * @return newly formatted string
     */
    public static String replaceSquareWithCurly(String in){
        return in.replace("[","{").replace("]","}");
    }
}
