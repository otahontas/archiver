
package com.otahontas.javasynth;

import java.util.Scanner;

public class UI {
    
    private Scanner scan;
    private Oscillator osc;
    private SamplePlayer player;
    
    private int SampleRate = 44100;
    // TODO: maybe just calculate freqs directly from 440hz or 220hz? 
    // e.g. A3 is 220, so A#3 is (2^1/12)*220 etc. (Math.pow) 
    private Frequencies f = new Frequencies();
    
    /**
     * Constructor for text based ui for this synth
     * @param scan Scanner to be used to ask user for input
     * @param osc Oscillator to be used as provider for audio samples
     * @param player Class which can play samples given by oscillator
     */
    
    public UI(Scanner scan, Oscillator osc, SamplePlayer player) {
        this.scan = scan;
        this.osc = osc;
        this.player = player;
        player.setSampleProvider(osc);
    }
    
    public void start() {
        double[] freqs = f.getFreqs();
        osc.setOscWaveshape(Oscillator.WAVESHAPE.SIN);
        osc.setFrequency(freqs[7]);
        player.startPlaying();
        
        /* 
        // TODO: FIX the stopping part
        String input = scan.nextLine();
        if (input.equals("s")) {
            player.startPlaying();
        } else if (input.equals("d")) {
            osc.setFrequency(0);
        }
        */
    }

}
