package com.onlineshop.shop1.exception;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Long id){
        super("해당하는 유저가 없습니다. :"+id);
    }
    public CustomerNotFoundException(String customerId){
        super("해당하는 유저가 없습니다. :"+ customerId);
    }
}
