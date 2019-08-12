
package com.otahontas.javasynth;

/**
 * Class containing basic implementation of Fast Fourier Transform
 * @author otahontas
 */
public class FFT {
    
    
    public static void transform(double[] real, double[] imag) {
        int n = real.length;
        
        // input need to be given as an array with lenght of power of 2 
        if (n == 0) throw new IllegalArgumentException("Give input");
        if (n != imag.length) throw new IllegalArgumentException("Both arrays should be the same size");
        if ((n & (n - 1)) != 0) throw new IllegalArgumentException("Input lenght should be power of 2");
        
        int levels = 31 - Integer.numberOfLeadingZeros(n);
        
        // Trigonometric tables
        double[] cosTable = new double[n / 2];
        double[] sinTable = new double[n / 2];
        
        for (int i = 0; i < n / 2; i++) {
                cosTable[i] = Math.cos(2 * Math.PI * i / n);
                sinTable[i] = Math.sin(2 * Math.PI * i / n);
        }

        // Bit-reversed addressing permutation
        for (int i = 0; i < n; i++) {
                int j = Integer.reverse(i) >>> (32 - levels);
                if (j > i) {
                        double temp = real[i];
                        real[i] = real[j];
                        real[j] = temp;
                        temp = imag[i];
                        imag[i] = imag[j];
                        imag[j] = temp;
                }
        }

        // Cooley-Tukey in-time 
        for (int size = 2; size <= n; size *= 2) {
                int half = size / 2;
                int tablestep = n / size;
                for (int i = 0; i < n; i += size) {
                        for (int j = i, k = 0; j < i + half; j++, k += tablestep) {
                                int l = j + half;
                                double tpre =  real[l] * cosTable[k] + imag[l] * sinTable[k];
                                double tpim = -real[l] * sinTable[k] + imag[l] * cosTable[k];
                                real[l] = real[j] - tpre;
                                imag[l] = imag[j] - tpim;
                                real[j] += tpre;
                                imag[j] += tpim;
                        }
                }
                
                // Prevent overflow here in 'size *= 2'
                // TODO: is there better way to check out this?
                if (size == n) break;
        }
    }
}
