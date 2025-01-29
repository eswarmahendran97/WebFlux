package com.reactive.webflux.course.requestHandler;

import com.reactive.webflux.course.exception.ApplicationExceptions;
import com.reactive.webflux.course.model.Customer;
import com.reactive.webflux.course.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class CustomerRequestHandler {

    @Autowired
    CustomerService customerService;

    public Mono<ServerResponse> getCustomerFlux(ServerRequest serverRequest) {
        var delayedCustomerFlux = customerService.getCustomerFlux().flatMap(customer -> Mono.just(customer)  // Wrap each customer in a Mono
            .delayElement(Duration.ofSeconds(2)));
        return ServerResponse.ok()
            .body(delayedCustomerFlux, String.class);
    }

    public Mono<ServerResponse> streamCustomerFlux(ServerRequest serverRequest) {
        var delayedCustomerFlux = customerService.getCustomerFlux().delayElements(Duration.ofSeconds(2));
        return ServerResponse.ok()
            .contentType(MediaType.TEXT_EVENT_STREAM)
            .body(delayedCustomerFlux, String.class);
    }

    public Mono<ServerResponse> getDelayCustomerFlux(ServerRequest serverRequest) {
        var delayedCustomerFlux = customerService.getCustomerDelayFlux().flatMap(customer -> Mono.just(customer)  // Wrap each customer in a Mono
            .delayElement(Duration.ofSeconds(2)));
        return ServerResponse.ok()
            .body(delayedCustomerFlux, String.class);
    }

    public Mono<ServerResponse> getCustomerFluxPaginated(ServerRequest serverRequest) {
        int page = serverRequest.queryParam("page").map(Integer::parseInt).orElse(1);
        int size = serverRequest.queryParam("size").map(Integer::parseInt).orElse(1);
        return customerService.getCustomerFlux(page, size)
                .delayElements(Duration.ofSeconds(1))
                .as(flux -> ServerResponse.ok().body(flux, java.lang.String.class));
    }

    public Mono<ServerResponse> saveCustomerFlux(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Customer.class).as(req -> customerService.saveCustomer(req))
                .switchIfEmpty(ApplicationExceptions.customerException("Error while saving customer"))
                .flatMap(ServerResponse.ok()::bodyValue);
    }


    public Mono<ServerResponse> calculate(ServerRequest serverRequest){
        var a = Integer.parseInt(serverRequest.pathVariable("a"));
        var b = Integer.parseInt(serverRequest.pathVariable("b"));
        String operator = serverRequest.headers().header("operator").getFirst();

        var data = switch(operator){
            case "+" -> a+b;
            case "-" -> a-b;
            case "*" -> a*b;
            case "/" -> a/b;
            default -> throw new IllegalStateException("Unexpected value: " + operator);
        };

        return ServerResponse.ok()
            .bodyValue(data);
    }
}