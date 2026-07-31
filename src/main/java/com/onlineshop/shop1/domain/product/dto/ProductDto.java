package com.onlineshop.shop1.domain.product.dto;

import com.onlineshop.shop1.domain.product.entity.Product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDto {

    private Long id;

    @NotBlank(message = "상품 이름은 필수입니다.")
    private String productName;

    @NotNull(message = "상품 가격은 필수입니다.")
    @Positive(message = "상품 가격은 양수여야 합니다.")
    private Double productPrice;

    @Positive(message = "상품 개수는 양수여야 합니다.")
    private int quantity;

    public static ProductDto from(Product product){
        return ProductDto.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .productPrice(product.getProductPrice())
                .quantity(product.getQuantity())
                .build();
    }

}
