package com.jjtest.tool.util.threadpool;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;

@Data
@Component
@ConfigurationProperties(prefix = "async.threadpool.param")
public class AsyncInvoke {

    private Map<String, String> config;

    private Map<String, ExecutorService> poolMap = new ConcurrentHashMap<>();

    private List<String> list;

    @PostConstruct
    public void init() {
        System.out.println(list);

        if (config == null || config.isEmpty()) {
            return;
        }
        Set<String> keyS = config.keySet();

        for (String key : keyS) {
            String poolName = key.trim();
            String paramStr = config.get(poolName);
            ThreadPoolParam threadPoolParam = JSONObject.parseObject(paramStr, ThreadPoolParam.class);
            poolMap.put(poolName, new BusinessThreadPoolExecutor(threadPoolParam, poolName));
        }
    }

    public ExecutorService getExecutorService(String poolName) {
        poolName = StringUtils.isBlank(poolName) ? "default" : poolName;
        if (!poolMap.containsKey(poolName)) {
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(10, 100,
                    60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(100));
            poolMap.putIfAbsent(poolName, threadPoolExecutor);
            return threadPoolExecutor;
        }
        return poolMap.get(poolName);
    }

    public void submit(AsyncHandler asyncHandler) {
        this.submit("default", asyncHandler);
    }

    public void submit(String poolName, AsyncHandler asyncHandler) {
        this.getExecutorService(poolName).submit(() -> {
            asyncHandler.handler();
        });
    }

}
