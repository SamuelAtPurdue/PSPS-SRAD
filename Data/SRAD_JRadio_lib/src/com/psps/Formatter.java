package com.psps;

/**
 * Created by Samuel Hild on 9/29/2018.
 */
public interface Formatter {

    Formatter form(Object... data);

    Object[] unform(Formatter formatter);



    /**
     * Simply dumps data to command line for Debugging
     * requires override of toString() for specific implementation
     * @param dumpData data to be dumped to
     */
    static void dump(Formatter dumpData){
        System.out.println(dumpData);
    }
}
