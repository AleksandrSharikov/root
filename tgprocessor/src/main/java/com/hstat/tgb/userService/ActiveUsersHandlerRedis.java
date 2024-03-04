package com.hstat.tgb.userService;

import com.hstat.common.CommonConstants;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Service
@Primary
public class ActiveUsersHandlerRedis implements ActiveUsersHandler {

    private final RedisTemplate<Long, Object> redisTemplate;
    private final int expiration = 1;
    private final RestTemplate restTemplate;
    private final String url;

    public ActiveUsersHandlerRedis(RedisTemplate<Long, Object> redisTemplate, RestTemplateBuilder restTemplateBuilder) {
        this.redisTemplate = redisTemplate;
        this.restTemplate = restTemplateBuilder.build();
        this.url = CommonConstants.ServicePort.USER.getPort();
    }

    @Override
    public void add(long chatId) {
        redisTemplate.opsForValue().set(chatId, null);
        redisTemplate.expire(chatId, expiration, TimeUnit.MINUTES);
    }

    @Override
    public boolean isActive(long chatId) {
        return Boolean.TRUE.equals(redisTemplate.expire(chatId, expiration, TimeUnit.MINUTES));
    }

    @Override
    public boolean askUser(long userId) {
        return Boolean.TRUE.equals(restTemplate.getForObject(url + "/tg/check/" + userId, Boolean.class)) ;

    }

    @Override
    public void remove(long chatId) {
        redisTemplate.delete(chatId);
    }
}
