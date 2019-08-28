package com.otahontas.wavcompressor.services;

import com.otahontas.wavcompressor.services.CompressorService;

public class CLIArgumentReader {

    private CompressorService cs;
    private static String info = "A CLI tool to compress and uncompress wav files";
    private static String usage =
        "Usage: java jar wavcompressor [-opts] source dest\n"
        +"e.g. java jar wavcompressor -lc orig.wav comp.wav\n"
        +"Opts:\n"
        +"-l Use LZW compression \t -h Use Huffman compression\n"
        +"-c Compress file \t -e Exctract file";

    public CLIArgumentReader(CompressorService cs) {
        this.cs = cs;
    }

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println(info + "\n" + usage);
        } else {
            String flag = args[1];
            switch (flag) {
                case "-c":  System.out.println("compress!");
                            break;
                case "-e":  System.out.println("extract!");
                            break;
                default:    System.out.println("Invalid flag!\n" + usage);
            }
        }
    }

/*
    public void compress(String filename) {
        FileIO f = new FileIO();
        byte[] dataBytearray = new byte[0];

        try {
            dataBytearray = f.readWavFile(filename);
        } catch (IOException ioe) {
            System.out.println(ioe);
        }
        WavInfoReader wir = new WavInfoReader(dataBytearray);
        dataBytearray = wir.getDataByteArray();
        int originalSizeUsed = Byte.SIZE * dataBytearray.length;
        LZWCompressor lw = new LZWCompressor();
        lw.addSamples(dataBytearray);
        byte[] lzwData = lw.compress();
        int newSizeUsed = Byte.SIZE * lzwData.length;
        double rate = (double) originalSizeUsed / (double) newSizeUsed;
        System.out.println("Compression rate: " + rate);
    }
*/
}
