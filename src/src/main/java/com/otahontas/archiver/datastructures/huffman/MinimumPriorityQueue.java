package com.otahontas.archiver.datastructures.huffman;

import com.otahontas.archiver.datastructures.huffman.Node;

/**
 * Implementation of minimum priority queue based on binary heap, used for
 * {@link HuffmanTree} {@link Node}s
 */

public class MinimumPriorityQueue {

    private int size;
    private Node[] heap;

    /**
     * Creates priority queue with index for all possible byte values
     */

    public MinimumPriorityQueue() {
        this.heap = new Node[256];
        this.size = 0;
    }

    /**
     * Returns the number of items in queue
     * @return Number of items in queue
     */

    public int getSize() {
        return size;
    }

    /**
     * Inserts huffman node to heap into correct place
     * @param node Node to be inserted
     */

    public void insert(Node node) {
        size++;
        int i = size - 1;
        while (i > 0 && heap[i / 2].compareTo(node) > 0) {
            heap[i] = heap[i / 2];
            i = i / 2;
        }
        heap[i] = node;
    }

    /**
     * Method checks whether the queue has no elements.
     * @return True when queue has no elements
     */

    public boolean isEmpty() {
        return this.size == 0;
    }

    /**
     * Returns the element from the queue that has the highest priority, 
     * but doesn't remove it.
     * @return Element with highest priority
     * */

    public Node peekMinimumElement() {
        return heap[0];
    }

    /**
     * Removes the element from the queue that has the highest priority, 
     * and returns it. 
     * @return Element with highest priority
     * */

    public Node popMinimumElement() {
        Node min = heap[0];
        heap[0] = heap[size - 1];
        size--;
        heapify(0);
        return min;
    }

    /* === PRIVATE METHODS === */

    /**
     * Create a new heap out of the rest of the heap elements
     * @param index
     */
    private void heapify(int index) {
        int left = 2 * index;
        int right = 2 * index + 1;
        int smallest = index;
        if (size > left && heap[left].compareTo(heap[index]) < 0) {
            smallest = left;
        }
        if (size > right && heap[right].compareTo(heap[smallest]) < 0) {
            smallest = right;
        }
        if (smallest != index) {
            swap(index, smallest);
            heapify(smallest);
        }
    }

    /**
     * Swap the content in given indexes
     * @param i Index 1
     * @param j Index 2
     */
    private void swap(int i, int j) {
        Node temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }
}
