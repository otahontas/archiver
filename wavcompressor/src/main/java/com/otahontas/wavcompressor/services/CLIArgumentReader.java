package com.otahontas.wavcompressor.services;

import com.otahontas.wavcompressor.services.CompressorService;

public class CLIArgumentReader {

    private static CompressorService cs = new CompressorService();
    private static String info = "A CLI tool to compress and uncompress wav files";
    private static String usage =
        "Usage: java jar wavcompressor.jar [-opts] source dest\n"
        +"e.g. java jar wavcompressor.jar -lc orig.wav comp.wav\n"
        +"Opts:\n"
        +"-l Use LZW compression \t -h Use Huffman compression\n"
        +"-c Compress file \t -e Exctract file";

    /**
     * Main reads cli arguments, handles errors and 
     * calls then compressorservice with user input
     * */

    public static void main(String[] args) {
        if (args.length != 3) {
            printGuide();
            return;
        } 
        
        String flags = args[0];
        if (flags.length() != 3 || flags.charAt(0) != '-') {
            System.out.println("Error: an incorrect usage of flags!\n");
            printGuide();
            return;
        }
        flags = sortString(flags);
        String source = args[1];
        String output = args[2];

        char command = flags.charAt(1);
        char algo = flags.charAt(2);

        if (!((command == 'e' || command == 'c') && (algo == 'l' || algo == 'h'))) {
            System.out.println("Error: an incorrect flag!\n");
            printGuide();
            return;
        }
        cs.run(command, algo, source, output);
    }

    private static void printGuide() {
        System.out.println(info + "\n" + usage);
    }

    /* Since input size is max 3, we can use basic bubble sort */
    private static String sortString(String str) {
        char[] token = str.toCharArray();
        for (int i = 0; i < token.length; i++) {
            for (int j = i+1; j < token.length; j++) {
                if (token[i] > token[j]) {
                    char temp = token[i];
                    token[i] = token[j];
                    token[j] = temp;
                }
            }
        }
        return new String(token);
    }
}
