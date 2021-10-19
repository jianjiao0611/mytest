package com.jjtest.taskcenter.task.service;

/**
 * 清洗步骤抽象类
 * @param <T> 原始数据1
 * @param <S> 原始数据2
 * @param <R> 结果
 */
public abstract class AbsCleanDataHandler<T, S, R> {

    private AbsCleanDataHandler nextCleanDataHandler;

    protected  void handler(T object1, S object2, R result) {
        this.doHandler(object1, object2, result);
        this.nextHandler(object1, object2, result);
    }

    protected void nextHandler(T object1, S object2, R result) {
        if (this.nextCleanDataHandler == null) {
            return;
        }
        this.nextCleanDataHandler.handler(object1, object2, result);
    }

    protected void setNextCleanDataHandler(AbsCleanDataHandler nextCleanDataHandler) {
        this.nextCleanDataHandler = nextCleanDataHandler;
    }

    protected abstract void doHandler(T object1, S object2, R result);

}
