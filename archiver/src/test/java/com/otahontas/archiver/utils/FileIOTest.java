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
    public void returnsArrayWhenReachingForRealFile() throws IOException, NoSuchFileException {
        byte[] array = f.readFile("testfiles/aaaa.txt");

        assertNotEquals(0, array.length);
    }

    @Test
    public void doesNotOverWriteFileWhenFileExists() throws IOException, NoSuchFileException {
        byte[] original = f.readFile("testfiles/aaaa.txt");
        byte[] writethis = {64, 64, 64, 64};
        f.writeFile("testfiles/aaaa.txt", writethis);
        byte[] expectToBeSame = f.readFile("testfiles/aaaa.txt");
        assertArrayEquals(original, expectToBeSame);
    }

}
