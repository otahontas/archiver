package com.otahontas.archiver.compressionalgos;

public interface CompressionAlgo {

    byte[] compress(byte[] dataToCompress);

    byte[] decompress(byte[] dataToDecompress);

}
