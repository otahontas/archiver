package com.otahontas.archiver;

import com.otahontas.archiver.services.CLIArgumentReader;
import com.otahontas.archiver.services.CompressorService;
import com.otahontas.archiver.performancetesting.Performance;

/**
 * Main class for Archiver program
 * */

public class Main {

    /**
     * Main method gets CLI arguments, passes them to parser and calls 
     * compressorservice or stops program depenging on parameters returned
     * from parser
     * @param args Command line arguments
     */

    public static void main(String[] args) {
        CompressorService cs = new CompressorService();
        CLIArgumentReader cli = new CLIArgumentReader();

        String[] arguments = cli.parse(args);

        switch(arguments.length) {
            case(1):    Performance p = new Performance();
                        p.runTests();
                        break;
            case(4):    cs.run(arguments);
                        break;
        }

    }
}
