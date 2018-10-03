package com.psps;

import static com.psps.CoreTools.*;
/**
 * Created by Samuel Hild on 10/3/2018.
 */
public class CommandLineInterface {

    private static final String VERSION = ".1";

    private enum Commands{
        help,
        kill,
        connect,
        list,
        cstatus,
        tflight,
        tstatus,
        terror,
        verbose,
        send
    }private enum AbbreviatedCommands {
        h,
        k,
        c,
        l,
        cs,
        tf,
        ts,
        te,
        v,
        s
    }
    public static void main(String[] args){
        if (args.length == 0) {
            System.out.printf("no arguments provided%n");
            usage();
        }
        if (!isValidCommand(args[0])) {
            System.out.printf("invalid command: %s%n", args[0]);
            usage();
        }
        execute(args);

    }
    private static void usage(){
        String usagemess = String.format(
                "Radio CLI%n" +
                "USAGE: java CommandLineInterface.jar [OPTIONS] [ARGS...]%n" +
                "OPTIONS:%n" +
                        "\t-h\t\t--help\t\t\t\t\t\t\t\t\tdisplay this usage message%n" +
                        "\t-k\t\t--kill\t\t[connection]\t\t\t\tkill a connections%n" +
                        "\t-c\t\t--connect\t[connection]\t\t\t\tstart a connection%n" +
                        "\t-l\t\t--list\t\t\t\t\t\t\t\t\tlist open connections%n" +
                        "\t-cs\t\t--cstatus\t[connection]\t\t\t\treturn status of a connection%n" +
                        "\t-tf\t\t--tflight\t[connection]\t\t\t\tget a test flight data%n" +
                        "\t-ts\t\t--tstatus\t[connection]\t\t\t\tget a test status data%n" +
                        "\t-te\t\t--terror\t[connection]\t\t\t\tget a test error report%n" +
                        "\t-v\t\t--verbose\t\t\t\t\t\t\t\tverbose logging%n"+
                        "\t-s\t\t--send\t\t[command] [connection]\t\tsend a command to the rocket%n"+
                "VERSION:%n\tRadio CLI\t%s%n",VERSION);
        System.out.printf(usagemess);
        System.exit(0);
    }
    private static boolean isValidCommand(String command){
        //check if inputted command is valid
        for (Commands com : Commands.values())
            if (command.equals("--"+com.name()))
                return true;

        for (AbbreviatedCommands com : AbbreviatedCommands.values())
            if (command.equals("-"+com.name()))
                return true;

        return false;
    }
    private static void execute(String[] args){
        conditionalPrint("[@@] debug: executing command: %s%n", args[0]);
    }


}
