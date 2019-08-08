package com.otahontas.javasynth;
import java.util.Scanner;


public class Main {
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Oscillator osc = new Oscillator();
        SamplePlayer player = new SamplePlayer();
        
        // Launch text UI
        Synth synth = new Synth(scanner, osc, player);
        synth.start();
        
    }

}
