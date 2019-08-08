
package com.otahontas.javasynth;

import java.util.Scanner;

public class Synth {
    
    private Scanner scan;
    private Oscillator osc;
    private SamplePlayer player;
    
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
        player.playSamples();
    }

}
