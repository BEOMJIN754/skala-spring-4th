package com.onlineshop.shop1.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LogoutResponse {

    private String message;

    public static LogoutResponse success() {
        return new LogoutResponse(
                "로그아웃되었습니다."
        );
    }
}