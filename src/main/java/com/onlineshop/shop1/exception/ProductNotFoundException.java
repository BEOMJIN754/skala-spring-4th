package com.onlineshop.shop1.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(Long id){
        super("해당하는 상품이 없습니다.");
    }
}
