package com.onlineshop.shop1.exception;

/**
 * InsufficientStockException
 */
public class InsufficientException extends RuntimeException {
    public InsufficientException(int quantity, int quantity2) {
        super("보유 상품 재고가 " + Math.abs(quantity2 - quantity) + "만큼 부족합니다.");
    }

    public InsufficientException(double point1, double point2) {
        super("보유 상품 재고가 " + Math.abs(point2 - point1) + "만큼 부족합니다.");
    }

}
