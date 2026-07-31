package com.onlineshop.shop1.domain.customer.entity;

import java.time.LocalDateTime;

import com.onlineshop.shop1.exception.InsufficientException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customers")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String customerId;

    @Column(nullable = false)
    private String customerPw;

    @Column(nullable = false)
    private double customerPoint;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CustomerRole role;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public void update(String customerId, Double customerPoint) {
        this.customerId = customerId;
        this.customerPoint = customerPoint;
    }

    public void usePoint(double totalprice) {
        if (customerPoint < totalprice)
            throw new InsufficientException(customerPoint, totalprice);
        this.customerPoint -= totalprice;
    }

    public void returnPoint(double totalprice) {
        this.customerPoint += totalprice;
    }
}
