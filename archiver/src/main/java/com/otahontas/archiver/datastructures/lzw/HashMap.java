package com.otahontas.archiver.datastructures.lzw;

import com.otahontas.archiver.datastructures.List;
import com.otahontas.archiver.datastructures.Pair;
import com.otahontas.archiver.utils.Math;

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

    /**
     * Checks if given objects is found in hash set
     *
     * @param obj object to search for
     * @return true if found, else false
     */
    public boolean contains(K key) {
        return true;
    }


    /**
     * Get value for given key. 
     * Returns null if there's no key with given index
     * @param key
     * @return
     */

    public V get(K key) {
        int hash = getHash(key);
        if (this.values[hash] == null) {
            return null;
        }
        List <Pair<K, V>> valuesInIndex = this.values[hash];

        for (int i = 0; i < valuesInIndex.size(); i++) {
            if (valuesInIndex.get(i).getKey().equals(key)) {
                return valuesInIndex.get(i).getValue();
            }
        }
        return null;
    }

    /**
     * Adds new value to Hash Map or if key is already present, updates the 
     * value
     * @param key Key to add
     * @param value Value to add with key
     */ 

    public void put(K key, V value) {
        List<Pair<K,V>> valuesInIndex = getListForKey(key);
        int index = getIndexForKey(valuesInIndex,key);

        if (index < 0) {
            valuesInIndex.add(new Pair<>(key, value));
            size++;
        } else {
            valuesInIndex.get(index).setValue(value);
        }

        if (1.0 * size / values.length > 0.75) {
            increaseSize();
        }
    }

    /**
     * Returns amount of elements if hashmap
     * @return amount of elements in hashmap
     */
    public int size() {
        return size;
    }

    /**
     * Removes key value pair from hashmap
     * @param key key for pair to remove 
     * @return null if key is not found, otherwise value found with key
     */

    public V remove(K key) {
        List<Pair<K,V>> valuesInIndex = getListForKey(key);
        if (valuesInIndex.size() == 0) {
            return null;
        }

        int index = getIndexForKey(valuesInIndex,key);
        if (index < 0) {
            return null;
        }

        Pair<K,V> pair = valuesInIndex.get(index);
        valuesInIndex.remove(pair);
        return pair.getValue();
    }

    /**
     * returns hash value for given key
     * @param key key
     * @return hash value
     */

    private int getHash(K key) {
        return (int) Math.abs(key.hashCode() % this.values.length);
    }

    /**
     * Increases internal array's size when necessary
     * */

    private void increaseSize() {
        List<Pair<K, V>>[] newValues = new List[this.values.length * 2];

        for (int i = 0; i < this.values.length; i++) {
            copy(newValues, i);
        }
        this.values = newValues;
    }

    /**
     * copies values from list to another
     * @param key
     * @return
     */
    private void copy(List<Pair<K,V>>[] newValues, int index) {
        for (int i = 0; i < this.values[index].size(); i++) {
            Pair<K, V> pair = this.values[index].get(i);
            int hash = getHash(pair.getKey());

            if (newValues[hash] == null) {
                newValues[hash] = new List<>();
            }

            newValues[hash].add(pair);
        }
    }

    /**
     * Gets list from index based on given key's hash value
     * @param key Key to look for
     * @return List from key or empty list if there wasn't any key available
     */

    private List<Pair<K,V>> getListForKey(K key) {
        int hash = getHash(key);
        if (this.values[hash] == null) {
            values[hash] = new List<>();
        }

        return values[hash];
    }

    /**
     * Return index where given key is or -1 if key can't be found from list
     * @param list List where key should be found
     * @param key Key to look for
     * @return index for key or -1 if key wasn't in list
     */

    private int getIndexForKey (List<Pair<K,V>> list, K key) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getKey().equals(key)) {
                return i;
            }
        }
        return -1;
    }
}
