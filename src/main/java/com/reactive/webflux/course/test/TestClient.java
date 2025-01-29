package com.reactive.webflux.course.test;

import com.reactive.webflux.course.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class TestClient {

    @Autowired
    WebClient webReactiveClient;


    public Flux<Product> callUploadProduct() {
        var products = Flux.range(0, 100).map(i -> {
            var product = new Product();
            product.setDescription("Iphone" + i);
            product.setPrice(ThreadLocalRandom.current().nextInt(1, 100));
            return product;
        }).delayElements(Duration.ofSeconds(2));

        return webReactiveClient
                .post()
                .uri("/traditionalProduct/upload")
//                .uri("/product")
                .body(products, Product.class)
                .retrieve()
                .bodyToFlux(Product.class);
    }
}