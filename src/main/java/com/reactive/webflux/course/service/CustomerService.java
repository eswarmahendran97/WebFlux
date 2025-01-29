package com.reactive.webflux.course.service;


import com.reactive.webflux.course.model.Customer;
import com.reactive.webflux.course.repository.CustomerRepository;
import com.reactive.webflux.course.validator.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    WebClient webReactiveClient;

    public Flux<String> getCustomerFlux(){
        var list = new ArrayList<Customer>();
        return customerRepository.findAll().map(Customer::getName);
    }

    public Mono<Customer> saveCustomer(Mono<Customer> customerMono){
        return customerMono.transform(RequestValidator.validateCustomer()).flatMap(customerRepository::save);
    }

    public Flux<String> getCustomerFlux(int page, int size){
        return customerRepository.findBy(PageRequest.of(page, size)).map(Customer::getName);
    }

    public Flux<String> getCustomerDelayFlux(){
        return webReactiveClient.get()
        .uri("/test/customerStream")
        .retrieve().bodyToFlux(String.class).doOnNext(System.out::println);
    }

}
