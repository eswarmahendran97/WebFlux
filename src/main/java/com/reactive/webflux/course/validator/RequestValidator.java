package com.reactive.webflux.course.validator;

import com.reactive.webflux.course.exception.ApplicationExceptions;
import com.reactive.webflux.course.model.Customer;
import com.reactive.webflux.course.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class RequestValidator {

    public static UnaryOperator<Mono<Customer>> validateCustomer(){
        return mono -> mono.filter(validateCustomerEmail()).switchIfEmpty(ApplicationExceptions.customerException("Invalid Customer"));
    }

    public static UnaryOperator<Flux<Product>> validateProduct(){
        return mono -> mono.filter(validateDescription()).switchIfEmpty(ApplicationExceptions.productException("Invalid Product"));
    }

    private static Predicate<Customer> validateCustomerEmail(){
        return obj -> Objects.nonNull(obj) && obj.getEmail() != null;
    }

    private static Predicate<Product> validateDescription(){
        return obj -> Objects.nonNull(obj) && obj.getDescription() != null;
    }

}