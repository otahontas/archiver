package com.otahontas.archiver.compressionalgos;

public interface CompressionAlgo {

    public byte[] compress(byte[] dataToCompress);

    public byte[] decompress(byte[] dataToDecompress);

}
