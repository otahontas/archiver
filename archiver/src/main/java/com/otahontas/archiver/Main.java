package com.otahontas.archiver;

import com.otahontas.archiver.compressionalgos.lzw.LZWCompressor;
import com.otahontas.archiver.compressionalgos.huffman.HuffmanCompressor;
import com.otahontas.archiver.utils.FileIO;
import com.otahontas.archiver.services.CLIArgumentReader;

public class Main {

    public static void main(String[] args) {
        LZWCompressor lzw = new LZWCompressor();
        HuffmanCompressor h = new HuffmanCompressor();
        FileIO f = new FileIO();

        CLIArgumentReader cli = new CLIArgumentReader(lzw, h, f);
        cli.run(args);
    }
}
