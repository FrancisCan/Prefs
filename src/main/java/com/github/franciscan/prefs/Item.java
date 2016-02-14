package com.github.franciscan.prefs;
/**
 * @author Francesco Cannizzaro
 */
public class Item<T> {

    public String key;
    public T value;

    public Item(String key, T value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return key + " : " + value;
    }
}
