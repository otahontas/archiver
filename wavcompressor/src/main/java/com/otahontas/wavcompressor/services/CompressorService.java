package com.otahontas.wavcompressor.services;

import com.otahontas.wavcompressor.utils.FileIO;
import com.otahontas.wavcompressor.utils.WavInfoReader;
import com.otahontas.wavcompressor.compressionalgos.LZWCompressor;
import com.otahontas.wavcompressor.compressionalgos.HuffmanCompressor;
import java.io.IOException;

public class CompressorService {

    private LZWCompressor lzw;
    private HuffmanCompressor huffman;
    private FileIO f;
    private WavInfoReader wir;

    public CompressorService() {
        //TODO: make interface for these
        this.lzw = new LZWCompressor();
        this.huffman = new HuffmanCompressor();
    }

    public void run(char command, char algo, String source, String output) {
        this.f = new FileIO();
        byte[] dataBytearray = new byte[0];

        try {
            dataBytearray = f.readFile(source);
        } catch (IOException ioe) {
            System.out.println("Error:" + ioe);
        }

        if (dataBytearray.length == 0) {
            System.out.println("Couldn't process given input");
            return;
        }

        //wir = new WavInfoReader(dataBytearray);
        //dataBytearray = wir.getDataByteArray();

        byte[] newData = new byte[0];

        switch (command) {
            case 'c':   newData = compress(algo, dataBytearray);
                        break;
            case 'e':   newData = extract(algo, dataBytearray);
                        break;
        }

        if (newData.length == 0) {
            System.out.println("Couldn't compress or extract file given");
            return;
        }

        try {
            f.writeFile(output, newData);
        } catch (IOException ioe) {
            System.out.println("Error:" + ioe);
        }

        compare(dataBytearray, newData);
    }

    private byte[] compress(char algo, byte[] dataBytearray) {
        switch (algo) {
            case 'l':   lzw.addSamples(dataBytearray);
                        return lzw.compress();
            case 'h':   System.out.println("not yet working");
                        break;
        }
        return new byte[0];
    }

    private byte[] extract(char algo, byte[] dataBytearray) {
        // not yet implemented
        System.out.println("not yet working");
        return new byte[0];

    }

    private void compare(byte[] original, byte[] compressed) {
        int originalSize = Byte.SIZE * original.length;
        int newSize = Byte.SIZE * compressed.length;
        double rate = (double) originalSize/ (double) newSize;
        System.out.println("Compression rate: " + rate);
    }
}
