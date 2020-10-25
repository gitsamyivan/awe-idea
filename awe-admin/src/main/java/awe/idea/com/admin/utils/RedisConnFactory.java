package awe.idea.com.admin.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPoolConfig;

@Component
public class RedisConnFactory {
    @Value("${awe.idea.redis.host:127.0.0.1}")
    private String redisHost;
    @Value("${awe.idea.redis.port:6379}")
    private Integer redisPort;
    @Value("${awe.idea.redis.password:}")
    private String redisPassword;
    @Value("${awe.idea.redis.database:1}")
    private Integer redisDatabase;

    @Bean(name = "poolConfig")
    public JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(100);
        poolConfig.setMaxWaitMillis(10000);
        poolConfig.setTestOnBorrow(true);
        return poolConfig;
    }

    @Bean("connectionFactory")
    public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig poolConfig){
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory();
        connectionFactory.setPoolConfig(poolConfig);
        connectionFactory.setHostName(redisHost);
        connectionFactory.setPort(redisPort);
        connectionFactory.setPassword(redisPassword);
        connectionFactory.setDatabase(redisDatabase);
        connectionFactory.setTimeout(10000);
        return connectionFactory;
    }

    @Bean("redisTemplate")
    public RedisTemplate redisTemplate(JedisConnectionFactory jedisConnectionFactory){
        RedisTemplate redisTemplate  = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        return redisTemplate;
    }
}
