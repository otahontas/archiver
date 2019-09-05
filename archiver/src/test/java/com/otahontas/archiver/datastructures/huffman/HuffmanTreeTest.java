package com.otahontas.archiver.datastructures.huffman;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.otahontas.archiver.compressionalgos.huffman.FrequencyCalculator;
import com.otahontas.archiver.datastructures.List;

/**
 * Unit tests for {@link HuffmanTree}
 * */

public class HuffmanTreeTest {
    FrequencyCalculator freqcalc;
    MinimumPriorityQueue nodequeue;
    HuffmanTree huffmantree;

    @Before
    public void setUp() {
        freqcalc = new FrequencyCalculator();
        nodequeue = new MinimumPriorityQueue();
        huffmantree = new HuffmanTree();
    }

    @Test
    public void treeIsBuiltCorrectlyWithOnlyOneElement() {
        byte[] array = { 10 };
        nodequeue = freqcalc.createNodeQueue(array);
        huffmantree.formHuffmanTree(nodequeue);

        Node n = huffmantree.getRoot();

        assertEquals(10+128, n.getValue());
    }

    @Test(expected=ArrayIndexOutOfBoundsException.class)
    public void treeBuildingFailsWithZeroElements() {
        nodequeue.popMinimumElement();
        huffmantree.formHuffmanTree(nodequeue);
    }

    /**
     * This test uses input data from 
     * https://www.geeksforgeeks.org/huffman-decoding/
     * */

    @Test
    public void treeIsBuildCorrectlyWithOddNumberOfElements() {
        byte[] array = { 65,65,65,65,65,65,
                         66,
                         67,67,67,67,67,67,
                         68,68,
                         69,69,69,69,69};

        nodequeue = freqcalc.createNodeQueue(array);
        huffmantree.formHuffmanTree(nodequeue);

        Node n = huffmantree.getRoot();
        Node leftmost = n.getLeftChild().getLeftChild().getLeftChild();
        Node rightmost = n.getRightChild().getRightChild();

        assertEquals(20,n.getFrequency());
        assertEquals(1,leftmost.getFrequency());
        assertEquals(66+128,leftmost.getValue());
        assertEquals(6,rightmost.getFrequency());
        assertEquals(67+128,rightmost.getValue());
    }


    /**
     * This test uses input data from Cormens "Introduction to Algorithms"
     * */

    @Test
    public void treeIsBuildCorrectlyWithEvenNumberOfElements() {
        byte array[] = new byte[100];
        for (int i = 0; i < 45; i++) array[i] = 65;
        for (int i = 45; i < 58; i++) array[i] = 66;
        for (int i = 58; i < 70; i++) array[i] = 67;
        for (int i = 70; i < 86; i++) array[i] = 68;
        for (int i = 86; i < 95; i++) array[i] = 69;
        for (int i = 95; i < 100; i++) array[i] = 70;

        nodequeue = freqcalc.createNodeQueue(array);
        huffmantree.formHuffmanTree(nodequeue);

        Node n = huffmantree.getRoot();
        Node leftmost = n.getLeftChild();
        Node bottomLeft = n.getRightChild().getRightChild().getLeftChild().getLeftChild();
        Node bottomRight = n.getRightChild().getRightChild().getLeftChild().getRightChild();

        assertEquals(100,n.getFrequency());
        assertEquals(45,leftmost.getFrequency());
        assertEquals(65+128,leftmost.getValue());
        assertEquals(5,bottomLeft.getFrequency());
        assertEquals(70+128,bottomLeft.getValue());
        assertEquals(9,bottomRight.getFrequency());
        assertEquals(69+128,bottomRight.getValue());
    }

    @Test
    public void treeBuildsCorrectlyForDecoding() {
        List<Boolean> list = new List<>();
        list.add(false);
        list.add(false);
        list.add(false);
        list.add(false);
        list.add(true);
        list.add(true);
        list.add(true);
        list.add(true);
        list.add(false);
        list.add(true);
        list.add(true);

        byte[] values = {10,66,68,69,65,67};

        huffmantree.formHuffmanTreeForDecoding(list, values);
        Node leftmost = huffmantree.getRoot().getLeftChild().getLeftChild().getLeftChild().getLeftChild();
        Node rightmost = huffmantree.getRoot().getRightChild().getRightChild();

        assertEquals(values[0],leftmost.getValue() - 128);
        assertEquals(values[values.length - 1],rightmost.getValue() - 128);
    }

    @Test
    public void treeBuiltForDecodingDoesNotHaveExtraNodes() {
        List<Boolean> list = new List<>();
        list.add(false);
        list.add(false);
        list.add(false);
        list.add(false);
        list.add(true);
        list.add(true);
        list.add(true);
        list.add(true);
        list.add(false);
        list.add(true);
        list.add(true);

        byte[] values = {10,66,68,69,65,67};
        huffmantree.formHuffmanTreeForDecoding(list, values);
        Node leftmost = huffmantree.getRoot().getLeftChild().getLeftChild().getLeftChild().getLeftChild();
        Node rightmost = huffmantree.getRoot().getRightChild().getRightChild();

        assertNull(leftmost.getLeftChild());
        assertNull(leftmost.getRightChild());
        assertNull(rightmost.getLeftChild());
        assertNull(rightmost.getRightChild());
    }
}
