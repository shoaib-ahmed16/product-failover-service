package com.product.failover.service.productfailoverservice.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProductNullObjectException extends  RuntimeException{
    private static String message =" Product Cannot be Null!!!";
    public ProductNullObjectException(String addone){
        super(addone+message);
    }
}
