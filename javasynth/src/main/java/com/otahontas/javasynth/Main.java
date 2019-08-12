package com.otahontas.javasynth;
import java.util.Arrays;
import java.util.Scanner;


public class Main {
    
    public static void main(String[] args) {
        
        double[] real = {0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,1};
        double[] imag = new double[real.length];
        
        FFT fft = new FFT();
        
        FFT.transform(real, imag);
        
        System.out.println(Arrays.toString(real));
        System.out.println(Arrays.toString(imag));
        
    }

}
