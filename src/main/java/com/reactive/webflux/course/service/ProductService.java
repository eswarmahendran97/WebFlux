package com.reactive.webflux.course.service;

import com.reactive.webflux.course.model.Product;
import com.reactive.webflux.course.repository.ProductRepository;
import com.reactive.webflux.course.utils.FileWriter;
import com.reactive.webflux.course.validator.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.nio.file.Path;
import java.time.Duration;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    Sinks.Many<Product> sink;

    public Flux<String> getAllProduct(){
        return productRepository.findAll().map(Product::getDescription);
    }

    public Flux<Product> saveProduct(Flux<Product> productFlux){
        return productFlux.doOnNext(System.out::println)
        .transform(RequestValidator.validateProduct())
        .as(productRepository::saveAll)
        .doOnNext(result -> sink.tryEmitNext(result));
    }

    public Mono<Product> saveSingleProduct(Mono<Product> productFlux){
        return productFlux.flatMap(productRepository::save).doOnNext(result -> sink.tryEmitNext(result));
    }

    public Flux<Product> getSSE(){
        return sink.asFlux().delayElements(Duration.ofSeconds(1));
    }

    public void download(){
        var productFlux = getAllProduct();
        FileWriter.create(productFlux, Path.of("product.txt")).doOnSuccess(System.out::println);
    }
}