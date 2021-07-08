package com.jjtest.user.po.tree;

import lombok.Data;

@Data
public class Node<K, V> {

    private int hash;

    private Node parent;

    private K k;

    private V v;

    private Node left;

    private Node right;

    public Node() {
    }

    public Node(K k, V v) {
        hash = k.hashCode();
        this.k = k;
        this.v = v;
    }

    @Override
    public String toString() {
        return "{"  +
                "k=" + k +
                ", v=" + v +
                '}';
    }
}
