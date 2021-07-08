package com.jjtest.tool.util.threadpool;

import lombok.Data;

import java.util.concurrent.*;

@Data
public class BusinessThreadPoolExecutor extends ThreadPoolExecutor implements ExecutorService {

    private  String poolName;


    public BusinessThreadPoolExecutor(ThreadPoolParam threadPoolParam, String poolName) {
        super(threadPoolParam.getCoreSize(), threadPoolParam.getMaxSize(), threadPoolParam.getKeepAliveSeconds(), TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(threadPoolParam.getQueueCapacity()));
        this.poolName = poolName;
    }
}
