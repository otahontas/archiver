package com.otahontas.archiver.services;


import com.otahontas.archiver.compressionalgos.lzw.LZWCompressor;
import com.otahontas.archiver.compressionalgos.huffman.HuffmanCompressor;
import com.otahontas.archiver.utils.FileIO;

/**
 * Class that reads cli arguments and passes them to service for handling
 * compression or extraction
 * */

public class CLIArgumentReader {

    private String info = "A CLI tool to compress and uncompress wav files";
    private String usage =
        "Usage: java -jar arhchiver.jar [-opts] source dest\n"
        +"e.g. java -jar archiver.jar -lc orig.wav comp.wav\n"
        +"Opts:\n"
        +"-l Use LZW compression \t -h Use Huffman compression\n"
        +"-c Compress file \t -e Exctract file";
    private CompressorService cs;

    public CLIArgumentReader(LZWCompressor lzw, HuffmanCompressor hc, FileIO f) {
        cs = new CompressorService(lzw,hc,f);
    }

    private void printGuide() {
        System.out.println(info + "\n" + usage);
    }

    /* Since input size is max 3, we can use basic bubble sort */
    private String sortString(String str) {
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

    public void run(String[] args) {
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
}
