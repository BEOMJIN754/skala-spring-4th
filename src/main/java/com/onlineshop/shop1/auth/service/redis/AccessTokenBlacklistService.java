package com.onlineshop.shop1.auth.service.redis;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccessTokenBlacklistService {

    private static final String BLACKLIST_PREFIX = "blacklist:";

    private final StringRedisTemplate redisTemplate;

    public void add(
            String accessToken,
            long remainingExpirationMillis
    ) {
        if (remainingExpirationMillis <= 0) {
            return;
        }

        redisTemplate.opsForValue().set(
                createKey(accessToken),
                "logout",
                Duration.ofMillis(remainingExpirationMillis)
        );
    }

    public boolean contains(String accessToken) {
        return Boolean.TRUE.equals(
                redisTemplate.hasKey(createKey(accessToken))
        );
    }

    private String createKey(String accessToken) {
        return BLACKLIST_PREFIX + accessToken;
    }
}