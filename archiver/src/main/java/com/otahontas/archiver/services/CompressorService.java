package com.otahontas.archiver.services;

import com.otahontas.archiver.utils.FileIO;
import com.otahontas.archiver.compressionalgos.lzw.LZWCompressor;
import com.otahontas.archiver.compressionalgos.huffman.HuffmanCompressor;

import java.io.IOException;

public class CompressorService {

    private LZWCompressor lzw;
    private HuffmanCompressor huffman;
    private FileIO f;

    public CompressorService(LZWCompressor lzw, HuffmanCompressor huffman,
                             FileIO f) {
        this.lzw = lzw;
        this.huffman = huffman;
        this.f = f;
    }

    public void run(char command, char algo, String source, String output) {
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
    }

    private byte[] compress(char algo, byte[] dataBytearray) {
        switch (algo) {
            case 'l':   lzw.compress();
                        break;
            case 'h':   return huffman.compress(dataBytearray);
        }
        return new byte[0];
    }

    private byte[] extract(char algo, byte[] dataBytearray) {
        switch (algo) {
            case 'l':   lzw.compress();
                        break;
            case 'h':   return huffman.decompress(dataBytearray);
        }
        return new byte[0];
    }
}
