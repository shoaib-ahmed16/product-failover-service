package com.product.failover.service.productfailoverservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private  Long productId;
    private Double price;
    private String sku;
    private String name;
    private String type;
    private Boolean isTaxable;
    private String imageUrl;
    private List<TaxDto> taxes;
    private Long quantities;
    private Long thresholdQuantities;
    private Boolean isActive;
}
