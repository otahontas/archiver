package com.otahontas.archiver.datastructures.huffman;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.otahontas.archiver.datastructures.huffman.Node;

/**
 * Unit tests for {@link MinimumPriorityQueue}
 * */

public class MinimumPriorityQueueTest {
    MinimumPriorityQueue nodequeue;

    @Before
    public void setUp() {
        nodequeue = new MinimumPriorityQueue();
    }

    @Test
    public void queueWithNodesDoesNotReturnEmpty() {
        nodequeue.insert(new Node(1,1));
        assertFalse(nodequeue.isEmpty());
    }

    @Test
    public void peekMinimumElementReturnsCorrectElement() {
        nodequeue.insert(new Node(1,1));
        nodequeue.insert(new Node(2,2));
        nodequeue.insert(new Node(3,3));

        Node n = nodequeue.peekMinimumElement();
        assertEquals(1, n.getValue());
    }

    @Test
    public void peekMinimumElementDoesNotRemoveElements() {
        nodequeue.insert(new Node(1,1));
        nodequeue.insert(new Node(2,2));
        nodequeue.insert(new Node(3,3));

        nodequeue.peekMinimumElement();
        assertEquals(3, nodequeue.getSize());
    }


    @Test
    public void popMinimumElementRemovesElements() {
        nodequeue.insert(new Node(1,1));
        nodequeue.insert(new Node(2,2));
        nodequeue.insert(new Node(3,3));

        nodequeue.popMinimumElement();
        assertEquals(2, nodequeue.getSize());
    }


}
