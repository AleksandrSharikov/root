package com.hstat.tgb.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
     //   RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration("redis", 6379);
        return new JedisConnectionFactory();//configuration);
    }

    @Bean
    public RedisTemplate<Long, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<Long, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }

}
