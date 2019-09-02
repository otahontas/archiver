package com.otahontas.wavcompressor.utils;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.IOException;

/**
 * Util that handles reading from wav file
 * */

public class FileIO {

    /**
     * Reads wav file and parses possible errors
     * @param filename Path to file
     * @return waw file as an array of bytes
     * @throws NoSuchFileException when given file doesn't exist
     * @throws IOException when reading the given file doesn't work
     */

    public byte[] readWavFile(String filename) throws NoSuchFileException, 
                                                      IOException {

        if (!(getExtension(filename).equals("wav"))) {
            System.out.print("Please give a wav file as an argument\n");
            return new byte[0];
        }

        return Files.readAllBytes(Paths.get(filename));
    }

    public byte[] readFile (String filename) throws NoSuchFileException,
           IOException {
        return Files.readAllBytes(Paths.get(filename));
    }

    public void writeFile(String filename, byte[] bytesToWrite) throws IOException {
        Files.write(Paths.get(filename), bytesToWrite, StandardOpenOption.CREATE);
    }

    private String getExtension(String filename) {
        String extension = "";

        int i = filename.lastIndexOf('.');
        if (i > 0) extension = filename.substring(i+1);
        return extension;
    }
}
