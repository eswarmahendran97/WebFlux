package com.reactive.webflux.course.utils;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class ReactiveSources {

    public static Flux<String> customerDelay(){
        return Flux.just("Eswar", "Saravana", "Moorthy", "Kesav").delayElements(Duration.ofSeconds(1));
    }
}
