package com.otahontas.archiver.compressionalgos.huffman;

import com.otahontas.archiver.datastructures.List;
import com.otahontas.archiver.datastructures.huffman.HuffmanTree;
import com.otahontas.archiver.datastructures.huffman.Node;

/**
 * Class that constructs codewords from {@link HuffmanTree}. 
 * Codewords are coded as list of 0's and 1's represented by booleans
 * */

public class CodeConstructor {

    private List<Boolean>[] codewords;
    private List<Boolean> treePreOrder;
    private List<Byte> values;

    /**
     * Create initial array table with place for each bytes codeword
     * Initialize lists for preorder and values so they can be saved to huffman tree
     * */
 
    public CodeConstructor() {
        this.codewords = (List<Boolean>[]) new List[256];
        this.treePreOrder = new List<>();
        this.values = new List<>();
    }

    public void constructCodeWordsPreorderAndNodeValues(HuffmanTree hufftree) {
        formCodeWordsRecursively(hufftree.getRoot(), new List<Boolean>());
        hufftree.setTreePreOrder(treePreOrder);
        hufftree.setValues(values);
    }

    /**
     * Gets codewords
     * @return Array of codewords as lists
     */

    public List<Boolean>[] getCodewords() {
        return this.codewords;
    }

    /**
     * Forms codewords recursively, construct pre-order traversal guide as a boolean list,
     * and collect values from Nodes with values
     * Fixes codewords array to have proper indexing by adding 128 to each byte value
     * @param node Root of (sub)tree
     * @param code List containing codeword from current path
     */

    private void formCodeWordsRecursively(Node node, List<Boolean> code) {
        if (node == null) return;

        if (node.getValue() != 128) {
            treePreOrder.add(true);
            values.add((byte) (node.getValue()));
        } else {
            treePreOrder.add(false);
        }

        if (node.getLeftChild() == null && node.getRightChild() == null) { 
            codewords[node.getValue() + 128] = code;
        }
        List<Boolean> left = new List<>();
        List<Boolean> right = new List<>();

        try {
            left = (List<Boolean>) code.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Could not clone list when creating codewords");
            System.out.println(e);
        }
        try {
            right = (List<Boolean>) code.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Could not clone list when creating codewords");
            System.out.println(e);
        }

        left.add(false);
        right.add(true);
        formCodeWordsRecursively(node.getLeftChild(),left);
        formCodeWordsRecursively(node.getRightChild(),right);
    }
}
