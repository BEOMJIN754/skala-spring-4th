package com.onlineshop.shop1.auth.service;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private static final String REFRESH_TOKEN_PREFIX = "refresh:";

    private final StringRedisTemplate redisTemplate;

    /** (공부 메모)
     * Refresh Token 저장
     *
     * @param customerId 사용자 아이디
     * @param refreshToken Refresh Token
     * @param expirationMillis Refresh Token 남은 유효시간(ms)
     */
    public void save(
            String customerId,
            String refreshToken,
            long expirationMillis
    ) {
        String key = createKey(customerId);
        // 로그인, 로그아웃 하지 않으면 토큰 데이터가 Redis 에 남기에 시간이 지나면 자동 삭제되는 로직
        redisTemplate.opsForValue().set(
                key,
                refreshToken,
                Duration.ofMillis(expirationMillis)
        );
    }

    // 저장된 Refresh Token 조회
    public String findByCustomerId(String customerId) {
        String key = createKey(customerId);

        return redisTemplate.opsForValue().get(key);
    }


    public boolean matches(String customerId, String refreshToken) {
        String savedRefreshToken = findByCustomerId(customerId);

        return savedRefreshToken != null
                && savedRefreshToken.equals(refreshToken);
    }

    public void delete(String customerId) {
        String key = createKey(customerId);

        redisTemplate.delete(key);
    }

    private String createKey(String customerId) {
        return REFRESH_TOKEN_PREFIX + customerId;
    }
}