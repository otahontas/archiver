package com.otahontas.archiver.services;

import com.otahontas.archiver.utils.FileIO;
import com.otahontas.archiver.compressionalgos.lzw.LZWCompressor;
import com.otahontas.archiver.compressionalgos.huffman.HuffmanCompressor;
import com.otahontas.archiver.compressionalgos.CompressionAlgo;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

/**
 * Class that handles calling correct compressor with correct parameters
 * Also handles calling FileIO class for reading and writing files to byte
 * arrays
 * */

public class CompressorService {

    private FileIO f;
    private CompressionAlgo compressor;

    /**
     * Default constructor creates compressor and file API objects
     * */

    public CompressorService() {
        this.f = new FileIO();
    }

    /**
     * Goes through given parameters and calls correct compressor.
     * Errors for FileIO and possible filenames are also handled here
     * @param params
     */

    public void run(String params[]) {
        char command = params[0].charAt(0);
        char algo = params[1].charAt(0);
        String source = params[2];
        String output = params[3];

        byte[] dataBytearray = new byte[0];

        try {
            dataBytearray = f.readFile(source);
        } catch (NoSuchFileException nsfe) {
            System.out.println("File given as source doesn't exist, see error below:");
            System.out.println(nsfe);
        } catch (IOException ioe) {
            System.out.println("Couldn't read the file, see error below:");
            System.out.println(ioe);
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
            f.writeFile(output + ".compressed", newData);
        } catch (IOException ioe) {
            System.out.println("Couldn't write the file, see error below:");
            System.out.println(ioe);
        }
    }

    /**
     * Calls compressor based on given algoritmh
     * @param algo Character representing Huffman or LZW
     * @param dataBytearray Data to be compressed as byte array
     * @return Compressed data as byte array
     */

    private byte[] compress(char algo, byte[] dataByteArray) {
        switch (algo) {
            case 'l':   compressor = new LZWCompressor();
            case 'h':   compressor = new HuffmanCompressor();
        }
        return compressor.compress(dataByteArray);
    }

    /**
     * Calls compressor based on given algoritmh
     * @param algo Character representing Huffman or LZW
     * @param dataBytearray Data to be decompressed as byte array
     * @return Decompressed data as byte array
     */

    private byte[] extract(char algo, byte[] dataByteArray) {
        switch (algo) {
            case 'l':   compressor = new LZWCompressor();
            case 'h':   compressor = new HuffmanCompressor();
        }
        return compressor.compress(dataByteArray);
    }
}
