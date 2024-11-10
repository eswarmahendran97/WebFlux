package com.reactive.webflux.flux;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class ReactiveSources {


    public static Flux<Integer> intNumberStream(){
        return Flux.range(0, 10).delayElements(Duration.ofSeconds(1));
    }

    public static Mono<Integer> unResponsiveMono(){
        return Mono.just(0).delayElement(Duration.ofSeconds(20));
    }
}
