package com.product.failover.service.productfailoverservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidTokenSubject extends RuntimeException{
    public InvalidTokenSubject(String message){
        super(message);
    }
}