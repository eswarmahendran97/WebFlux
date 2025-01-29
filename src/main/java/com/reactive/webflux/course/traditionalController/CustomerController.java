package com.reactive.webflux.course.traditionalController;

import com.reactive.webflux.course.service.CustomerService;
import com.reactive.webflux.course.utils.ReactiveSources;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RestController
@RequestMapping("traditionalCustomer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @RequestMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getCustomerDelayFlux(){
        return customerService.getCustomerFlux().delayElements(Duration.ofSeconds(1));
    }
}