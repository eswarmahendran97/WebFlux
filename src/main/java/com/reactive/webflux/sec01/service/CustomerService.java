package com.reactive.webflux.sec01.service;

import com.reactive.webflux.sec01.model.Customer;
import com.reactive.webflux.sec01.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public Flux<String> getCustomerFlux(){
        var list = new ArrayList<Customer>();
        return customerRepository.findAll().map(e-> e.getName());
    }

    public Mono<Customer> saveCustomer(Mono<Customer> customerMono){
        return customerMono.flatMap(customerRepository::save);
    }

}
