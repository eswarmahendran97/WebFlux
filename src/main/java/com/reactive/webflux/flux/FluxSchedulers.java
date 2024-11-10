package com.reactive.webflux.flux;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

public class FluxSchedulers {

    public static void main(String[] args) throws InterruptedException {

        FluxSchedulers schedulers = new FluxSchedulers();
        // Simple flux which uses only one thread
//        schedulers.simpleFlux();

        // Delay operation will be performed another thread. Also, by default flux uses elasticScheduler
//        schedulers.delayFlux();

        // All Upstream data(source..Flux.just(..)) will be processed in same thread
        // Delay operation will be performed another thread
//        schedulers.singleUpStreamScheduler();

        // All Upstream data(source..Flux.just(..)) will be processed in different threads... each thread for Each onNext
        // Delay operation will be performed another thread
//        schedulers.singleDownStreamScheduler();
        //Note: use both publishOn and subscribeOn with SingleScheduler to make whole operation with 1 thread(Not main)

        // OnNext and Operation in operator will be performed on same thread(not single)...which runs synchronously
//        schedulers.immediateScheduler();

        // Create mutiple subscribers with parallel thread runs at same time on same operation with different flux input
//        schedulers.parallelScheduler();

        // Mutiple threads gets created as per need to run asynchronously...
        // also it won't stop main even though any delay inside operators on operation
//        schedulers.withOutBoundedElastic();
//        schedulers.withBoundedElastic();
//        schedulers.withBoundedElasticMultiFlux();
    }

    private void withOutBoundedElastic() {
        System.out.println("Started");
        Flux.just("SELECT * FROM users")
                .flatMap(query -> {
                    // Simulate a blocking database call
                    return Mono.fromCallable(() -> {
                        // Perform blocking database operation here
                        Thread.sleep(2000); // Simulate delay
                        return "Result from DB";
                    });
                })
                .doOnNext(result -> System.out.println("Query Result: " + result))
                .subscribe();
        System.out.println("Got blocked for 2 sec, bacause of block last");
    }

    private void withBoundedElastic() throws InterruptedException {
        System.out.println("Started");
        Flux.just("SELECT * FROM users")
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(query -> {
                    return Mono.fromCallable(() -> {
                        // Perform blocking database operation here
                        Thread.sleep(1000); // Simulate delay
                        return "Result from DB";
                    });
                })
                .doOnNext(result -> System.out.println("Query Result: " + result))
                .subscribe();
        System.out.println("Not blocked");

        Thread.sleep(3000);
    }

    private void withBoundedElasticMultiFlux() throws InterruptedException {
        System.out.println("Started");
        Flux.merge(io1(), io2(), io3())
                .subscribe();
        System.out.println("Not blocked and all 3 io will be running asynchronously");

        Thread.sleep(3000);
    }

    private void immediateScheduler() throws InterruptedException {
        Flux.just(1,2,3,4,5)
                .subscribeOn(Schedulers.immediate())
                .delayElements(Duration.ofSeconds(1))
                .publishOn(Schedulers.immediate())
                .map(number -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    return Thread.currentThread().getName() + "-> Processed: " + number * number; // Return the squared value
                })
                .log()
                .subscribe(System.out::println);

        Thread.sleep(11000);
    }

    private void parallelScheduler() throws InterruptedException {
        Flux.range(1, 100)
                .parallel(4)
                .runOn(Schedulers.parallel())
                .map(number -> {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    return Thread.currentThread().getName() + "-> Processed: " + number * number; // Return the squared value
                })
                .doOnNext(result -> System.out.println("Thread: " + result))
                .subscribe();

        Thread.sleep(60000);
    }

    void simpleFlux(){
        Flux.just(1,2,3,4,5)
                .log()
                .map(e ->{
                    logThread();
                    return e * 2;
                })
                .subscribe(System.out::println);
    }

    void delayFlux() throws InterruptedException {
        Flux.just(1,2,3,4,5)
                .delayElements(Duration.ofSeconds(1))
                .map(e ->{
                    logThread();
                    return e * 2;
                })
                .log()
                .subscribe(System.out::println);
        Thread.sleep(11000);
    }

    void singleUpStreamScheduler() throws InterruptedException {
        Flux.just(1,2,3,4,5)
                .log()
                .subscribeOn(Schedulers.single())
                .delayElements(Duration.ofSeconds(1))
                .map(e ->{
                    logThread();
                    return e * 2;
                })
                .subscribe(System.out::println);
        Thread.sleep(11000);
    }

    void singleDownStreamScheduler() throws InterruptedException {
        Flux.just(1,2,3,4,5)
                .log()
                .delayElements(Duration.ofSeconds(1))
                .publishOn(Schedulers.single())
                .map(e ->{
                    logThread();
                    return e * 2;
                })
                .subscribe(System.out::println);
        Thread.sleep(11000);
    }

    private Flux io1(){
        return Flux.just("SELECT * FROM users")
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(query -> {
                    // Simulate a blocking database call
                    return Mono.fromCallable(() -> {
                        // Perform blocking database operation here
                        Thread.sleep(1000); // Simulate delay
                        return "Result from DB";
                    });
                })
                .doOnNext(result -> System.out.println("Query Result: " + result));
    }

    private Flux io2(){
        return Flux.just("Make Api call")
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(query -> {
                    // Simulate a blocking database call
                    return Mono.fromCallable(() -> {
                        Thread.sleep(1500);
                        return "Result from API";
                    });
                })
                .doOnNext(result -> System.out.println("Query Result: " + result));
    }

    private Flux io3(){
        return Flux.just("Make another DB call")
                .subscribeOn(Schedulers.boundedElastic())
                .flatMap(query -> {
                    return Mono.fromCallable(() -> {
                        Thread.sleep(2000);
                        return "Result from DB";
                    });
                })
                .doOnNext(result -> System.out.println("Query Result: " + result));
    }

    private void logThread() {
        System.out.println("Thread:"+ Thread.currentThread().getName());
    }

}
