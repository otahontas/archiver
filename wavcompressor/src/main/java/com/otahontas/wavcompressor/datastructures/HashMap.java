package com.otahontas.wavcompressor.datastructures;

import com.otahontas.wavcompressor.datastructures.List;
import com.otahontas.wavcompressor.datastructures.Pair;

public class HashMap<K, V> {

    private List<Pair<K, V>>[] values;
    private int size;

    public HashMap() {
        this.values = new List[32];
        this.size = 0;
    }
}
