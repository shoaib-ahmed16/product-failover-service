package com.product.failover.service.productfailoverservice.config;

import com.product.failover.service.productfailoverservice.controller.ProductController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouteConfig {
    @Bean
    public RouterFunction<ServerResponse> productRoute(ProductController controller){
        return route(GET("/product-failOver-micro-service")
                        .and(accept(MediaType.APPLICATION_JSON)),controller::getProductBySku);
    }


}
