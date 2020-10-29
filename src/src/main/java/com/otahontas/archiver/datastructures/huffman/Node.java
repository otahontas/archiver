package com.otahontas.archiver.datastructures.huffman;

/**
 * Class for elements in {@link HuffmanTree}.
 */
public class Node implements Comparable<Node> {

    private int frequency;
    private int value;
    private Node leftChild;
    private Node rightChild;
    private Node parent;

    /**
     * Creates tree node with frequency as comparing key and byte as value.
     * Initially sets both children as null
     */
    public Node(int frequency, int value) {
        this.frequency = frequency;
        this.value = value;
        this.leftChild = null;
        this.rightChild = null;
    }

    /**
     * Returns frequency of byte value in this node
     * @return Frequency of byte
     * */

    public int getFrequency() {
        return this.frequency;
    }

    /**
     * Returns byte value in this node
     * @return Byte value
     * */

    public int getValue() {
        return this.value;
    }

    /**
     * Sets left child
     * @param leftChild The Node that is set as the left Child
     */
    public void setLeftChild(Node leftChild) {
        this.leftChild = leftChild;
    }

    /**
     * Sets right child
     * @param rightChild The Node that is set as the right child.
     */
    public void setRightChild(Node rightChild) {
        this.rightChild = rightChild;
    }

    /**
     * Gets right child
     * @return The right child of the node
     */
    public Node getRightChild() {
        return this.rightChild;
    }

    /**
     * Gets left child
     * @return The left child of the node
     */
    public Node getLeftChild() {
        return this.leftChild;
    }

    /**
     * Gets parent
     * @return Parent of the node
     */
    public Node getParent() {
        return this.parent;
    }

    /**
     * Set parent
     * @param Node to be set as parent
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }


    /**
     * Compares two Nodes.
     * Smaller one is the one with a smaller frequency.
     * @param n Node to compare this node with
     * @return Negative value when others frequency is higher. When both are 
     * same, return smaller byte value
     */
    @Override
    public int compareTo(Node n) {
        if (this.frequency == n.frequency) {
            return this.value - n.value;
        }
        return this.frequency - n.frequency;
    }

}
