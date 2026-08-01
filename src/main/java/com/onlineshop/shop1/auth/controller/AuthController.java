package com.onlineshop.shop1.auth.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlineshop.shop1.auth.dto.LoginRequest;
import com.onlineshop.shop1.auth.dto.LoginResponse;
import com.onlineshop.shop1.auth.dto.ReissueRequest;
import com.onlineshop.shop1.auth.dto.ReissueResponse;
import com.onlineshop.shop1.auth.dto.SignupRequest;
import com.onlineshop.shop1.auth.dto.SignupResponse;
import com.onlineshop.shop1.auth.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "인증", description = "회원가입 및 로그인 API")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    @Operation(summary = "회원가입", description = "사용자 아이디와 비밀번호로 회원가입합니다.")
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest request) {
        SignupResponse response = authService.signup(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    @Operation(summary = "로그인", description = "사용자 아이디와 비밀번호를 검증합니다.")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reissue")
    @Operation(summary = "Access Token 재발급", description = "유효한 Refresh Token을 사용해 새로운 Access Token을 발급합니다.")
    public ResponseEntity<ReissueResponse> reissue(@Valid @RequestBody ReissueRequest request) {
        ReissueResponse response = authService.reissue(request);
        return ResponseEntity.ok(response);
    }
}