package com.reactive.webflux.course.utils;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Order(2)
@Component
public class AuthorizationWebFilter implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var type = exchange.getAttributeOrDefault("TYPE", "Strandard");
        if(type.equals("Strandard") && !exchange.getRequest().getMethod().equals(HttpMethod.GET)){
            return Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN));
        }
        return chain.filter(exchange);
    }
}