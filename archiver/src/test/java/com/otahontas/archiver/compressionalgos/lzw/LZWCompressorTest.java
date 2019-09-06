package com.otahontas.archiver.compressionalgos.lzw;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


/**
 * Unit tests for {@link LZWCompressor}
 * */

public class LZWCompressorTest {
    LZWCompressor lzw;

    @Before
    public void setUp() {
        lzw = new LZWCompressor();
    }

    @Test
    public void dataIsSameAfterCompressionAndDecompression() {
        byte[] array = new byte[100];

        for (int i = 0; i < 45; i++) array[i] = 65;
        for (int i = 45; i < 58; i++) array[i] = 66;
        for (int i = 58; i < 70; i++) array[i] = 67;
        for (int i = 70; i < 86; i++) array[i] = 68;
        for (int i = 86; i < 95; i++) array[i] = 69;
        for (int i = 95; i < 100; i++) array[i] = 70;

        byte[] compressed = lzw.compress(array);
        byte[] decompressed = lzw.decompress(compressed);
        assertEquals(array.length,decompressed.length);

        assertArrayEquals(array, decompressed);
    }

    @Test
    public void anotherTestThatDataIsSameAfterCompressionAndDecompression() {
        byte[] array = {84, 104, 101, 32, 80, 114, 111, 106, 101, 99, 116, 32, 
                        71, 117, 116, 101, 110, 98, 101, 114, 103, 32, 69, 66, 
                        111, 15, 101, 115, 44, 32, 98, 121, 32, 74, 97, 109, 
                        101, 115, 32, 74, 111, 121, 99, 101, 10 };

        byte[] compressed = lzw.compress(array);
        byte[] decompressed = lzw.decompress(compressed);

        assertEquals(array.length,decompressed.length);
        assertArrayEquals(array, decompressed);
    }

    @Test
    public void TestThatDataIsSameForFileWithAllPositiveBytes() {
        byte[] array = new byte[128];
        for (int i = 0; i < 128; i++) {
            array[i] = (byte) i;
        }
        byte[] compressed = lzw.compress(array);
        byte[] decompressed = lzw.decompress(compressed);

        assertEquals(array.length,decompressed.length);
        assertArrayEquals(array, decompressed);
    }
}
