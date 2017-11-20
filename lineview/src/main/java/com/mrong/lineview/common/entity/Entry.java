package com.mrong.lineview.common.entity;

/**
 * 以键值对的形式
 * 
 * @author 林金成
 *         2017年11月14日
 * @param <K>
 *            : 键
 * @param <V>
 *            : 值
 */
public class Entry<K, V> {
    private K key;
    private V value;

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Entry() {
        super();
    }

    public Entry(K key, V value) {
        super();
        this.key = key;
        this.value = value;
    }
}
