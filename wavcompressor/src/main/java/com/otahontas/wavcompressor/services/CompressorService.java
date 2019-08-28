package com.otahontas.wavcompressor.services;

import com.otahontas.wavcompressor.utils.FileIO;
import com.otahontas.wavcompressor.compressionalgos.LZWCompressor;
import com.otahontas.wavcompressor.compressionalgos.HuffmanCompressor;

public class CompressorService {

    private LZWCompressor lzw;
    private HuffmanCompressor huffman;

    public CompressorService() {
        this.lzw = new LZWCompressor();
        this.huffman = new HuffmanCompressor();
    }
}
