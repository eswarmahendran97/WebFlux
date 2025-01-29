package com.reactive.webflux.course.exception;

public class ProductException extends RuntimeException {
    ProductException(String message){
        super(message);
    }
}