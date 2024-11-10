package com.reactive.webflux.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;

@RestController
@CrossOrigin("*")
public class ReactiveController {
    private static Flux<Integer> getFromDb() {
        return Flux.range(0, 10).delayElements(Duration.ofSeconds(1));
    }

    @GetMapping(value = "/records", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Integer> getRecords() {
        return getFromDb()
                .map(rec -> {
                    System.out.println("Processed: " + (rec * 2));
                    return rec * 2;
                })
                .concatWith(Mono.just(-1));
    }
}
