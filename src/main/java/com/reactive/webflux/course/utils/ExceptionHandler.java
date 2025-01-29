package com.reactive.webflux.course.utils;

import com.reactive.webflux.course.exception.CustomerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.net.URI;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(CustomerException.class)
    public ProblemDetail handleException(CustomerException customerException) {
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, customerException.getMessage());
        problem.setType(URI.create("http://example.com"));
        problem.setDetail("Customer exception");
        return problem;
    }
}