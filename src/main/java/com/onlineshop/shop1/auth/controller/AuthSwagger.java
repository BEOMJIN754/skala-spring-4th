package com.onlineshop.shop1.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import com.onlineshop.shop1.auth.dto.request.LoginRequest;
import com.onlineshop.shop1.auth.dto.request.ReissueRequest;
import com.onlineshop.shop1.auth.dto.request.SignupRequest;
import com.onlineshop.shop1.auth.dto.response.LoginResponse;
import com.onlineshop.shop1.auth.dto.response.LogoutResponse;
import com.onlineshop.shop1.auth.dto.response.ReissueResponse;
import com.onlineshop.shop1.auth.dto.response.SignupResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "인증",
        description = "회원가입, 로그인, 토큰 재발급 및 로그아웃 API"
)
public interface AuthSwagger {

    @SecurityRequirements
    @Operation(
            summary = "회원가입",
            description = "사용자 아이디와 비밀번호를 입력하여 회원가입합니다.",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = SignupRequest.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "회원가입 성공",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = SignupResponse.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "요청값 검증 실패 또는 잘못된 요청"
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "이미 존재하는 사용자 아이디"
                    )
            }
    )
    ResponseEntity<SignupResponse> signup(SignupRequest request);

    @SecurityRequirements
    @Operation(
            summary = "로그인",
            description = "사용자 아이디와 비밀번호를 검증하고 Access Token과 Refresh Token을 발급합니다.",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = LoginRequest.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "로그인 성공",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = LoginResponse.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "요청값 검증 실패"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "아이디 또는 비밀번호 불일치"
                    )
            }
    )
    ResponseEntity<LoginResponse> login(LoginRequest request);

    @SecurityRequirements
    @Operation(
            summary = "Access Token 재발급",
            description = "유효한 Refresh Token을 사용하여 새로운 Access Token을 발급합니다.",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = ReissueRequest.class
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Access Token 재발급 성공",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ReissueResponse.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Refresh Token이 요청에 포함되지 않음"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Refresh Token이 유효하지 않거나 만료됨"
                    )
            }
    )
    ResponseEntity<ReissueResponse> reissue(ReissueRequest request);

    @Operation(
            summary = "로그아웃",
            description = "저장된 Refresh Token을 삭제하고 현재 Access Token을 블랙리스트에 등록하여 무효화합니다.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "로그아웃 성공",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = LogoutResponse.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Authorization 헤더 형식 오류"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "인증 정보가 없거나 Access Token이 유효하지 않음"
                    )
            }
    )
    ResponseEntity<LogoutResponse> logout(
            @Parameter(hidden = true)
            Authentication authentication,

            @Parameter(
                    description = "Bearer 형식의 Access Token",
                    required = true,
                    example = "Bearer eyJhbGciOiJIUzI1NiJ9..."
            )
            String authorizationHeader
    );
}