
package com.otahontas.javasynth;


public class Frequencies {
    private double[] freqs;

    public Frequencies() {
        this.freqs = new double[] {130.81, 138.59, 146.83, 155.56, 164.81, 
                                   174.61, 185.00, 196.00, 207.65, 220.00, 
                                   233.08, 246.94, 261.63, 277.18, 293.66,
                                   311.13, 329.63, 349.23, 369.99, 392.00,
                                   415.30, 440.00, 466.16, 493.88, 523.25 };
        }
    /**
     * Get frequencies for notes from C3 to C5 (two full octaves)
     * @return List of frequencies as doubles
     */
    public double[] getFreqs() {
        return this.freqs;
    }
    
    
}