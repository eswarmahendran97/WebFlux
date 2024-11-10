package com.reactive.webflux.flux;

import reactor.core.publisher.Flux;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CreateFlux {




    public static void main(String[] args) throws Exception {

        Flux<String> flux = Flux.create(sink -> {
            // Open the sink to start emitting values
            sink.next("Hello");
            sink.next("World");

            // Indicate completion
            sink.complete();
        });

        flux.subscribe(System.out::println);

        try {
            var itemMono = ReactiveSources.unResponsiveMono().block(Duration.ofSeconds(5));
        } catch (Exception exception){
            System.out.println(exception);
        }

        var fl = ReactiveSources.intNumberStream().log().subscribe(System.out::println);
        var item = ReactiveSources.intNumberStream().toStream().toList();
        System.out.println(item);

    }



}
