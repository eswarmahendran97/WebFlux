package com.reactive.webflux.course.repository;

import com.reactive.webflux.course.model.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {
    Flux<Customer> findBy(Pageable page);
}
