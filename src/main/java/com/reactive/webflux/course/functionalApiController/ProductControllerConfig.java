package com.reactive.webflux.course.functionalApiController;

import com.reactive.webflux.course.requestHandler.ProductRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ProductControllerConfig {

    @Autowired
    ProductRequestHandler productRequestHandler;

    @Bean
    public RouterFunction<ServerResponse> productRoute(){
        return RouterFunctions.route()
        .GET("product", e -> productRequestHandler.getAllProduct(e))
        .POST("product", e -> productRequestHandler.saveAllProduct(e))
        .build();

    }

}