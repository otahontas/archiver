package com.otahontas.javasynth;

/**
 * This class contains oscillator - device that generates waveforms of audio samples
 * @author otahontas
 */
public class Oscillator {
    
    private WAVESHAPE waveshape;
    private double samplesPerPeriod;
    private long currentSample;
    private int sampleRate;
    
    /**
     * Waveshape enumeration
     */   
    public enum WAVESHAPE {
        SIN, SQU, SAW
    }

    /**
     * Constructor. Set sin wave to be default with sample rate 44100hz
     */
    public Oscillator() {
        this.waveshape = Oscillator.WAVESHAPE.SIN;
        this.sampleRate = 44100;
    }
    
    /**
     * Set waveshape of oscillator
     *
     * @param waveshape Determines the waveshape of this oscillator
     */
    public void setOscWaveshape(WAVESHAPE waveshape) {
        this.waveshape = waveshape;
    }
    
    /**
      * Set the frequency of the oscillator in Hz.
      *
      * @param frequency Frequency in Hz for this oscillator
      */
    public void setFrequency(double frequency) {
        samplesPerPeriod = (this.sampleRate  / frequency);
    }
    
    /**
     * Set new sample rate
     * @param sampleRate Sample rate in points per second for oscillator 
     * (higher sample rate gives more "full" sound)
     */
    
    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }
    
    /** 
     * Get samples to be filled to buffer, increasing index of current sample
     * until it reaches periodic limit
     * @return Next sample from oscillator for SamplePlayer
     */
    public double getSample() {
        double value;
        double point = currentSample / samplesPerPeriod;
        
        switch (waveshape) {
            default:
            case SIN:   value = Math.sin(2.0 * Math.PI * point);
                        break;
            case SQU:   value = 
                        (currentSample < (samplesPerPeriod / 2)) ? 1.0 : -1.0;
                        break;
            case SAW:   value = 2.0 * (point - Math.floor(point + 0.5));
                        break;
        }
        currentSample++;
        currentSample %= samplesPerPeriod;
        return value;
    }
    
    /**
     * Get a buffer of oscillator samples. Method fills buffer with 
     * samples encoded as bytes in big endian order
     * and.
     * 
     * @param buffer Array to fill samples in
     * 
         */
    public void getSamples(byte [] buffer) {
        int index = 0;
        for (int i = 0; i < buffer.length / 2; i++) {
            short ss = (short) Math.round(getSample() * Short.MAX_VALUE);
            buffer[index++] = (byte)(ss >> 8);
            buffer[index++] = (byte)(ss & 0xFF);
        }
        
    }

}