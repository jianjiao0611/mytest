package com.jjtest.user.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "spring.redis")
@Data
public class RedisProperties {

    private int database = 0; // Database index used by the connection factory
    private String url; // 连接URL，重写host，post和password，忽略用户名，如：redis://user:password@example.com:6379
    private String host = "localhost"; // Redis server host
    private String password; // Login password of the redis server
    private int port = 6379; // Redis server port.
    private boolean ssl; // Whether to enable SSL support.
    private Duration timeout; //Connection timeout.
    private Sentinel sentinel;
    private Cluster cluster;
    private final Jedis jedis = new Jedis();
    private final Lettuce lettuce = new Lettuce();

    private int maxIdle;
    // getter and setter...
    public static class Pool { // 连接池配置属性
        private int maxIdle = 8; //  连接池内空闲连接的最大数量，使用负值表示没有限制
        private int minIdle = 0; // 连接池内维护的最小空闲连接数，值为正时才有效
        private int maxActive = 8; // 给定时间连接池内最大连接数，使用负值表示没有限制
        private Duration maxWait = Duration.ofMillis(-1); // 当池耗尽时，在抛出异常之前，连接分配应阻塞的最长时间。使用负值可无限期阻止
         // getter and setter...
    }
    // Cluster properties.
    public static class Cluster {
        private List<String> nodes; // 逗号分隔的"host:port"列表，至少要有一个
        private Integer maxRedirects; // 在集群中执行命令时要遵循的最大重定向数
        // getter and setter...
    }
    // Redis sentinel properties.
    public static class Sentinel {
        private String master; // Name of the Redis server.
        private List<String> nodes; // Comma-separated list of "host:port" pairs.
         // getter and setter...
    }
    // Jedis client properties.
    public static class Jedis {
        private Pool pool; // Jedis pool configuration.
        // getter and setter...
    }
    // Lettuce client properties.
    public static class Lettuce {
        private Duration shutdownTimeout = Duration.ofMillis(100); // Shutdown timeout.
        private Pool pool; // Lettuce pool configuration.
         // getter and setter...
    }
}
