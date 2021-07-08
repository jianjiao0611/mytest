package com.jjtest.user.po.tree;

import lombok.Data;

@Data
public class Tree<K, V> extends AbstractTree<K, V> {

    private Node root;

    @Override
    public Node find(K k) {
        if (root == null || k == null) {
            return null;
        }
        int hash = k.hashCode();
        Node current = root;
        while (current != null) {
            if (current.getHash() == hash && current.getK().equals(k)) {
                return current;
            }
            if (hash > current.getHash()) {
                current = current.getRight();
            } else {
                current = current.getLeft();
            }
        }
        return null;
    }

    @Override
    public boolean insert(K k, V v) {
        if (k == null || v == null) {
            throw new NullPointerException();
        }
        Node node = new Node(k, v);
        if (root == null) {
            root = node;
            return true;
        }
        int hash = k.hashCode();
        Node current = root;

        while (current != null) {
            int p = 0;
            if (hash > current.getHash()) {
                p = 1;
                if (current.getRight() == null) {
                    current.setRight(node);
                    node.setParent(current);
                    return true;
                }
                current = current.getRight();
            } else if (hash < current.getHash()) {
                p = -1;
                if (current.getLeft() == null) {
                    current.setLeft(node);
                    node.setParent(current);
                    return true;
                }
                current = current.getLeft();
            } else {
                if (p > 0) {
                    current.getParent().setRight(node);
                } else {
                    current.getParent().setLeft(node);
                }
                node.setParent(current.getParent());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(K k) {
        return false;
    }

    @Override
    public String toString() {
        return getStr(root);
    }

    private String getStr(Node node) {
        if (node == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("{ parent:").append(node.getParent());
        builder.append(",k:" + node.getK());
        builder.append(",v:" + node.getV());


        if (node.getLeft() != null) {
            builder.append(",left:").append(getStr(node.getLeft()));
        } else {
            builder.append(",left:").append(node.getLeft());
        }
        if (node.getRight() != null) {
            builder.append(",right:").append(getStr(node.getRight()));
        } else {
            builder.append(",right:").append(node.getRight());
        }
        builder.append(" }");
        return builder.toString();
    }
}
