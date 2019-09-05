package com.otahontas.archiver.compressionalgos.huffman;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.otahontas.archiver.datastructures.huffman.MinimumPriorityQueue;
import com.otahontas.archiver.datastructures.huffman.Node;

/**
 * Unit tests for {@link FrequencyCalculator}
 * */

public class FrequencyCalculatorTest {
    FrequencyCalculator freqcalc;
    MinimumPriorityQueue nodequeue;


    @Before
    public void setUp() {
        freqcalc = new FrequencyCalculator();
        nodequeue = new MinimumPriorityQueue();
    }

    @Test
    public void calculatorCountsFrequenciesAndCreatesQueueCorrectly() {
        byte[] array = { -10,-10,-10,0,0,0,0,100,100,100,100,100 };
        MinimumPriorityQueue result = freqcalc.createNodeQueue(array);
        Node minimum = result.popMinimumElement();

        assertEquals(3, minimum.getFrequency());
        assertEquals(-10, minimum.getValue());
    }

    @Test 
    public void calculatorDoesNotAddNonOccuringBytesToQueue() {
        byte[] array = { -10,-10,-10,0,0,0,0,100,100,100,100,100 };
        MinimumPriorityQueue result = freqcalc.createNodeQueue(array);
        result.popMinimumElement();
        result.popMinimumElement();
        result.popMinimumElement();

        assertTrue(result.isEmpty());
    }
    
    @Test
    public void calculatorCountsFrequenciesCorrectlyWithLargeAmounts() {
        int freq = 1000000;
        byte[] array = new byte[freq];
        MinimumPriorityQueue result = freqcalc.createNodeQueue(array);
        Node minimum = result.popMinimumElement();

        assertEquals(freq, minimum.getFrequency());
        assertEquals(0, minimum.getValue());
    }
}
