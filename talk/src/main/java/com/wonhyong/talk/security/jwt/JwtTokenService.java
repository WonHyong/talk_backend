package com.wonhyong.talk.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class JwtTokenService {
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public JwtTokenService(RedisTemplate<String, String> redisTemplate) {
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
