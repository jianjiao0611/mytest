package com.jjtest.tool.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;

import java.util.Collections;

/**
 * redis 分布式锁
 */
@Service
public class DistributedLock {

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";
    /**
     * 释放锁 lua脚本
     */
    private static final String UNLOCK_LUA = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 尝试获取分布式锁
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间（毫秒）
     * @return 是否获取成功
     */
    public  boolean tryGetDistributedLock(String lockKey, String requestId, int expireTime) {
        try {
            return (boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {

                    String result = null;
                    Object nativeConnection = redisConnection.getNativeConnection();
                    if (nativeConnection instanceof JedisCluster) {
                        result = ((JedisCluster) nativeConnection).set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
                    }
                    if (nativeConnection instanceof Jedis) {
                        result = ((Jedis) nativeConnection).set(lockKey, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, expireTime);
                    }
                    return LOCK_SUCCESS.equals(result);
                }
            });
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 获取分布式锁
     * @param lockKey 锁
     * @param requestId 请求标识
     * @param expireTime 超期时间（毫秒）
     * @param retryNum 重试次数
     * @param retryTime 每次重试暂停时间(毫秒)
     * @return 是否获取成功
     */
    public boolean tryLock(String lockKey, String requestId, int expireTime, int retryNum, int retryTime) {
        try {
            int num = 0;
            while (num < retryNum) {
                boolean b = tryGetDistributedLock(lockKey, requestId, expireTime);
                if (b) {
                    return b;
                }
                Thread.sleep(retryTime);
                num++;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 释放分布式锁
     * @param lockKey 锁
     * @param requestId 请求标识
     * @return 是否释放成功
     */
    public  boolean releaseDistributedLock(String lockKey, String requestId) {
        try {
            return (Boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
                @Override
                public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    Long result = 0L;
                    Object nativeConnection = redisConnection.getNativeConnection();
                    if (nativeConnection instanceof JedisCluster) {
                        result = (Long) ((JedisCluster) nativeConnection).eval(UNLOCK_LUA, Collections.singletonList(lockKey), Collections.singletonList(requestId));
                    }
                    if (nativeConnection instanceof Jedis) {
                        result = (Long) ((Jedis) nativeConnection).eval(UNLOCK_LUA, Collections.singletonList(lockKey), Collections.singletonList(requestId));
                    }
                    return result == 1L;
                }
            });
        } catch (Exception e) {
            return false;
        }
    }
}
