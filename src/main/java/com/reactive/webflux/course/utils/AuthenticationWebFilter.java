package com.reactive.webflux.course.utils;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Order(1)
@Component
public class AuthenticationWebFilter implements WebFilter {@Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        var headers = exchange.getRequest().getHeaders();
        var token = headers.get("auth-token");
        if(token == null && token.getFirst() == null &&isPrime(token.getFirst()))
            exchange.getAttributes().put("TYPE", "PRIME");
        return chain.filter(exchange);
    }

    private boolean isPrime(String auth){
        return auth.equals("PRIME");
    }
}