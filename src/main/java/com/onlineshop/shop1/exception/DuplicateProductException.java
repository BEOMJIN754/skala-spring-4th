package com.onlineshop.shop1.exception;

public class DuplicateProductException extends RuntimeException {
    public DuplicateProductException(String name){
        super("동일한 이름의 상품이 있습니다.");
    }
}
