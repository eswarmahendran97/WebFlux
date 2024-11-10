package com.reactive.webflux.sec01.controller;

import com.reactive.webflux.sec01.model.Customer;
import com.reactive.webflux.sec01.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @RequestMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getCustomerFlux(){
        return customerService.getCustomerFlux().delayElements(Duration.ofSeconds(1));
    }

    @PostMapping
    public Mono<Customer> getCustomerFlux(@RequestBody Mono<Customer> customerMono){
        return customerService.saveCustomer(customerMono);
    }

}
