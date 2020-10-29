package com.otahontas.archiver.services;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for {@link CLIArgumentReader}
 * */

public class CLIArgumentReaderTest{
    private CLIArgumentReader cli;


    @Before
    public void setUp() {
        cli = new CLIArgumentReader();
    }

    @Test
    public void callingWithoutArgsReturnsEmptyArray() {
        String[] parsed = cli.parse(new String[0]);
        assertEquals(0, parsed.length);
    }

    @Test
    public void incorrectAmountOfArgsReturnsEmptyArray() {
        String[] args1 = {"juuh", "jööh"};
        String[] args2 = {"juuh", "jööh", "mjöhöm", "mjähäm"};
        String[] parsed = cli.parse(args1);
        String[] parsed2 = cli.parse(args2);

        assertEquals(0, parsed.length);
        assertEquals(0, parsed2.length);
    }

    @Test
    public void callingWithoutFlagsReturnsEmptyArray() {
        String[] args1 = {"juuh", "jööh", "mjöhöm"};
        String[] parsed = cli.parse(args1);
        assertEquals(0, parsed.length);
    }

    @Test
    public void callingWithWrongFlagReturnEmptyArray() {
        String[] args1 = {"-ld", "source.txt", "output"};
        String[] parsed = cli.parse(args1);
        assertEquals(0, parsed.length);
    }

    @Test
    public void callingWithPerformanceFlagReturnCorrectArray() {
        String[] args1 = {"-p"};
        String[] parsed = cli.parse(args1);
        assertEquals(1, parsed.length);
        assertEquals("-p", parsed[0]);
    }

    @Test
    public void callingWithCorrectFlagsReturnsCorrectArray() {
        String[] args1 = {"-lc", "source.txt", "output"};
        String[] parsed = cli.parse(args1);
        assertEquals(4, parsed.length);
        assertEquals("c", parsed[0]);
        assertEquals("l", parsed[1]);
        assertEquals("source.txt", parsed[2]);
        assertEquals("output", parsed[3]);
    }
}
