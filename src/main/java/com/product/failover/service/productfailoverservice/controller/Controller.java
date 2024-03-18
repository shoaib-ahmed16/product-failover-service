package com.product.failover.service.productfailoverservice.controller;

import com.product.failover.service.productfailoverservice.exception.ProductNullObjectException;
import com.product.failover.service.productfailoverservice.dto.ProductDto;
import com.product.failover.service.productfailoverservice.dto.TaxDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
@RestController("/api/v1/product")
public class Controller {


    @GetMapping("/{sku}")
    public ResponseEntity<ProductDto> getProductBySku(@PathVariable("sku") String sku){
        log.info("Initiating Product retrieval by SKU: {}", sku);
        if(Objects.isNull(sku)|| sku.trim().equals("")){
            log.error("Received an empty or null value for 'productSku' :{}. Unable to proceed with this request.",sku);
            throw new ProductNullObjectException("ProductImpFieldDto updating");
        }
        ProductDto productDto =ProductDto.builder()
                .productId(123L)
                .price(500.00)
                .sku("Levis-01")
                .taxes(new ArrayList<>(Arrays.asList(TaxDto.builder()
                                .taxId(1L)
                                .type("CGST")
                                .displayName("C-GST")
                                .percent(18.0)
                                .code("Ind-cgst")
                        .build())))
                .build();
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }
}
