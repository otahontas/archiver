/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.otahontas.javasynth;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author otahontas
 */
public class FFTTest {
    
    public FFTTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }


    @Test
    public void testTransformWithSmallInput() {
        // Input
        double[] real = new double [16];
        double[] imag = new double [real.length];
        real[1] = 5;
        
        // supposed outputs
        double[] realSolution = {5, 4.62, 3.54, 1.91, 0, -1.91, -3.54, -4.62, 
                                -5, -4.62, -3.54, -1.91, 0, 1.91, 3.54, 4.62};
        double[] imagSolution = {0, -1.91, -3.54, -4.62, -5, -4.62, -3.54, -1.91, 
                                0, 1.91, 3.54, 4.62, 5, 4.62, 3.54, 1.91};
        
        FFT.transform(real, imag);
        // since calc is made with doubles, and real solutions above are calculated
        // by hand, we should allow delta (= max difference)  of 0.01
        assertArrayEquals(realSolution, real, 0.01);
        assertArrayEquals(imagSolution, imag, 0.01);
    }
    
    @Test
    public void testTransformWithOnlyZeroInput() {
        double[] real = new double[4];
        double[] imag = new double[real.length];
        double[] realSolution = new double[4];
        double[] imagSolution = new double[4];
        
        
        FFT.transform(real, imag);
        
        assertArrayEquals(realSolution, real, 0.00);
        assertArrayEquals(imagSolution, imag, 0.00);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testTransformWithEmptyInput() {
        double[] real = new double [0];
        double[] imag = new double [real.length];
        
        FFT.transform(real, imag);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testTransformWithDifferenSizeArrays() {
        double[] real = new double [2];
        double[] imag = new double [4];
        
        FFT.transform(real, imag);
    }
    
}
