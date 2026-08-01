package com.onlineshop.shop1.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "아이디는 필수입니다.")
    private String customerId;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String customerPw;
}
