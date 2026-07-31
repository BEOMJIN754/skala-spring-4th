package com.onlineshop.shop1.domain.order.dto.response;

import com.onlineshop.shop1.domain.order.entity.Order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDto {

    private Long orderId;
    @NotNull(message = "고객 ID는 필수입니다.")
    @Positive(message = "고객 ID는 양수여야 합니다.")
    private Long customerId;

    @NotNull(message = "상품 ID는 필수입니다.")
    @Positive(message = "상품 ID는 양수여야 합니다.")
    private Long productId;

    @NotBlank(message = "상품 이름은 필수입니다.")
    private String productName;

    @NotNull(message = "상품 가격은 필수입니다.")
    @Positive(message = "상품 가격은 양수여야 합니다.")
    private Double productPrice;

    @Positive(message = "주문 수량은 양수여야 합니다.")
    private int quantity;

    @NotNull(message = "총 가격은 필수입니다.")
    @Positive(message = "총 가격은 양수여야 합니다.")
    private Double totalPrice;

    public static OrderResponseDto from(Order order) {
        return OrderResponseDto.builder()
                .orderId(order.getId())
                .customerId(order.getCustomer().getId())
                .productId(order.getProduct().getId())
                .productName(order.getProduct().getProductName())
                .productPrice(order.getProduct().getProductPrice())
                .quantity(order.getQuantity())
                .totalPrice(
                        order.getProduct().getProductPrice()
                                * order.getQuantity())
                .build();
    }
}