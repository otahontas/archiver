package com.otahontas.archiver.compressionalgos.lzw;

import java.util.HashMap;

import com.otahontas.archiver.datastructures.Pair;
import com.otahontas.archiver.datastructures.lzw.BitArray;
import com.otahontas.archiver.datastructures.List;

public class LZWCompressor {

    private final List<Byte> rawSamples;

    public LZWCompressor() {
        rawSamples = new List<>();
    }

    public List<Byte> getSamples() {
        return rawSamples;
    }

    public void clearSamples() {
        rawSamples.clear();
    }

    public void addSample(byte sample) {
        rawSamples.add(sample);
    }

    public void addSamples(byte[] samples) {
        for (byte sample : samples) {
            addSample(sample);
        }
    }

    public HashMap<String, Integer> createDictionary() {
        HashMap<String, Integer> dict = new HashMap<>();
        for (int i = 0; i < 256; i++) {
            dict.put(Integer.toString(i),  i);
        }
        return dict;
    }

    public byte[] compress() {
        HashMap<String, Integer> dict = createDictionary();

        String p = "";
        BitArray finalOutput = new BitArray();
        for (int i = 0; i < rawSamples.size(); i++) {
            String c = Integer.toUnsignedString(rawSamples.get(i) & 0xFF);
            String currentCodeword;
            if (!p.equals("")) {
                currentCodeword = p + "|" + c;
            } else {
                currentCodeword = c;
            }
            if (dict.containsKey(currentCodeword)) {
                p = currentCodeword;
            } else {
                finalOutput.add(BitArray.intToBitArray(dict.get(p)));
                dict.put(currentCodeword, (int) dict.size());
                p = c;
            }
        }
        if (rawSamples.size() > 0) {
            finalOutput.add(BitArray.intToBitArray(dict.get(p)));
        }
        return finalOutput.toByteArray();
    }
    
    private void output(String cW) {
        String[] parts = cW.split("\\|");
        for (String part : parts) {
            addSample((byte) Integer.parseInt(part));
        }
    }

    public List<Byte> decompress(byte[] compressionResultsAsBytes) {
        if (compressionResultsAsBytes.length == 0) {
            clearSamples();
            return getSamples();
        }

        //TODO: create correct reverse method for this
        HashMap<Integer, String> reversedCodeWordsDict = new HashMap<>();
        
        BitArray data = new BitArray(compressionResultsAsBytes);
        if (data.size() == 0) {
            return null;
        }
        clearSamples();
        try {
            Pair<Integer, Integer> cW = BitArray.bitArrayToInt(data, 0);
            output(reversedCodeWordsDict.get(cW.getKey()));
            String pW = reversedCodeWordsDict.get(cW.getKey());
            while (cW.getValue() < data.size()) {
                cW = BitArray.bitArrayToInt(data, cW.getValue());
                String entry;
                if (reversedCodeWordsDict.containsKey(cW.getKey())) {
                    entry = reversedCodeWordsDict.get(cW.getKey());
                } else {
                    entry = pW + "|" + pW.split("\\|")[0];
                }
                output(entry);
                reversedCodeWordsDict.put(reversedCodeWordsDict.size(), pW + "|" + entry.split("\\|")[0]);
                pW = entry;
            }
        } catch (Exception e) {
        }
        
        
        return getSamples();
    }
}
