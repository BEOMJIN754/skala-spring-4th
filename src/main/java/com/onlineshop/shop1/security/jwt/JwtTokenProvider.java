package com.onlineshop.shop1.security.jwt;

import java.time.Instant;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import com.onlineshop.shop1.domain.customer.entity.CustomerRole;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.JwtException;

@Component
public class JwtTokenProvider {

    private static final String TOKEN_TYPE_CLAIM = "tokenType";
    private static final String ROLE_CLAIM = "role";

    private static final String ACCESS_TOKEN_TYPE = "ACCESS";
    private static final String REFRESH_TOKEN_TYPE = "REFRESH";

    private final JwtProperties jwtProperties;
    private final SecretKey secretKey;

    public JwtTokenProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;

        byte[] keyBytes = Decoders.BASE64.decode(
                jwtProperties.secret());

        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createAccessToken(
            String customerId,
            CustomerRole role) {
        Instant now = Instant.now();
        Instant expiration = now.plusMillis(
                jwtProperties.accessTokenExpiration());

        return Jwts.builder()
                .subject(customerId)
                .claim(ROLE_CLAIM, role.name())
                .claim(TOKEN_TYPE_CLAIM, ACCESS_TOKEN_TYPE)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(secretKey)
                .compact();
    }

    public String createRefreshToken(String customerId) {
        Instant now = Instant.now();
        Instant expiration = now.plusMillis(
                jwtProperties.refreshTokenExpiration());

        return Jwts.builder()
                .subject(customerId)
                .claim(TOKEN_TYPE_CLAIM, REFRESH_TOKEN_TYPE)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(secretKey)
                .compact();
    }

    public Claims parseClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getCustomerId(String token) {
        return parseClaims(token).getSubject();
    }

    public String getTokenType(String token) {
        return parseClaims(token)
                .get(TOKEN_TYPE_CLAIM, String.class);
    }

    // 리프레시 만료 시간
    public long getRefreshTokenExpiration() {
        return jwtProperties.refreshTokenExpiration();
    }

    // 엑세스 만료 시간
    public long getAccessTokenExpiration() {
        return jwtProperties.accessTokenExpiration();
    }

    // 토큰 잘못들어왔을 때 검증
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }

    public boolean isRefreshToken(String token) {
        try {
            String tokenType = getTokenType(token);

            return REFRESH_TOKEN_TYPE.equals(tokenType);
        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }

    

}