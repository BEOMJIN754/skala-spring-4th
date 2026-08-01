package com.onlineshop.shop1.domain.order.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.onlineshop.shop1.domain.order.dto.request.OrderRequestDto;
import com.onlineshop.shop1.domain.order.dto.response.OrderResponseDto;

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
        name = "주문",
        description = "상품 주문 생성, 사용자 주문 조회 및 주문 취소 API"
)
public interface OrderSwagger {

    @Operation(
            summary = "주문하기",
            description = "사용자와 상품 정보를 기반으로 상품 주문을 생성합니다.",
            requestBody = @RequestBody(
                    required = true,
                    description = "주문 생성에 필요한 사용자 ID, 상품 ID 및 주문 수량",
                    content = @Content(
                            schema = @Schema(
                                    implementation = OrderRequestDto.class
                            ),
                            examples = @ExampleObject(
                                    name = "주문 생성 요청 예시",
                                    value = """
                                            {
                                              "customerId": 1,
                                              "productId": 1,
                                              "quantity": 2
                                            }
                                            """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "주문 생성 성공",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = OrderRequestDto.class
                                    ),
                                    examples = @ExampleObject(
                                            name = "주문 생성 응답 예시",
                                            value = """
                                                    {
                                                      "customerId": 1,
                                                      "productId": 1,
                                                      "quantity": 2
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "요청값 검증 실패 또는 잘못된 주문 요청"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "사용자 또는 상품을 찾을 수 없음"
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "사용자 포인트 부족 또는 상품 재고 부족"
                    )
            }
    )
    ResponseEntity<OrderRequestDto> createOrder(
            OrderRequestDto orderDto
    );

    @Operation(
            summary = "사용자 주문 상품 조회",
            description = "사용자 PK ID를 기반으로 해당 사용자의 전체 주문 상품 목록을 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "사용자 주문 목록 조회 성공",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = OrderResponseDto.class
                                            )
                                    ),
                                    examples = @ExampleObject(
                                            name = "사용자 주문 목록 응답 예시",
                                            value = """
                                                    [
                                                      {
                                                        "orderId": 1,
                                                        "customerId": 1,
                                                        "productId": 1,
                                                        "quantity": 2
                                                      },
                                                      {
                                                        "orderId": 2,
                                                        "customerId": 1,
                                                        "productId": 3,
                                                        "quantity": 1
                                                      }
                                                    ]
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "사용자를 찾을 수 없거나 주문 내역이 없음"
                    )
            }
    )
    ResponseEntity<List<OrderResponseDto>> findProductbyCustomerId(
            @Parameter(
                    description = "주문 목록을 조회할 사용자의 PK ID",
                    required = true,
                    example = "1"
            )
            Long id
    );

    @Operation(
            summary = "주문 취소",
            description = "주문 PK ID를 기반으로 주문을 취소합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "주문 취소 성공"
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "잘못된 주문 취소 요청"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "주문을 찾을 수 없음"
                    )
            }
    )
    ResponseEntity<Void> cancelOrder(
            @Parameter(
                    description = "취소할 주문의 PK ID",
                    required = true,
                    example = "1"
            )
            Long id
    );
}