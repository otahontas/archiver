package com.otahontas.wavcompressor.datastructures;

public class List<T> {

    private T[] values;
    private int size;

    public List() {
        this.values = (T[]) new Object[10];
        this.size = 0;
    }

    public void add(T value) {
        if (this.size == this.values.length) {
            increaseSize();
        }

        this.values[this.size] = value;
        this.size++;
    }

    public boolean contains(T value) {
        return indexOfvalue(value) >= 0;
    }

    public void remove(T value) {
        int indexOfvalue = indexOfvalue(value);
        if (indexOfvalue < 0) return;
        moveLeft(indexOfvalue);
        this.size--;
    }

    public T value(int index) {
        if (index < 0 || index >= this.size) {
            throw new ArrayIndexOutOfBoundsException(
                "Index " + index + "is outside of scope [0, " + this.size + "]");
        }
        return this.values[index];
    }

    public int size() {
        return this.size;
    }

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
        for (int i = start; i < this.size - 1; i++) {
            this.values[i] = this.values[i + 1];
        }
    }
}
