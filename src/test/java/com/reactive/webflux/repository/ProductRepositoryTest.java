package com.reactive.webflux.repository;

import com.reactive.webflux.AbstractTest;
import com.reactive.webflux.sec01.model.Product;
import com.reactive.webflux.sec01.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import java.time.Duration;

public class ProductRepositoryTest extends AbstractTest {

    @Autowired
    ProductRepository productRepository;

    @Test
    public void findByPriceTest() throws InterruptedException {
        var range = productRepository.findByPriceBetween(1000, 2000);
        range.subscribe(System.out::println);
//        Thread.sleep(5000);
    }

    @Test
    public void findByPagable() throws InterruptedException {
        var range = productRepository.findBy(Pageable.ofSize(3)).map(Product::getPrice);
        range.subscribe(System.out::println);
//        Thread.sleep(5000);
    }

}
