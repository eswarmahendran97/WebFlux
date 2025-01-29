package com.reactive.webflux.course;

import com.reactive.webflux.course.model.Product;
import com.reactive.webflux.course.service.ProductService;
import com.reactive.webflux.course.test.TestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class Setup implements CommandLineRunner {

    @Autowired
    ProductService productService;

    @Override
    public void run(String... args) {
        var products = Flux.range(0, 100).map(i -> {
            var product = new Product();
            product.setDescription("Iphone" + i);
            product.setPrice(ThreadLocalRandom.current().nextInt(1, 100));
            return product;
        }).delayElements(Duration.ofSeconds(2));
        productService.saveProduct(products).subscribe();
    }
}