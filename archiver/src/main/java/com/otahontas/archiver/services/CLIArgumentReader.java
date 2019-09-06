package com.otahontas.archiver.services;

import com.otahontas.archiver.utils.Sorter;

/**
 * Class that reads cli arguments and passes them to service for handling
 * compression or extraction
 * */

public class CLIArgumentReader {


    /**
     * Parses given arguments and returns array with arguments to be 
     * passed to compressor service.
     *
     * Possible arguments:
     * -c for compressng or -e for extracting 
     * -h use huffman or -l use lzw
     * -p: run performance tests (must be run without other flags)
     * Checks first for performance test, then for normal usage and 
     * then for correct flag usage.
     *
     * If arguments can be parsed for normal usage, returns String array
     * with command, algoritmh, source file and output file in this order.
     *
     * Returns empty array if arguments cannot be parsed
     * @param args
     */

    public String[] parse(String[] args) {

        if (args.length == 1 && args[0].equals("-p")) {
            String[] parsed = {"-p"};
            return parsed;
        }

        if (args.length != 3) {
            printError("argument");
            printGuide();
            return new String[0];
        } 
        
        String flags = args[0];
        if (flags.length() != 3 || flags.charAt(0) != '-') {
            printError("flag");
            printGuide();
            return new String[0];
        }

        Sorter sorter = new Sorter();

        char[] flagsAsChars = sorter.sortCharArray(flags.toCharArray());

        char command = flagsAsChars[1];
        char algo = flagsAsChars[2];

        if (!((command == 'e' || command == 'c') && (algo == 'l' || algo == 'h'))) {
            printError("flagtype");
            printGuide();
            return new String[0];
        }

        String[] parsed = {Character.toString(command), 
                           Character.toString(algo), 
                           args[1], 
                           args[2]};
        return parsed;
    }

    /**
     * Prints program usage guide to stdout
     * */
    private void printGuide() {
        final String info = "A CLI tool to compress and uncompress wav files";
        final String usage =
            "Usage: java -jar archiver.jar [-opts] source dest\n"
            +"e.g. java -jar archiver.jar -lc orig.wav comp.wav\n\n"
            +"Define algoritmh and desired functionality with opts:\n"
            +"-l Use LZW compression \t -h Use Huffman compression\n"
            +"-c Compress file \t -e Exctract file\n\n"
            +"Optionally performance tests can be run with flag -p";

        System.out.println(info + "\n" + usage);
    }
    /**
     * Prints usage error to stdout based on error type 
     * @param error Error type
     */

    private void printError(String error) {
        final String argumentError = "Wrong number of arguments! Correct usage below:\n";
        final String flagError = "Incorrect usage of flags! Correct usage below:\n";  
        final String flagTypeError = "Incorrect flag, see correct usage below\n";  

        switch (error) {
            case "argument":    System.out.println(argumentError);
                                break;
            case "flag":        System.out.println(flagError);
                                break;
            case "flagtype":    System.out.println(flagTypeError);
                                break;
        }
    }
}
