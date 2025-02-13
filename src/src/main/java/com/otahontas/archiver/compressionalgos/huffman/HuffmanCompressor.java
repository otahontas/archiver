package com.otahontas.archiver.compressionalgos.huffman;

import com.otahontas.archiver.compressionalgos.CompressionAlgo;
import com.otahontas.archiver.datastructures.List;
import com.otahontas.archiver.datastructures.huffman.HuffmanTree;
import com.otahontas.archiver.datastructures.huffman.MinimumPriorityQueue;
import com.otahontas.archiver.datastructures.huffman.Node;
import com.otahontas.archiver.utils.Arrays;

// Class uses ByteBuffer for easier conversion between bytes and shorts
import java.nio.ByteBuffer;

/**
 * Class that handles compressing and decompressing with Huffman algorithm and data structures
 * Implements CompressionAlgo interface
 */

public class HuffmanCompressor implements CompressionAlgo {

    /**
     * Compresses given data array by counting byte frequencies, creating huffman tree, getting huffman codes and
     * writing them to file with decoding information.
     * Compressed file structure:
     * 1. First two bytes contain the tree size (e.g. how many nodes there are) this should be max 511 when there is
     * even number of all 256 bytes. Then the tree has n + n - 1 nodes.
     * 2. Third byte contains amount of tree nodes with values. This helps to catch node values when decoding.
     * 3. Fourth byte contains amount of trailing zeroes in the end of file.
     * 4. Next bytes contains all node values.
     * 5. Rest of bytes contain
     * a) huffman tree in pre-order traversal, where every node with some byte value is written as 1's, rest as 0's
     * b)encoded bytes topped to end with trailing
     *
     * @param dataToCompress Data from uncompressed file as byte array
     */

    public byte[] compress(byte[] dataToCompress) {
        CodeConstructor constructor = new CodeConstructor();
        FrequencyCalculator freqcalc = new FrequencyCalculator();
        HuffmanTree hufftree = new HuffmanTree();

        MinimumPriorityQueue prioqueue = freqcalc.createNodeQueue(dataToCompress);
        hufftree.formHuffmanTreeForEncoding(prioqueue);
        constructor.constructCodeWordsPreorderAndNodeValues(hufftree);

        List<Boolean>[] codewords = constructor.getCodewords();
        List<Boolean> compressed = new List<>();
        for (byte b : dataToCompress) compressed.extend(codewords[b + 128]);

        byte[] huffmanTreeValues = hufftree.getValues();
        byte[] header = collectHeaderInformationForCompression(compressed.size(), hufftree, huffmanTreeValues.length);
        byte[] treeOrderAndData = collectDataForCompression(compressed, header[3], hufftree);

        Arrays arrays = new Arrays();
        byte[] compressedDataToWrite;
        compressedDataToWrite = arrays.concatByteArray(header, huffmanTreeValues);
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
     *
     * @param dataToDecompress Data from compressed file as byte array as byte array
     */

    public byte[] decompress(byte[] dataToDecompress) {

        short[] header = collectHeaderInformationForDecompression(dataToDecompress);

        byte[] nodevalues = new byte[header[1]];
        for (int i = 0; i < header[1]; i++) nodevalues[i] = dataToDecompress[i + 4];

        int headerSize = 4 + nodevalues.length;

        List<Boolean>[] extracted = extractHuffmanTreeAndData(header, headerSize, dataToDecompress);

        HuffmanTree hufftree = new HuffmanTree();
        hufftree.formHuffmanTreeForDecoding(extracted[0], nodevalues);

        return decodeData(extracted[1], hufftree);
    }

    /**
     * Collects header information for compression and returns them as byte array
     *
     * @param sizeOfCompressedBits Size of bit list with compressed data
     * @return Byte array with header info
     */

    private byte[] collectHeaderInformationForCompression(int sizeOfCompressedBits, HuffmanTree hufftree, int valuesSize) {
        byte[] header = new byte[4];
        byte[] treeSize = ByteBuffer.allocate(2).putShort(hufftree.GetTreeSize()).array();
        header[0] = treeSize[0];
        header[1] = treeSize[1];
        header[2] = (byte) (valuesSize - 129);
        header[3] = (byte) (8 - ((hufftree.GetTreeSize() + sizeOfCompressedBits) % 8));
        return header;
    }

    /**
     * Collects rest of the data for compression from bit lists and
     * turns bits into bytes
     *
     * @param compressed List of compressed data as bits
     * @param trailing   amount of trailing zeros to add
     * @return Huffman tree traversal order and compressed data as bytes
     */
    private byte[] collectDataForCompression(List<Boolean> compressed, byte trailing, HuffmanTree hufftree) {
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
        header[1] = (short) (129 + dataToDecompress[2]);
        header[2] = dataToDecompress[3];
        return header;
    }

    /**
     * Turn byte array into corresponding list of bits represented as booleans
     * @param restBytes
     * @return
     */

    private List<Boolean> turnBytesToBits(byte[] restBytes) {
        List<Boolean> bits = new List<>();
        for (byte b : restBytes) {
            boolean[] result = new boolean[8];
            for (int i = 0; i < 8; i++) {
                result[i] = (b & (1 << i)) != 0;
            }
            for (int i = 7; i >= 0; i--) {
                bits.add(result[i]);
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
                buffer.add((byte) (current.getValue()));
                current = decodeTree.getRoot();
            }
        }

        byte[] decompressedData = new byte[buffer.size()];

        for (int i = 0; i < buffer.size(); i++) {
            decompressedData[i] = buffer.get(i);
        }

        return decompressedData;
    }

    private List<Boolean>[] extractHuffmanTreeAndData(short[] header, int headerSize,
                                                      byte[] dataToDecompress) {
        byte[] restBytes = new byte[dataToDecompress.length - headerSize];

        for (int i = 0; i < restBytes.length; i++) {
            restBytes[i] = dataToDecompress[i + headerSize];
        }
        List<Boolean> restBits = turnBytesToBits(restBytes);
        restBits.removeLast(header[2]);

        List<Boolean> huffmanTreeInPreOrder = new List<>();
        List<Boolean> encodedData = new List<>();

        for (int i = 0; i < header[0]; i++) huffmanTreeInPreOrder.add(restBits.get(i));

        for (int i = header[0]; i < restBits.size(); i++) encodedData.add(restBits.get(i));

        List<Boolean>[] extractedLists = (List<Boolean>[]) new List[2];
        extractedLists[0] = huffmanTreeInPreOrder;
        extractedLists[1] = encodedData;
        return extractedLists;
    }
}
