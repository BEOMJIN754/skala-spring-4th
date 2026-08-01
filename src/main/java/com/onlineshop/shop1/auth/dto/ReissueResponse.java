package com.onlineshop.shop1.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReissueResponse {

    private String tokenType;
    private String accessToken;
    private String refreshToken;
    private long accessTokenExpiration;
    private long refreshTokenExpiration;

    public static ReissueResponse of(
            String accessToken,
            String refreshToken,
            long accessTokenExpiration,
            long refreshTokenExpiration
    ) {
        return ReissueResponse.builder()
                .tokenType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenExpiration(accessTokenExpiration)
                .refreshTokenExpiration(refreshTokenExpiration)
                .build();
    }
}