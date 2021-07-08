package com.jjtest.user.po.tree;

public abstract class AbstractTree<K, V> {

    public int count = 0;

    /**
     * 查询
     *
     * @return
     */
    public abstract Node find(K k);

    /**
     * 插入
     *
     * @param o
     */
    public abstract boolean insert(K k, V v);

    /**
     * 删除
     *
     * @param o
     */
    public abstract boolean delete(K k);

    /**
     * 节点个数
     *
     * @return
     */
    public int count() {
        return this.count;
    }
}
