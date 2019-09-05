package com.otahontas.archiver.compressionalgos.huffman;

import com.otahontas.archiver.datastructures.huffman.HuffmanTree;
import com.otahontas.archiver.datastructures.huffman.Node;
import com.otahontas.archiver.datastructures.List;

/**
 * Class that constructs codewords from {@link HuffmanTree}. 
 * Codewords are coded as list of 0's and 1's represented by booleans
 * */

public class CodeConstructor {

    private List<Boolean>[] codewords;
    private List<Boolean> treePreOrder;
    private List<Byte> values;
    
    /**
     * Constructs object, calls for forming codewords and right preorder
     * to {@link HuffmanTree}
     * @param hufftree Huffman Tree to construct codes from
     * */
 
    public CodeConstructor(HuffmanTree hufftree) {
        this.codewords = (List<Boolean>[]) new List[256];
        this.treePreOrder = new List<>();
        this.values = new List<>();
        formCodeWords(hufftree.getRoot(), new List<Boolean>());
        hufftree.setTreePreOrder(treePreOrder);
        hufftree.setValues(values);
    }

    /**
     * Gets all codewords from given huffmantree
     * @return Array of codewords as lists
     */

    public List<Boolean>[] getCodewords() {
        return this.codewords;
    }

    /* === PRIVATE METHODS === */

    /**
     * Forms codewords recursively and construct preorder traversal guide
     * as boolean list
     * @param node Root node
     * @param code codeword from current path
     */

    private void formCodeWords(Node node, List<Boolean> code) {
        if (node == null) return;

        if (node.getValue() != 128) {
            treePreOrder.add(true);
            values.add((byte) (node.getValue() - 128));
        } else {
            treePreOrder.add(false);
        }

        if (node.getLeftChild() == null && node.getRightChild() == null) { 
            codewords[node.getValue()] = code;
        }
        List<Boolean> left = new List<>();
        List<Boolean> right = new List<>();

        try {
            left = (List<Boolean>) code.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println(e);
        }
        try {
            right = (List<Boolean>) code.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println(e);
        }

        left.add(false);
        right.add(true);
        formCodeWords(node.getLeftChild(),left);
        formCodeWords(node.getRightChild(),right);
    }

}
