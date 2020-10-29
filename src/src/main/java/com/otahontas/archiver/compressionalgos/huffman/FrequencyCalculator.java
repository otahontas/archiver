package com.otahontas.archiver.compressionalgos.huffman;

import com.otahontas.archiver.datastructures.huffman.MinimumPriorityQueue;
import com.otahontas.archiver.datastructures.huffman.Node;

/**
 * Class that calculates frequencies for each byte n in given array
 * and returns minimum priority queue keyed by frequencies
 */

public class FrequencyCalculator {
    private int[] frequencies;
    private MinimumPriorityQueue nodequeue;

    /**
     * Create initial array with place for each byte.
     * This method supposes one byte occurs in file max 2147483647
     * times (Integer max value), which should be enough for this programs purposes
     * */
    
    public FrequencyCalculator() {
        frequencies = new int[256];
        nodequeue = new MinimumPriorityQueue();
    }
    
   /**
    * Calculates frequencies for each byte in array and creates minimum priority queue.
    * Queue is created with Huffman tree Nodes that have
    * bytes as values and byte frequencies as keys
    * Doesn't count bytes that have occurred zero times.
    * @param bytes Array containing bytes from file
    * @return Priority queue with Huffman tree nodes
    */

    public MinimumPriorityQueue createNodeQueue(byte[] bytes) {
        countFrequencies(bytes);
        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] != 0) nodequeue.insert(new Node(frequencies[i], i - 128));
        }
        return nodequeue;
    }

    /**
     * Counts bytes and creates array with frequencies
     * Each byte value is shifted with digit 128 to get proper indexing
     * @param bytes Array containing bytes from file
     */

    private void countFrequencies(byte[] bytes) {
        for (byte b : bytes) {
            frequencies[b + 128]++;
        }
    }
}
