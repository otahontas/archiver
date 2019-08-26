package com.otahontas.wavcompressor;

/**
 * 
 *
 */

import com.otahontas.wavcompressor.compressionalgos.LZWCompressor;
import com.otahontas.wavcompressor.utils.WavInfoReader;
import com.otahontas.wavcompressor.utils.FileIO;
import java.io.IOException;

public class Main {


    public static void main(String[] args) {

        FileIO f = new FileIO();
        byte[] dataBytearray = new byte[0];
        try {
            dataBytearray = f.readWavFile("testfiles/Yamaha-V50-Rock-Beat-120bpm.wav");
        } catch (IOException ioe) {
            System.out.print(ioe);
        }

        WavInfoReader wir = new WavInfoReader(dataBytearray);

        dataBytearray = wir.getDataByteArray();

        int originalSizeUsed = Byte.SIZE * dataBytearray.length;

        LZWCompressor lw = new LZWCompressor();
        lw.addSamples(dataBytearray);
        byte[] lzwData = lw.compress();
        int newSizeUsed = Byte.SIZE * lzwData.length;

        double rate = (double) originalSizeUsed / (double) newSizeUsed;

        System.out.println("Compression rate: " + rate);
    }
}
