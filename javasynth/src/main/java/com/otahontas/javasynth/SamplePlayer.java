
package com.otahontas.javasynth;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;


// TODO: check multithreading stuff later
public class SamplePlayer {
    
    private AudioFormat format;
    private DataLine.Info info;
    private SourceDataLine sdline;
    private int bufferSize = 1000;
    private byte [] sampleData = new byte[bufferSize];
    private Oscillator sampleProvider;
    
    /**
     * Constructor for SamplePlayer. By default it uses sample rate of 44100, 
     * 16 bits per sample, mono channel and signed bigEndian byte order
     * 
     */

    public SamplePlayer() {
        format = new AudioFormat(44100, 16, 1, true, true);
        info = new DataLine.Info(SourceDataLine.class, format);
    }
    
    /**
     * Give oscillator which provides samples to be played through dataline
     * @param sampleProvider 
     */
    
    public void setSampleProvider(Oscillator sampleProvider) {
        this.sampleProvider = sampleProvider;
    }
    
    /**
     * Set new Samplerate, which also affect audioformat
     * @param sampleRate Sample rate in points per second for oscillator 
     * (higher sample rate gives more "full" sound)
     */
    
    public void setSampleRate(int sampleRate) {
        format = new AudioFormat(sampleRate, 16, 1, true, true);
    }
    
    /**
     * Get Line to write data to, get samples from sample provider and 
     * write samples to dataline with offset of 0
     */
    public void playSamples() {
        try {
            sdline = (SourceDataLine) AudioSystem.getLine(info);
            sdline.open(format);
            sdline.start();
            
            while(true) {
                sampleProvider.getSamples(sampleData);
                sdline.write(sampleData, 0, bufferSize);
            }
            
        } catch (LineUnavailableException lue) {
            lue.printStackTrace();
        } 
    }
    
    
    
    
}
