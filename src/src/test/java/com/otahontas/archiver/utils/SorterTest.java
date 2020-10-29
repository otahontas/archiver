package com.otahontas.archiver.utils;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for {@link Sorter} 
 * */

public class SorterTest {
    private Sorter sorter;

    @Before
    public void setUp() {
        sorter = new Sorter();
    }

    @Test
    public void arrayGetsSorterCorrectly() {
        char[] array = {'g', 'f', 'e', 'd', 'c', 'b', 'a'};
        char[] sortedArray = {'a', 'b', 'c', 'd', 'e', 'f', 'g'};
        char[] sortedWithSorter = sorter.sortCharArray(array);

        assertArrayEquals(sortedArray, sortedWithSorter);
    }

    @Test
    public void alreadySortedArrayDoesNotGetSortedWrongly() {
        char[] sortedArray = {'a', 'b', 'c', 'd', 'e', 'f', 'g'};
        char[] sortedWithSorter = sorter.sortCharArray(sortedArray);
        assertArrayEquals(sortedArray, sortedWithSorter);
    }
}
