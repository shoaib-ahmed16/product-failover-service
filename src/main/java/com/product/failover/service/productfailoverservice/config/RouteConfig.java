package com.product.failover.service.productfailoverservice.config;

import com.product.failover.service.productfailoverservice.controller.ProductController;
import com.product.failover.service.productfailoverservice.dto.ProductDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

//@Configuration
public class RouteConfig {
//    @Bean
//    public RouterFunction<ServerResponse> productRoute(ProductController controller){
//        return route(GET("/api/v1/fallback/product/{sku}")
//                        .and(accept(MediaType.APPLICATION_JSON)),
//                request -> {
//                    String sku = request.pathVariable("sku");
//                    ResponseEntity<ProductDto> responseEntity = controller.getProductBySku(sku);
//                    return ServerResponse.status(responseEntity.getStatusCode())
//                            .bodyValue(responseEntity.getBody());
//                });
//    }
}
