package com.onlineshop.shop1.domain.product.entity;

import com.onlineshop.shop1.exception.InsufficientException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String productName;

    @Column(nullable = false)
    private Double productPrice;

    @Column
    private int quantity;

    public void update(String productName, Double productPrice, int quantity) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.quantity = quantity;
    }

    public void decreaseStock(int quantity2) {
        if (quantity < quantity2) throw new InsufficientException(quantity, quantity);
        this.quantity -= quantity2;
    }
        public void increaseStock(int quantity2) {
        this.quantity += quantity2;
    }
}
