package com.reactive.webflux.course.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.Consumer;

@Component
public class ApplicationExceptionHandler {

    public Mono<ServerResponse> handleException(CustomerException customerException, ServerRequest serverRequest) {
        return handleException(HttpStatus.INTERNAL_SERVER_ERROR, customerException, serverRequest, (problem) ->{
            problem.setType(URI.create("http://example.com"));
            problem.setDetail("Customer exception");
        });
    }

    private Mono<ServerResponse> handleException(HttpStatus httpStatus, Exception exception, ServerRequest serverRequest, Consumer<ProblemDetail> consumer){
        var problem = ProblemDetail.forStatusAndDetail(httpStatus, exception.getMessage());
        problem.setInstance(URI.create(serverRequest.path()));
        consumer.accept(problem);
        return ServerResponse.status(httpStatus).bodyValue(problem);
    }
}