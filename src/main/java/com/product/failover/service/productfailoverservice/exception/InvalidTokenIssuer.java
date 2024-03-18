package com.product.failover.service.productfailoverservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidTokenIssuer extends RuntimeException{
    public InvalidTokenIssuer(String message){
        super(message);
    }
}