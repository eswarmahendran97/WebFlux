package com.reactive.webflux.course.requestHandler;

import com.reactive.webflux.course.exception.ApplicationExceptions;
import com.reactive.webflux.course.model.Customer;
import com.reactive.webflux.course.model.Product;
import com.reactive.webflux.course.service.CustomerService;
import com.reactive.webflux.course.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class ProductRequestHandler {

    @Autowired
    ProductService productService;

    public Mono<ServerResponse> getAllProduct(ServerRequest serverRequest) {
        var flux = productService.getAllProduct().flatMap(customer -> Mono.just(customer)  // Wrap each customer in a Mono
            .delayElement(Duration.ofSeconds(2)));
        return ServerResponse.ok()
            .body(flux, String.class);
    }

    public Mono<ServerResponse> saveAllProduct(ServerRequest serverRequest) {
        var flux = serverRequest.bodyToFlux(Product.class).as(req -> productService.saveProduct(req))
                .switchIfEmpty(ApplicationExceptions.customerException("Error while saving product"));
        return ServerResponse.ok().body(flux, Product.class);
    }
}