package com.reactive.webflux.course.traditionalController;

import com.reactive.webflux.course.model.Product;
import com.reactive.webflux.course.service.ProductService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("traditionalProduct")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping(value = "upload")
    public Flux<Product> upload(@RequestBody Flux<Product> productFlux){
        System.out.println("invoked");
        return productService.saveProduct(productFlux);
    }

    @PostMapping(value = "saveSingle")
    public Mono<Product> saveSingle(@RequestBody Mono<Product> productFlux){
        System.out.println("invoked");
        return productService.saveSingleProduct(productFlux);
    }

    @GetMapping(value = "SSE/{priceFilter}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Product> sse(@PathVariable Integer priceFilter){
        return productService.getSSE().filter(product -> product.getPrice() > priceFilter);
    }

    @GetMapping(value = "download")
    public void download(){
        productService.download();
    }
}