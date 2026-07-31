package com.onlineshop.shop1.domain.order.dto.request;

import com.onlineshop.shop1.domain.order.entity.Order;

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
public class OrderRequestDto {
    
    @NotNull(message = "고객ID 는 필수입니다.")
    private Long customerId;
    @NotNull(message = "상품ID 는 필수입니다.")
    private Long productId;

    @NotNull(message = "수량은 필수입니다.")
    @Positive(message = "수량은 양수여야 합니다.")
    private int quantity;

    public static OrderRequestDto from(Order order){
        return OrderRequestDto.builder()
                .customerId(order.getCustomer().getId())
                .productId(order.getProduct().getId())
                .quantity(order.getQuantity())
                .build();
    }
}
