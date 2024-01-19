package com.wonhyong.talk.Redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void saveRefreshToken(String username, String refreshToken) {
        String key = "refreshToken:" + username;
        redisTemplate.opsForValue().set(key, refreshToken, 12, TimeUnit.HOURS);
    }

    public String getRefreshToken(String username) {
        String key = "refreshToken:" + username;
        return redisTemplate.opsForValue().get(key);
    }

    public void deleteRefreshToken(String username) {
        String key = "refreshToken:" + username;
        redisTemplate.delete(key);
    }
}
