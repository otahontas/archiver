package com.otahontas.javasynth;

public class Oscillator {
    
    private WAVESHAPE waveshape;
    private double samplesPerPeriod;
    private long currentSample;
    
    /**
     * Waveshape enumeration
     */   
    public enum WAVESHAPE {
        SIN, SQU, SAW
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
      * @param sampleRate Sample rate in points per second for oscillator 
      */
    public void setFrequency(double frequency, int sampleRate) {
        samplesPerPeriod = (sampleRate  / frequency);
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
     * Get a buffer of oscillator samples
     * 
     * @param buffer Array to fill samples in
     * 
     */
    
    // TODO: tutki mitä nää tekee
    public void getSamples(byte [] buffer) {
        int index = 0;
        for (int i = 0; i < buffer.length / 2; i++) {
            
            double ds = getSample() * Short.MAX_VALUE;
            short ss = (short) Math.round(ds);
            
            buffer[index++] = (byte)(ss >> 8);
            buffer[index++] = (byte)(ss & 0xFF);
        }
        
    }

}
