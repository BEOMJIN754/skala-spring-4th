package com.onlineshop.shop1.domain.customer.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.onlineshop.shop1.domain.customer.dto.CustomerDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(
        name = "유저 정보 관리",
        description = "유저 정보 조회, 수정 및 삭제 API"
)
public interface CustomerSwagger {

    @Operation(
            summary = "사용자 조회 (Id)",
            description = "사용자 PK ID를 기반으로 사용자 정보를 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "사용자 조회 성공",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = CustomerDto.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "사용자를 찾을 수 없음"
                    )
            }
    )
    ResponseEntity<CustomerDto> getCustomerById(
            @Parameter(
                    description = "조회할 사용자의 PK ID",
                    required = true,
                    example = "1"
            )
            Long id
    );

    @Operation(
            summary = "사용자 전체 조회",
            description = "등록된 사용자 전체 목록을 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "사용자 전체 조회 성공",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = CustomerDto.class
                                            )
                                    )
                            )
                    )
            }
    )
    ResponseEntity<List<CustomerDto>> getAllCustomer();

    @Operation(
            summary = "사용자 조회 (CustomerId)",
            description = "사용자의 customerId를 기반으로 사용자 정보를 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "사용자 조회 성공",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = CustomerDto.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "사용자를 찾을 수 없음"
                    )
            }
    )
    ResponseEntity<CustomerDto> getCustomerByName(
            @Parameter(
                    description = "조회할 사용자의 customerId",
                    required = true,
                    example = "customer01"
            )
            String customerId
    );

    @Operation(
            summary = "사용자 정보 변경",
            description = "사용자의 customerId와 포인트 정보를 변경합니다.",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(
                            schema = @Schema(
                                    implementation = CustomerDto.class
                            ),
                            examples = @ExampleObject(
                                    value = """
                                            {
                                              "customerId": "updatedCustomer",
                                              "customerPoint": 10000
                                            }
                                            """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "사용자 정보 변경 성공",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = CustomerDto.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "요청값 검증 실패"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "사용자를 찾을 수 없음"
                    )
            }
    )
    ResponseEntity<CustomerDto> updateCustomer(
            @Parameter(
                    description = "수정할 사용자의 PK ID",
                    required = true,
                    example = "1"
            )
            Long id,

            CustomerDto customerDto
    );

    @Operation(
            summary = "사용자 삭제",
            description = "사용자 PK ID를 기반으로 사용자 정보를 삭제합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "사용자 삭제 성공"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "사용자를 찾을 수 없음"
                    )
            }
    )
    ResponseEntity<Void> deleteCustomer(
            @Parameter(
                    description = "삭제할 사용자의 PK ID",
                    required = true,
                    example = "1"
            )
            Long id
    );
}