package com.otahontas.wavcompressor.utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

/**
 * Util that handles reading from wav file
 * */

public class FileIO {

    public byte[] readWavFile(String filename) throws NoSuchFileException, 
                                                      IOException {

        if (!(getExtension(filename).equals("wav"))) {
            System.out.print("Please give a wav file as an argument");
            return new byte[0];
        }

        return Files.readAllBytes(Paths.get(filename));
    }

    private String getExtension(String filename) {
        String extension = "";

        int i = filename.lastIndexOf('.');
        if (i > 0) extension = filename.substring(i+1);
        return extension;
    }
}
