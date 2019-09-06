package com.otahontas.archiver.datastructures.lzw;

import com.otahontas.archiver.datastructures.List;
import com.otahontas.archiver.datastructures.Pair;
/** 
 * Resizable HashMap implementation using {@link utils.List} and {@link utils.Pair}
 * */

public class HashMap<K, V> {

    private List<Pair<K, V>>[] values;
    private int size;

    public HashMap() {
        this.values = new List[32];
        this.size = 0;
    }
}
