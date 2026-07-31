package com.onlineshop.shop1.domain.customer.dto;

import com.onlineshop.shop1.domain.customer.entity.Customer;

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
public class CustomerDto {

    private Long id;

    @NotNull(message = "포인트는 필수입니다." )
    @Positive(message = "가격은 양수여야 합니다.")
    private Double customerPoint;

    @NotBlank(message = "아이디는 필수입니다.")
    private String customerId;

    public static CustomerDto from(Customer customer){
        return CustomerDto.builder()
                .id(customer.getId())
                .customerPoint(customer.getCustomerPoint())
                .customerId(customer.getCustomerId())
                .build(); 
    }
}
