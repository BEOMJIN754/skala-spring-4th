package com.onlineshop.shop1.auth.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.onlineshop.shop1.auth.dto.request.LoginRequest;
import com.onlineshop.shop1.auth.dto.request.ReissueRequest;
import com.onlineshop.shop1.auth.dto.request.SignupRequest;
import com.onlineshop.shop1.auth.dto.response.LoginResponse;
import com.onlineshop.shop1.auth.dto.response.LogoutResponse;
import com.onlineshop.shop1.auth.dto.response.ReissueResponse;
import com.onlineshop.shop1.auth.dto.response.SignupResponse;
import com.onlineshop.shop1.auth.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController implements AuthSwagger {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@Valid @RequestBody SignupRequest request) {
        SignupResponse response = authService.signup(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reissue")
    public ResponseEntity<ReissueResponse> reissue(@Valid @RequestBody ReissueRequest request) {
        ReissueResponse response = authService.reissue(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(
            Authentication authentication,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader) {
        String accessToken = extractBearerToken(authorizationHeader);

        LogoutResponse response = authService.logout(
                authentication.getName(),
                accessToken);

        return ResponseEntity.ok(response);
    }

    private String extractBearerToken(
            String authorizationHeader) {
        if (authorizationHeader == null
                || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException(
                    "Access Token이 필요합니다.");
        }

        return authorizationHeader.substring(7);
    }
}