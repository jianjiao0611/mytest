package com.jjtest.tool.util.threadpool;

import lombok.Data;

/**
 * 线程池参数
 */
@Data
public class ThreadPoolParam {

    /**
     * 线程池核心线程数
     */
    private int coreSize;
    /**
     * 线程池最大数
     */
    private int maxSize;
    /**
     * 存放任务队列大小
     */
    private int queueCapacity;
    /**
     * 空闲线程存活时间
     */
    private int keepAliveSeconds;
}
