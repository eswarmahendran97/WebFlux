package com.reactive.webflux.course.test;


import com.reactive.webflux.course.model.Product;
import com.reactive.webflux.course.utils.ReactiveSources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    TestClient testClient;

    // Testing as different service.. used in WebClient microservice call
    @RequestMapping(value = "/customerStream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getCustomerDelayFlux(){
        return ReactiveSources.customerDelay();
    }

    // Testing as different service.. used in WebClient microservice call
    @RequestMapping(value = "/upload", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Product> testUpload(){
        return testClient.callUploadProduct();
    }


}
