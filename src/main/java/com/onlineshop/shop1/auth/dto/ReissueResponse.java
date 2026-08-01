package com.onlineshop.shop1.auth.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReissueResponse {

    private String tokenType;
    private String accessToken;
    private long accessTokenExpiration;

    public static ReissueResponse of(
            String accessToken,
            long accessTokenExpiration
    ) {
        return ReissueResponse.builder()
                .tokenType("Bearer")
                .accessToken(accessToken)
                .accessTokenExpiration(accessTokenExpiration)
                .build();
    }
}