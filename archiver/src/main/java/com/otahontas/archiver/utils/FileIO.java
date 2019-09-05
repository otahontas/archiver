package com.otahontas.archiver.utils;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.IOException;

/**
 * Util that handles reading files 
 * */

public class FileIO {

    /**
     * Reads file and parses possible errors
     * @param filename Path to file
     * @return file as Byte array 
     * @throws NoSuchFileException when given file doesn't exist
     * @throws IOException when reading the given file doesn't work
     */
    public byte[] readFile (String filename) throws NoSuchFileException,
                                                    IOException {
        return Files.readAllBytes(Paths.get(filename));
    }

    /**
     * Writes file as given filename
     * @param filename Path to file
     * @param bytesToWrite Array of bytes to write to file
     * @throws IOException when writing fails
     */

    // TODO: throw exception when file already exists

    public void writeFile(String filename, byte[] bytesToWrite) throws IOException {
        Files.write(Paths.get(filename), bytesToWrite, StandardOpenOption.CREATE);
    }
}
