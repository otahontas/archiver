package com.otahontas.wavcompressor.utils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.NoSuchFileException;
import java.io.IOException;

/**
 * Unit tests for FileIO;
 */

public class FileIOTest {
    FileIO reader;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @Before
    public void setUp() {
        reader = new FileIO();
    }

    @Test
    public void WavFileReturnsNonEmptyArray() throws NoSuchFileException,
           IOException {

        byte[] result 
        = reader.readWavFile("testfiles/Yamaha-V50-Rock-Beat-120bpm.wav");
        assertNotEquals(0, result.length);
    }

    @Test
    public void NonWavFileReturnsErrorString() throws NoSuchFileException, 
                                                      IOException {
        System.setOut(new PrintStream(outContent));
        reader.readWavFile("testfiles/testi");
        assertEquals("Please give a wav file as an argument\n",
                     outContent.toString());
        System.setOut(originalOut);
    }

    @Test
    public void NonWavFileReturnEmptyByteArray() throws NoSuchFileException, 
                                                        IOException {

        byte[] result = reader.readWavFile("testfiles/testi");
        assertEquals(0, result.length);
    }

    @Test(expected = NoSuchFileException.class)
    public void NonExistingFileReturnsError() throws IOException {
        reader.readWavFile("yolo.wav");
    }
}
