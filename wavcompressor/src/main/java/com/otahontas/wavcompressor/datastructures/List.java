package com.otahontas.wavcompressor.datastructures;

/**
 *
 * Basic implementation of ArrayList - a resizable array. 
 * Array does only grow when needed. 
 * This implementation doesn't shrink array, so memory consumption is a little
 * bit higher than it should be.
 * 
 * @param <T> Type of objects list is going hold, e.g. List<String> = new List<>();
 */

public class List<T> {

    private T[] values;
    private int size;

    /**
     * Creates new ArrayList with default size of 10.
     * */

    public List() {
        this.values = (T[]) new Object[10];
        this.size = 0;
    }

    /**
     * Adds new item to the list
     * @param value Suitable object to add 
     * */

    public void add(T value) {
        if (this.size == this.values.length) {
            increaseSize();
        }

        this.values[this.size] = value;
        this.size++;
    }

    /**
     * Clear list by setting all values to null
     * */

    public void clear() {
        for (int i = 0; i < this.size; i++) {
            this.values[i] = null;
        }

    }

    /**
     * Checks if given item is on the list
     * @param value Suitable object to check
     * @return True or false depending on whether object is in list
     * */

    public boolean contains(T value) {
        return indexOfvalue(value) >= 0;
    }

    /**
     * Returns the item in given index of list. 
     * Throws IndexOutOfBoundsException if given index is greater than size of 
     * the list or the index is below zero.
     *
     * @param index index where from to return the item
     * @return the item in given index
     */

    public T get(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException(
                "Index " + index + "is outside of scope [0, " + this.size + "]");
        }
        return this.values[index];
    }

    /**
     * Removes first occurence of object from list if possible
     * @param Suitable object to remove
     * @return True if object was removed, otherwise false
     * */


    public boolean remove(T value) {
        int indexOfvalue = indexOfvalue(value);
        if (indexOfvalue < 0) return false;
        moveLeft(indexOfvalue);
        this.size--;
        this.values[this.size] = null;
        return true;
    }

    /**
     * Returns the amount of items in this list.
     *
     * @return amount of items in list
     */

    public int size() {
        return this.size;
    }
    /** 
     * Replaces value in given index with new object and returns old object in
     * that index.
     *
     * @param index Index where to set new object
     * @param obj Object to be set
     * @return old object from given index, null if it was empty
     * */

    public T set (int index, T obj) {
        if (index >= size() || index < 0) {
            throw new IndexOutOfBoundsException(
                "Index " + index + "is outside of scope [0, " + this.size + "]");
        }

        T output = values[index];
        values[index] = obj;

        return output;
    }

    /**
     * Return string presentation for list
     * */

    @Override
    public String toString() {
        String output = "[";
        for (int i = 0; i < this.size; i++) {
            output += this.values[i];
            if (i < size - 1) {
                output += ", ";
            }
        }
        output += "]";

        return output;
    }

    /* === PRIVATE METHODS === */

    private void increaseSize() {
        int newSize = this.values.length + this.values.length / 2;
        T[] newList = (T[]) new Object[newSize];
        for (int i = 0; i < this.values.length; i++) {
            newList[i] = this.values[i];
        }

        this.values = newList;
    }


    private int indexOfvalue(T value) {
        for (int i = 0; i < this.size; i++) {
            if (this.values[i].equals(value)) {
                return i;
            }
        } return -1;
    }

    private void moveLeft(int start) {
        if (start < 0 || start >= this.size) return;
        for (int i = start; i < this.size - 1; i++) {
            this.values[i] = this.values[i + 1];
        }
    }

}
