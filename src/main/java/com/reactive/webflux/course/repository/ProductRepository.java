package com.reactive.webflux.course.repository;

import com.reactive.webflux.course.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;


@Repository
public interface ProductRepository extends R2dbcRepository<Product, Integer> {
    Flux<Product> findByPriceBetween(int from, int to);

    Flux<Product> findBy(Pageable pageable);
}
