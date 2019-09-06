package com.otahontas.archiver.performancetesting;

import com.otahontas.archiver.utils.FileIO;
import com.otahontas.archiver.compressionalgos.CompressionAlgo;
import com.otahontas.archiver.compressionalgos.lzw.LZWCompressor;
import com.otahontas.archiver.compressionalgos.huffman.HuffmanCompressor;

import java.io.IOException;
import java.nio.file.NoSuchFileException;

public class Performance {
    private FileIO f;
    private CompressionAlgo compressor;

    public void runTests() {
        this.f = new FileIO();
        String[] filenames = { 
            "testfiles/smalltestwithtext",
            "testfiles/smalltestwithwav",
            "testfiles/mediumtestwithtext",
            "testfiles/largetestwithwav",
            "testfiles/largetestwithtext", 
            "testfiles/testwithrandomfile"
        };

        for (String file : filenames) {
            runIndividualTest(file);
        }
    }

    public void runIndividualTest(String filename) {
        // Get content
        byte[] content = new byte[0];
        try {
            content = f.readFile(filename);
        } catch (NoSuchFileException nsfe){
            System.out.println(nsfe);
        } catch (IOException ioe) {
            System.out.println(ioe);
        }

        System.out.println("====== RUNNING TESTS FOR FILE: " + filename + " ======\n");

        // Run LZW tests
        compressor = new LZWCompressor();

        long LZWcompStart = System.nanoTime();
        byte[] compressed = compressor.compress(content);
        long LZWcompTime = System.nanoTime() - LZWcompStart;
        double LZWRatio = (double) 100 * compressed.length / (double) content.length;

        long LZWdecompStart = System.nanoTime();
        compressor.decompress(compressed);
        long LZWdecompTime = System.nanoTime() - LZWdecompStart;


        // Run Huffman tests
        compressor = new HuffmanCompressor();
        long huffCompStart = System.nanoTime();

        compressed = compressor.compress(content);
        long huffCompTime = System.nanoTime() - huffCompStart;
        double huffRatio = (double) 100 * compressed.length / (double) content.length;

        long huffDecompStart = System.nanoTime();
        compressor.decompress(compressed);
        long huffDecompTime = System.nanoTime() - huffDecompStart;


        System.out.println("=== Test results with LZW: ===");
        System.out.println("1. Compression time (ms): " + (LZWcompTime / 1000000));
        System.out.println("2. Decompression time (ms): " + (LZWdecompTime / 1000000));
        System.out.println("3. Compression ratio achieved: " + String.format("%.0f%%\n",LZWRatio));

        System.out.println("=== Test results with Huffman: ===");
        System.out.println("1. Compression time (ms): " + (huffCompTime / 1000000));
        System.out.println("2. Decompression time (ms): " + (huffDecompTime / 1000000));
        System.out.println("3. Compression ratio achieved:" + String.format("%.0f%%\n",huffRatio));
        System.out.println("==================================\n");
    }
}
