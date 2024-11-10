package com.reactive.webflux.repository;

import com.reactive.webflux.AbstractTest;
import com.reactive.webflux.sec01.model.Customer;
import com.reactive.webflux.sec01.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

public class CustomerRepositoryTest extends AbstractTest {

    @Autowired
    CustomerRepository customerRepository;

    @Test
    public void testCustomerRepository(){
        customerRepository.findAll().map(Customer::getName)
                .as(StepVerifier::create)
                .expectNextCount(10)
                .expectComplete()
                .verify();
    }

    @Test
    public void task1(){
        customerRepository.findAll()
                .map(Customer::getEmail)
                .filter(e -> e.endsWith("ke@gmail.com"))
                .as(StepVerifier::create)
                .assertNext( e -> Assertions.assertTrue(e.endsWith("mike@gmail.com")))
                .assertNext( e -> Assertions.assertTrue(e.endsWith("jake@gmail.com")))
                .expectComplete()
                .verify();
    }
}
