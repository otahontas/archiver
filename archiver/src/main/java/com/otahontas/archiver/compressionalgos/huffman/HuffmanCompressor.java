package com.otahontas.archiver.compressionalgos.huffman;

import com.otahontas.archiver.compressionalgos.CompressionAlgo;
import com.otahontas.archiver.datastructures.huffman.MinimumPriorityQueue;
import com.otahontas.archiver.datastructures.huffman.HuffmanTree;
import com.otahontas.archiver.datastructures.List;
import com.otahontas.archiver.utils.Arrays;

import com.otahontas.archiver.datastructures.huffman.Node;

import java.nio.ByteBuffer;

public class HuffmanCompressor implements CompressionAlgo {

    private FrequencyCalculator freqcalc;
    private MinimumPriorityQueue prioqueue;
    private HuffmanTree hufftree;
    private Arrays arrays;

    public HuffmanCompressor() {
        this.freqcalc = new FrequencyCalculator();
        this.prioqueue = new MinimumPriorityQueue();
        this.arrays = new Arrays();
    }

    /**
     * Compresses given data array by counting byte frequencies, 
     * creating huffman tree, getting huffman codes and 
     * writing them to file with decoding information.
     *
     * Compressed file structure:
     * 1. First two bytes contain the tree size (e.g. how many nodes there are)
     * this should be max 511 when there is even number of all 256 bytes. Then
     * the tree has n + n - 1 nodes.
     * 2. Third byte contains amount of tree nodes with values. This helps
     * us to catch node values which follow in next bytes.
     * 3. Fourth byte contains amount of trailing zeroes in the end of file.
     * 4. Next bytes contains all node values.
     * 5. Then file contains huffman tree in pre-order traversal. Every node with 
     * some byte value is written as 1, nodes without values are written as 0. After this the file contains encoded bytes topped to end with trailing zeros to mach byte size
     * @param dataToCompress Data from uncompressed file as byte array
     * */
    public byte[] compress(byte[] dataToCompress) {
        prioqueue = freqcalc.createNodeQueue(dataToCompress);
        hufftree = new HuffmanTree();
        hufftree.formHuffmanTree(prioqueue);
        CodeConstructor constructor = new CodeConstructor(hufftree);
        List<Boolean>[] codewords = constructor.getCodewords();

        List<Boolean> compressed = new List<>();
        for (byte b : dataToCompress) {
            compressed.extend(codewords[b + 128]);
        }

        byte[] header = collectHeaderInformationForCompression(compressed.size());
        byte[] huffmanTreeValues = hufftree.getValues();
        byte[] treeOrderAndData = collectDataForCompression(compressed, header[3]);

        byte[] compressedDataToWrite = arrays.concatByteArray(header, huffmanTreeValues);
        compressedDataToWrite = arrays.concatByteArray(compressedDataToWrite, treeOrderAndData);

        return compressedDataToWrite;
    }

    /**
     * Decompress given data by going through header and torning bytes into 
     * bit lists. 
     * Header used contains next information with corresponging indexes:
     * 0. Amount of tree nodes
     * 1. Amount of tree nodes with some value
     * 2. Trailing bits
     * @param dataToDecompress Data from compressed file as byte array as byte array
     * */

    public byte[] decompress(byte[] dataToDecompress) {

        short[] header = collectHeaderInformationForDecompression(dataToDecompress);

        byte[] nodevalues = new byte[header[1]];
        for (int i = 0; i < header[1]; i++) nodevalues[i] = dataToDecompress[i+4];

        int headerAndValuesSize = 4 + nodevalues.length;
        byte[] restBytes = new byte[dataToDecompress.length - headerAndValuesSize];
        for (int i = 0; i < restBytes.length; i++) {
            restBytes[i] = dataToDecompress[i + headerAndValuesSize];
        }
        List<Boolean> restBits = turnBytesToBits(restBytes);
        restBits.removeLast(5);

        List<Boolean> huffmanTreeInPreOrder = new List<>();
        List<Boolean> encodedData = new List<>();

        for (int i = 0; i < header[0]; i++) huffmanTreeInPreOrder.add(restBits.get(i));

        for (int i = header[0]; i < restBits.size(); i++) encodedData.add(restBits.get(i));

        HuffmanTree hufftree = new HuffmanTree();
        hufftree.formHuffmanTreeForDecoding(huffmanTreeInPreOrder, nodevalues);

        byte[] decompressedData = decodeData(encodedData, hufftree);

        return decompressedData;
    }

    /* === PRIVATE METHODS == */

    /**
     * Collects header information for compression and returns them as byte array
     * @param sizeOfCompressedBits Size of bit list with compressed data
     * @return Byte array with header info
     */

    private byte[] collectHeaderInformationForCompression(int sizeOfCompressedBits) {
        byte[] header = new byte[4];
        byte[] treeSize = ByteBuffer.allocate(2).putShort(hufftree.GetTreeSize()).array();
        header[0] = treeSize[0];
        header[1] = treeSize[1];
        header[2] = hufftree.getAmountOfValues();
        header[3] = (byte) (8 - ((hufftree.GetTreeSize() + sizeOfCompressedBits) % 8));
        return header;
    }

    /**
     * Collects rest of the data for compression from bit lists and 
     * turns bits into bytes
     * @param compressed List of compressed data as bits
     * @param trailing amount of trailing zeros to add
     * @return Huffman tree traversal order and compressed data as bytes
     */
    private byte[] collectDataForCompression(List<Boolean> compressed,  byte trailing) {
        List<Boolean> restbits = hufftree.getTreePreOrder();
        restbits.extend(compressed);
        for (int i = 0; i < trailing; i++) restbits.add(false);

        byte[] restBytes = new byte[restbits.size() / 8];
        for (int entry = 0; entry < restBytes.length; entry++) {
            for (int bit = 0; bit < 8; bit++) {
                if (restbits.get(entry * 8 + bit)) {
                    restBytes[entry] |= (128 >> bit);
                }
            }
        }
        return restBytes;
    }

    /**
     * Collects header info (tree size, amount of values, trailing zeroes) 
     * from decompressed file and returns info as short array
     *
     * @param dataToDecompress
     * @return Header info as array
     */

    private short[] collectHeaderInformationForDecompression(byte[] dataToDecompress) {
        ByteBuffer bb = ByteBuffer.allocate(2);
        bb.put(dataToDecompress[0]);
        bb.put(dataToDecompress[1]);

        short[] header = new short[3];

        header[0] = bb.getShort(0);
        header[1] = (short) (128 + dataToDecompress[2]);
        header[2] = dataToDecompress[3];
        return header;
    }

    /**
     * Turn byte array into corresponding list of bits represented as booleans
     * Should be optimized more, since uses string conversion at the moment
     * @param restBytes
     * @return
     */

    private List<Boolean> turnBytesToBits(byte[] restBytes) {
        List<Boolean> bits = new List<>();
        for (byte b : restBytes) {
            String s = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '1') {
                    bits.add(true);
                } else {
                    bits.add(false);
                }
            }
        }
        return bits;
    }

    private byte[] decodeData(List<Boolean> encodedData, HuffmanTree decodeTree) {
        
        Node current = decodeTree.getRoot();
        List<Byte> buffer = new List<>();

        for (int i = 0; i < encodedData.size(); i++) {
            current = encodedData.get(i) ? current.getRightChild() : current.getLeftChild();
            if (current.getLeftChild() == null && current.getRightChild() == null) {
                buffer.add((byte) current.getValue());
                current = decodeTree.getRoot();
            } 
        }

        byte[] decompressedData = new byte[buffer.size()];

        for (int i = 0; i < buffer.size();i++) {
            decompressedData[i] = buffer.get(i);
        }

        return decompressedData;
    }
}
