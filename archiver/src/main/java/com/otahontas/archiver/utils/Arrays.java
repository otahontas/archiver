package com.otahontas.archiver.utils;

public class Arrays {

    public byte[] concatByteArray(byte[] a, byte[] b) {
        byte[] array = new byte[a.length + b.length];
        for (int i = 0; i < a.length; i++) {
            array[i] = a[i];
        }
        for (int i = a.length, j = 0; i < a.length + b.length; i++, j++) {
            array[i] = b[j];
        }
        return array;
    }
}
