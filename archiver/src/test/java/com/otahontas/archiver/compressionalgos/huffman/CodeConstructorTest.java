package com.otahontas.archiver.compressionalgos.huffman;

import com.otahontas.archiver.datastructures.List;
import com.otahontas.archiver.datastructures.huffman.HuffmanTree;
import com.otahontas.archiver.datastructures.huffman.MinimumPriorityQueue;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests for {@link CodeConstructor} 
 * */

public class CodeConstructorTest {
    CodeConstructor constructor;
    FrequencyCalculator freqcalc;
    MinimumPriorityQueue nodequeue;
    HuffmanTree huffmantree;
    List<Boolean>[] codewords;
    byte[] array;

    @Before
    public void setUp() {
        freqcalc = new FrequencyCalculator();
        nodequeue = new MinimumPriorityQueue();
        huffmantree = new HuffmanTree();
        constructor = new CodeConstructor();
    }

    @Test
    public void codeWordsGetBuiltCorrectlyFromHuffmanTree() {
        array = new byte[100];

        for (int i = 0; i < 45; i++) array[i] = 65;
        for (int i = 45; i < 58; i++) array[i] = 66;
        for (int i = 58; i < 70; i++) array[i] = 67;
        for (int i = 70; i < 86; i++) array[i] = 68;
        for (int i = 86; i < 95; i++) array[i] = 69;
        for (int i = 95; i < 100; i++) array[i] = 70;

        nodequeue = freqcalc.createNodeQueue(array);
        huffmantree.formHuffmanTreeForEncoding(nodequeue);
        constructor.constructCodeWordsPreorderAndNodeValues(huffmantree);
        codewords = constructor.getCodewords();

        List<Boolean> codewordForCharAAsList = codewords[65+128];
        List<Boolean> codewordForCharFAsList = codewords[70+128];
        String codewordForCharA = buildCodeWordFromList(codewordForCharAAsList);
        String codewordForCharF = buildCodeWordFromList(codewordForCharFAsList);


        assertEquals("0", codewordForCharA.toString());
        assertEquals("1100", codewordForCharF.toString());
    }

    @Test
    public void anotherTestForThatCodeWordsGetBuiltCorrectlyFromHuffmanTree() {
        byte[] array = {84, 111, 101, 32, 80, 114, 111, 106, 101, 99, 116, 32, 
                        101, 111, 32, 74, 111, 121, 99, 101, 10 };

        nodequeue = freqcalc.createNodeQueue(array);
        huffmantree.formHuffmanTreeForEncoding(nodequeue);
        constructor.constructCodeWordsPreorderAndNodeValues(huffmantree);
        codewords = constructor.getCodewords();

        List<Boolean> codeword84AsList = codewords[84+128];
        String codewordForByte84 = buildCodeWordFromList(codeword84AsList);

        List<Boolean> codeword111AsList = codewords[111+128];
        String codewordForByte111 = buildCodeWordFromList(codeword111AsList);

        assertEquals("11111", codewordForByte84);
        assertEquals("01", codewordForByte111);
    }

    @Test
    public void checkThatCorrectPreOrderIsCreated() {
        byte[] array = {84, 111, 101, 32, 80, 114, 111, 106, 101, 99, 116, 32, 
                        101, 111, 32, 74, 111, 121, 99, 101, 10 };

        nodequeue = freqcalc.createNodeQueue(array);
        huffmantree.formHuffmanTreeForEncoding(nodequeue);
        constructor.constructCodeWordsPreorderAndNodeValues(huffmantree);
        List<Boolean> preorder = huffmantree.getTreePreOrder();

        StringBuilder preorderAsString = new StringBuilder();
        for (int i = 0; i < preorder.size() ;i++) {
            if (preorder.get(i)) {
                preorderAsString.append(1);
            } else {
                preorderAsString.append(0);
            }
        }
        assertEquals("00110001110010110011011", preorderAsString.toString());
    }

    @Test
    public void checkThatCorrectValuesAreExtracted() {
        byte[] array = {84, 111, 101, 32, 80, 114, 111, 106, 101, 99, 116, 32, 
                        101, 111, 32, 74, 111, 121, 99, 101, 10 };

        nodequeue = freqcalc.createNodeQueue(array);
        huffmantree.formHuffmanTreeForEncoding(nodequeue);
        constructor.constructCodeWordsPreorderAndNodeValues(huffmantree);
        byte[] values = huffmantree.getValues();

        assertEquals(12,values.length);
        assertEquals(101, values[0]);
        assertEquals(121, values[7]);
        assertEquals(84, values[11]);
    }


    private String buildCodeWordFromList(List<Boolean> list) {
        StringBuilder codeword = new StringBuilder();
        for (int i = 0; i < list.size();i++) {
            if (list.get(i)) {
                codeword.append(1);
            } else {
                codeword.append(0);
            }
        }
        return codeword.toString();
    }
}
