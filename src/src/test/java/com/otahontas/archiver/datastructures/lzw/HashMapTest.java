package com.otahontas.archiver.datastructures.lzw;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link HashMap}
 * */

public class HashMapTest {

    @Test
    public void testThatHashMapWorksWithBasicData() {
        HashMap<String, String> hm = new HashMap<>();
    
        hm.put("USA", "Washington DC");
        hm.put("Nepal", "Kathmandu");
        hm.put("India", "New Delhi");
        hm.put("Australia", "Sydney");
    
        assertNotNull(hm);
        assertEquals(4, hm.size());
        assertEquals("Kathmandu", hm.get("Nepal"));
        assertEquals("Sydney", hm.get("Australia"));
    }


    @Test
    public void testThatTwoMapsContainSameValues() {

        HashMap<String, String> map = new HashMap<>();
        map.put("j", "java");
        map.put("c", "c++");
        map.put("p", "python");
        map.put("n", "node");

        HashMap<String, String> expected = new HashMap<>();
        expected.put("n", "node");
        expected.put("c", "c++");
        expected.put("j", "java");
        expected.put("p", "python");

        assertEquals(map.size(), expected.size());

        assertEquals(map.get("j"), expected.get("j"));
        assertEquals(map.get("c"), expected.get("c"));
        assertEquals(map.get("p"), expected.get("p"));
        assertEquals(map.get("n"), expected.get("n"));
    }
}
