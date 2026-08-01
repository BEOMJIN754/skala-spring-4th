package com.onlineshop.shop1.domain.product.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.onlineshop.shop1.domain.product.dto.ProductDto;

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
        name = "상품 정보 관리",
        description = "상품 정보 조회, 등록, 수정 및 삭제 API"
)
public interface ProductSwagger {

    @Operation(
            summary = "전체 상품 조회",
            description = "등록된 전체 상품 목록을 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "전체 상품 조회 성공",
                            content = @Content(
                                    array = @ArraySchema(
                                            schema = @Schema(
                                                    implementation = ProductDto.class
                                            )
                                    ),
                                    examples = @ExampleObject(
                                            name = "전체 상품 조회 응답 예시",
                                            value = """
                                                    [
                                                      {
                                                        "id": 1,
                                                        "productName": "노트북",
                                                        "productPrice": 1500000,
                                                        "productStock": 10
                                                      },
                                                      {
                                                        "id": 2,
                                                        "productName": "키보드",
                                                        "productPrice": 120000,
                                                        "productStock": 30
                                                      }
                                                    ]
                                                    """
                                    )
                            )
                    )
            }
    )
    ResponseEntity<List<ProductDto>> getAllProduct();

    @Operation(
            summary = "상품 조회 (Id)",
            description = "상품 PK ID를 기반으로 상품 정보를 조회합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "상품 조회 성공",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ProductDto.class
                                    ),
                                    examples = @ExampleObject(
                                            name = "상품 조회 응답 예시",
                                            value = """
                                                    {
                                                      "id": 1,
                                                      "productName": "노트북",
                                                      "productPrice": 1500000,
                                                      "productStock": 10
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "상품을 찾을 수 없음"
                    )
            }
    )
    ResponseEntity<ProductDto> getProductById(
            @Parameter(
                    description = "조회할 상품의 PK ID",
                    required = true,
                    example = "1"
            )
            Long id
    );

    @Operation(
            summary = "상품 추가",
            description = "상품 이름, 가격 및 재고 정보를 입력하여 새로운 상품을 등록합니다.",
            requestBody = @RequestBody(
                    required = true,
                    description = "등록할 상품 정보",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ProductDto.class
                            ),
                            examples = @ExampleObject(
                                    name = "상품 추가 요청 예시",
                                    value = """
                                            {
                                              "productName": "마우스",
                                              "productPrice": 50000,
                                              "productStock": 20
                                            }
                                            """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "상품 추가 성공",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ProductDto.class
                                    ),
                                    examples = @ExampleObject(
                                            name = "상품 추가 응답 예시",
                                            value = """
                                                    {
                                                      "id": 3,
                                                      "productName": "마우스",
                                                      "productPrice": 50000,
                                                      "productStock": 20
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "상품명, 가격 또는 재고 등 요청값 검증 실패"
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "이미 존재하는 상품명"
                    )
            }
    )
    ResponseEntity<ProductDto> addProduct(
            ProductDto productDto
    );

    @Operation(
            summary = "상품 정보 변경",
            description = "상품 PK ID를 기반으로 상품 이름, 가격 및 재고 정보를 변경합니다.",
            requestBody = @RequestBody(
                    required = true,
                    description = "변경할 상품 정보",
                    content = @Content(
                            schema = @Schema(
                                    implementation = ProductDto.class
                            ),
                            examples = @ExampleObject(
                                    name = "상품 수정 요청 예시",
                                    value = """
                                            {
                                              "productName": "게이밍 마우스",
                                              "productPrice": 70000,
                                              "productStock": 15
                                            }
                                            """
                            )
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "상품 정보 변경 성공",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = ProductDto.class
                                    ),
                                    examples = @ExampleObject(
                                            name = "상품 수정 응답 예시",
                                            value = """
                                                    {
                                                      "id": 3,
                                                      "productName": "게이밍 마우스",
                                                      "productPrice": 70000,
                                                      "productStock": 15
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "상품 정보 요청값 검증 실패"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "수정할 상품을 찾을 수 없음"
                    ),
                    @ApiResponse(
                            responseCode = "409",
                            description = "변경하려는 상품명이 이미 존재함"
                    )
            }
    )
    ResponseEntity<ProductDto> updateProduct(
            @Parameter(
                    description = "수정할 상품의 PK ID",
                    required = true,
                    example = "1"
            )
            Long id,

            ProductDto productDto
    );

    @Operation(
            summary = "상품 삭제",
            description = "상품 PK ID를 기반으로 상품 정보를 삭제합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "상품 삭제 성공"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "삭제할 상품을 찾을 수 없음"
                    )
            }
    )
    ResponseEntity<Void> deleteProduct(
            @Parameter(
                    description = "삭제할 상품의 PK ID",
                    required = true,
                    example = "1"
            )
            Long id
    );
}