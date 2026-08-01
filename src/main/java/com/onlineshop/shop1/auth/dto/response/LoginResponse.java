package com.onlineshop.shop1.auth.dto;

import com.onlineshop.shop1.domain.customer.entity.Customer;
import com.onlineshop.shop1.domain.customer.entity.CustomerRole;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {

    private String tokenType;
    private String accessToken;
    private String refreshToken;
    private long accessTokenExpiration;
    private long refreshTokenExpiration;

    public static LoginResponse of(
            String accessToken,
            String refreshToken,
            long accessTokenExpiration,
            long refreshTokenExpiration
    ) {
        return LoginResponse.builder()
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiration(accessTokenExpiration)
                .refreshTokenExpiration(refreshTokenExpiration)
                .build();
    }
}