package com.reactive.webflux.course.exception;

import reactor.core.publisher.Mono;

public class ApplicationExceptions {

    public static <T> Mono<T> customerException(String message){
        return Mono.error(new CustomerException(message));
    }

    public static <T> Mono<T> productException(String message){
        return Mono.error(new ProductException(message));
    }

}