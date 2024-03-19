package com.product.failover.service.productfailoverservice.controller;

import com.product.failover.service.productfailoverservice.dto.ProductDto;
import com.product.failover.service.productfailoverservice.dto.TaxDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Arrays;

@Slf4j
@Component
public class ProductController {

    public Mono<ServerResponse> getProductBySku(ServerRequest request) {

        String sku =request.pathVariable("sku");

        ProductDto productDto = getProductFromDatabase(sku);

        if (productDto != null) {
            return ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_STREAM_JSON)
                    .bodyValue(productDto);
        } else {
            return ServerResponse.notFound().build();
        }
    }

    private ProductDto getProductFromDatabase(String sku) {
        // Your logic to retrieve product from database
        // This is just a mock implementation
        return ProductDto.builder()
                .productId(123L)
                .price(500.00)
                .sku(sku)
                .taxes(Arrays.asList(
                        TaxDto.builder()
                                .taxId(1L)
                                .type("CGST")
                                .displayName("C-GST")
                                .percent(18.0)
                                .code("Ind-cgst")
                                .build()
                ))
                .build();
    }
}
