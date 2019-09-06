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
     * Also checks if there is already a file and in that case doesn't overwrite it
     * @param filename Path to file
     * @param bytesToWrite Array of bytes to write to file
     * @throws IOException when writing fails
     */

    public void writeFile(String filename, byte[] bytesToWrite) throws IOException {
        if (Files.notExists(Paths.get(filename))) {
            Files.write(Paths.get(filename), bytesToWrite, StandardOpenOption.CREATE);
        } else {
            System.out.println("File " + Paths.get(filename).toString() + " already "
                    + "exists. Give new name for output.");
        }
    }
}
