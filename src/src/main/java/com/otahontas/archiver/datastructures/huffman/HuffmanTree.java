package com.otahontas.archiver.datastructures.huffman;

import com.otahontas.archiver.datastructures.List;

/**
 * Class for Huffman Tree with optimal prefix codes
 * */

public class HuffmanTree { 

    private Node root;
    private List<Boolean> treePreOrder;
    private List<Byte> values;

    /**
     * Forms Huffman tree based on priority queue keyed with byte frequencies.
     * Huffman tree is uses value 128 as "null", since all it's initial values
     * are bytes in range -128 to 127. 
     * @param priorityqueue
     */
    
    public void formHuffmanTreeForEncoding(MinimumPriorityQueue priorityqueue) {
        while (priorityqueue.getSize() > 1) {
            Node x = priorityqueue.popMinimumElement();
            Node y = priorityqueue.popMinimumElement();
            Node z = new Node(x.getFrequency() + y.getFrequency(), 128);
            z.setLeftChild(x);
            z.setRightChild(y);
            priorityqueue.insert(z);
        }
        root = priorityqueue.popMinimumElement();
    }
    
    /**
     * Constructs decoding tree from given list.
     * List should contain huffman tree in pre-order traversal order, marking
     * every node with value as true and nodes with no value as false.
     * Frequencies are not used and are set to 128.
     * Inserts 128 as value for every node that has no value.
     * Inserts value from given byte array for every node that has a value.
     * @param preorder Huffman tree as list
     * @param nodevalues Values of tree nodes with values
     */

    public void formHuffmanTreeForDecoding(List<Boolean> preorder, byte[] nodevalues) {
        root = new Node(128, 128);
        Node parent = root;
        int valuesIndex = 0;
        for (int i = 1; i < preorder.size(); i++) {
            while (parent.getLeftChild() != null && parent.getRightChild() != null) {
                parent = parent.getParent();
            }
            if (preorder.get(i)) {
                if (parent.getLeftChild() == null) {
                    parent.setLeftChild(new Node(128,nodevalues[valuesIndex++]));
                } else {
                    parent.setRightChild(new Node(128,nodevalues[valuesIndex++]));
                }
            } else {
                if (parent.getLeftChild() == null) {
                    parent.setLeftChild(new Node(128,128));
                    Node child = parent.getLeftChild();
                    child.setParent(parent);
                    parent = child;
                } else {
                    parent.setRightChild(new Node(128,128));
                    Node child = parent.getRightChild();
                    child.setParent(parent);
                    parent = child;
                }
            }
        }
    }

    /**
     * Returns root of this Huffman tree
     * @return Root node
     * */

    public Node getRoot() {
        return root;
    }

    /**
     * Returns size of this Huffman tree
     * @return Size
     * */

    public short GetTreeSize() {
        return (short) treePreOrder.size();
    }

    public void setTreePreOrder(List<Boolean> order) {
        this.treePreOrder = order;
    }
    
    public List<Boolean> getTreePreOrder() {
        return this.treePreOrder;
    }

    public void setValues(List<Byte> values) {
        this.values = values;
    }

    public byte[] getValues() {
        byte[] values = new byte[this.values.size()];
        for (int i = 0; i < this.values.size(); i++) {
            values[i] = this.values.get(i);
        }
        return values;
    }
}
