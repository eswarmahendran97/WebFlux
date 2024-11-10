package com.reactive.webflux.flux;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.List;

public class FlatMap {

    static class User {
        private final int id;
        private final String name;

        User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }
    }

    static class UserDetails {
        private final int id;
        private final String details;

        UserDetails(int id, String details) {
            this.id = id;
            this.details = details;
        }

        @Override
        public String toString() {
            return "UserDetails{id=" + id + ", details='" + details + "'}";
        }
    }

    public static void main(String[] args) {
        List<User> userList = List.of(new User(1, "Alice"), new User(2, "Bob"), new User(3, "Charlie"),
                new User(1, "Alice"), new User(2, "Bob"), new User(3, "Charlie"),
                new User(1, "Alice"), new User(2, "Bob"), new User(3, "Charlie"),
                new User(1, "Alice"), new User(2, "Bob"), new User(3, "Charlie"),
                new User(1, "Alice"), new User(2, "Bob"), new User(3, "Charlie"),
                new User(1, "Alice"), new User(2, "Bob"), new User(3, "Charlie"));

        Flux<UserDetails> userDetailsFlux = Flux.fromIterable(userList)
                .flatMap(user -> Mono.defer(() -> fetchUserDetailsById(user.getId())).subscribeOn(Schedulers.boundedElastic())); // Asynchronous DB call

        userDetailsFlux.subscribe(
                userDetails -> System.out.println("Fetched user details: " + userDetails),
                error -> System.err.println("Error: " + error),
                () -> System.out.println("Completed fetching user details.")
        );
    }

    private static Mono<UserDetails> fetchUserDetailsById(int id) {
        System.out.println(id);
        return Mono.just(new UserDetails(id, "Details for User " + id)); // Simulate a DB call
    }
}
