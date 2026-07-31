package com.onlineshop.shop1.exception;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(Long id){
        super("해당 주문이 존재하지 않습니다: "+id);
    }
}
