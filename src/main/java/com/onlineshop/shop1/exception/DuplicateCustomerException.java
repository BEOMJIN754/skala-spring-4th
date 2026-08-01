package com.onlineshop.shop1.exception;

public class DuplicateCustomerException extends RuntimeException {

    public DuplicateCustomerException() {
        super("이미 사용 중인 아이디입니다.");
    }
}