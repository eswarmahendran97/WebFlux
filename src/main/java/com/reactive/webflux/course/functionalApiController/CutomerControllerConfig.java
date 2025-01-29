package com.reactive.webflux.course.functionalApiController;

import com.reactive.webflux.course.exception.ApplicationExceptionHandler;
import com.reactive.webflux.course.exception.CustomerException;
import com.reactive.webflux.course.requestHandler.CustomerRequestHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class CutomerControllerConfig {

    @Autowired
    CustomerRequestHandler customerRequestHandler;

    @Autowired
    ApplicationExceptionHandler applicationExceptionHandler;


    @Bean
    public RouterFunction<ServerResponse> customerRoute(){
        return RouterFunctions.route()
        .GET("customer", e -> this.customerRequestHandler.getCustomerFlux(e))
        .GET("delay", e -> this.customerRequestHandler.getDelayCustomerFlux(e))
        .path("customer", this::customerChildRoute)
//        .GET("customer/paginated", e -> this.customerRequestHandler.getCustomerFluxPaginated(e))
//        .POST("customer", e -> this.customerRequestHandler.saveCustomerFlux(e))
        .onError(CustomerException.class, applicationExceptionHandler::handleException)
        .filter(((request, next) -> {
            System.out.println("Inside functional api filter1");
            return next.handle(request);
        }))
        .filter(((request, next) -> {
            System.out.println("Inside functional api filter2");
            return next.handle(request);
        }))
        .build();
    }

    private RouterFunction<ServerResponse> customerChildRoute(){
        return RouterFunctions.route()
        .GET("/stream", e -> this.customerRequestHandler.streamCustomerFlux(e))
        .GET("/paginated", e -> this.customerRequestHandler.getCustomerFluxPaginated(e))
        .POST(e -> this.customerRequestHandler.saveCustomerFlux(e))
        .build();
    }


    @Bean
    public RouterFunction<ServerResponse> calculatorRoutes(){
        return RouterFunctions
        .route()
        .GET("calculator/{a}/0", request -> ServerResponse.badRequest().bodyValue("B cannot be 0"))
        .GET("calculator/{a}/{b}", RequestPredicates.path("*/?/?").and(RequestPredicates.headers(headers -> headers.header("operator").size() == 1)), e -> customerRequestHandler.calculate(e))
        .build();
    }
}