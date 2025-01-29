package com.reactive.webflux.course.exception;

public class CustomerException extends RuntimeException{

    public CustomerException(String message){
        super(message);
    }

}