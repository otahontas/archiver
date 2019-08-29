package com.otahontas.wavcompressor.services;

import com.otahontas.wavcompressor.utils.FileIO;
import com.otahontas.wavcompressor.compressionalgos.LZWCompressor;
import com.otahontas.wavcompressor.compressionalgos.HuffmanCompressor;

public class CompressorService {

    private LZWCompressor lzw;
    private HuffmanCompressor huffman;

    public CompressorService() {
        this.lzw = new LZWCompressor();
        this.huffman = new HuffmanCompressor();
    }


    public void run(char command, char algo, String source, String output) {
        System.out.println("run the commands");
    }
}


/*
        switch (command) {
            case 'c':   compressorservice.compress()
                        break;
            case 'e':   compressorservice.extract()
                        break;
            default:    System.out.println("Invalid flag!\n" + usage);
            }
        switch (algo) {
            case 'l':   lzw.compress()
                        break;
            case 'h':   System.out.println("extract!");
                        break;
            default:    System.out.println("Invalid flag!\n" + usage);
            }
        }

    }

    public void extract (char algo) {

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
