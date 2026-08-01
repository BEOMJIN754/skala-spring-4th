package com.onlineshop.shop1.auth.dto.response;

import com.onlineshop.shop1.domain.customer.entity.Customer;
import com.onlineshop.shop1.domain.customer.entity.CustomerRole;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignupResponse {

    private Long id;
    private String customerId;
    private double customerPoint;
    private CustomerRole role;

    public static SignupResponse from(Customer customer) {
        return SignupResponse.builder()
                .id(customer.getId())
                .customerId(customer.getCustomerId())
                .customerPoint(customer.getCustomerPoint())
                .role(customer.getRole())
                .build();
    }
}