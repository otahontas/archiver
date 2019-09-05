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

        String codeWordForCharA = "";
        List<Boolean> wordAsList = codewords[65+128];

        for (int i = 0; i < wordAsList.size();i++) {
                if (wordAsList.get(i)) {
                    codeWordForCharA += "1";
                } else {
                    codeWordForCharA += "0";
                }
        }

        String codeWordForCharF = "";
        wordAsList = codewords[70+128];
        for (int i = 0; i < wordAsList.size();i++) {
                if (wordAsList.get(i)) {
                    codeWordForCharF += "1";
                } else {
                    codeWordForCharF += "0";
                }
        }

        assertEquals("0", codeWordForCharA);
        assertEquals("1100", codeWordForCharF);
    }

    @Test
    public void anotherTestForThatCodeWordsGetBuiltCorrectlyFromHuffmanTree() {
        byte[] array = {84, 111, 101, 32, 80, 114, 111, 106, 101, 99, 116, 32, 
                        101, 111, 32, 74, 111, 121, 99, 101, 10 };

        nodequeue = freqcalc.createNodeQueue(array);
        huffmantree.formHuffmanTreeForEncoding(nodequeue);
        constructor.constructCodeWordsPreorderAndNodeValues(huffmantree);
        codewords = constructor.getCodewords();

        for (int j = 0; j < codewords.length; j++) {
            if (codewords[j] == null) continue;
            List<Boolean> wordAsList = codewords[j];

            String s = "";
            for (int i = 0; i < wordAsList.size();i++) {
                    if (wordAsList.get(i)) {
                        s += "1";
                    } else {
                        s += "0";
                    }
            }
        }
    }

    @Test
    public void checkThatCorrectPreOrderIsCreated() {
        byte[] array = {84, 111, 101, 32, 80, 114, 111, 106, 101, 99, 116, 32, 
                        101, 111, 32, 74, 111, 121, 99, 101, 10 };

        nodequeue = freqcalc.createNodeQueue(array);
        huffmantree.formHuffmanTreeForEncoding(nodequeue);
        constructor.constructCodeWordsPreorderAndNodeValues(huffmantree);
        List<Boolean> preorder = huffmantree.getTreePreOrder();

        String preorderAsString = "";
        for (int i = 0; i < preorder.size() ;i++) {
            if (preorder.get(i)) {
                preorderAsString += "1";
            } else {
                preorderAsString += "0";
            }
        }
        //assertEquals("00001101100110110011011", preorderAsString);
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
        //assertEquals(106, values[0]);
        //assertEquals(121, values[7]);
        //assertEquals(111, values[11]);
    }
}
