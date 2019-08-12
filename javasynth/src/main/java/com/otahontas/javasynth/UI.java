
package com.otahontas.javasynth;

import java.util.Scanner;

public class Synth {
    
    private Scanner scan;
    private Oscillator osc;
    private SamplePlayer player;
    
    private int SampleRate = 44100;
    private Frequencies f = new Frequencies();
    
    /**
     * Constructor for text based ui for this synth
     * @param scan Scanner to be used to ask user for input
     * @param osc Oscillator to be used as provider for audio samples
     * @param player Class which can play samples given by oscillator
     */
    
    public Synth(Scanner scan, Oscillator osc, SamplePlayer player) {
        this.scan = scan;
        this.osc = osc;
        this.player = player;
        player.setSampleProvider(osc);
    }
    
    public void start() {
        double[] freqs = f.getFreqs();
        osc.setOscWaveshape(Oscillator.WAVESHAPE.SAW);
        osc.setFrequency(freqs[0]);
        player.startPlaying();
        
        /* Make oscillator "playable" with stdin
        String input = scan.nextLine();
        if (input.equals("s")) {
            player.startPlaying();
        } else if (input.equals("d")) {
            player.stopPlaying();
        }
        */
    }

}
