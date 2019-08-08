
package com.otahontas.javasynth;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;


public class SamplePlayer {
    
    private AudioFormat format;
    private DataLine.Info info;
    private SourceDataLine sdline;
    private int bufferSize = 1000;
    private byte [] sampleData = new byte[bufferSize];
    private Oscillator sampleProvider;
    private volatile boolean cancelled;
    private Thread playingThread;
    
    /**
     * Constructor for SamplePlayer. By default it uses sample rate of 44100, 
     * 16 bits per sample, mono channel and signed bigEndian byte order. 
     * Dataline info object describes line format 
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
     * Set new sample rate, which also affects audioformat
     * @param sampleRate Sample rate in points per second for oscillator 
     * (higher sample rate gives more "full" sound)
     */
    
    public void setSampleRate(int sampleRate) {
        format = new AudioFormat(sampleRate, 16, 1, true, true);
    }
    
    /**
     * Get Line to write data to, get samples from sample provider and 
     * write samples to data line with offset of 0.
     * Use anonymous class syntax to get new Thread to be run
     */
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try {
                sdline = (SourceDataLine) AudioSystem.getLine(info);
                sdline.open(format);
                sdline.start();
                
                while (true) {
                    sampleProvider.getSamples(sampleData);
                    sdline.write(sampleData, 0, bufferSize);
                }
            } catch (LineUnavailableException lue) {
                lue.printStackTrace();
            } finally {
                sdline.drain();
                sdline.close();
            }
        }
    };
    
    /**
     * Start player in its own thread so it can be controlled better
     */
    public void startPlaying() {
        playingThread = new Thread(runnable);
        playingThread.start();
    }
    
    /**
     * Stop player and close data line
     * TODO: fix this one
     */
    public void stopPlaying() {
    }
}
