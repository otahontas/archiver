package com.otahontas.archiver.utils;

import org.junit.Before;
import org.junit.Test;

import java.nio.file.NoSuchFileException;
import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Unit tests for {@link FileIO}
 * */

public class FileIOTest {
    private FileIO f;

    @Before
    public void setUp() {
        f = new FileIO();
    }

    @Test(expected = NoSuchFileException.class)
    public void getErrorWhenOpeningNonExistingFile() throws IOException {
        f.readFile("testfiles/yolloo.txt");
    }

    @Test
    public void returnsArrayWhenReachingForRealFile() throws IOException {
        byte[] array = f.readFile("testfiles/smalltestwithtext");

        assertNotEquals(0, array.length);
    }

    @Test
    public void doesNotOverWriteFileWhenFileExists() throws IOException {
        byte[] original = f.readFile("testfiles/smalltestwithtext");
        byte[] writethis = {64, 64, 64, 64};
        f.writeFile("testfiles/smalltestwithtext", writethis);
        byte[] expectToBeSame = f.readFile("testfiles/smalltestwithtext");
        assertArrayEquals(original, expectToBeSame);
    }

}
