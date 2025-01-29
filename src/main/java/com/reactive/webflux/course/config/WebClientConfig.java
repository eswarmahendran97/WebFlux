package com.reactive.webflux.course.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.*;

import java.util.function.Consumer;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webReactiveClient(){
        return webReactiveClient(b -> b.filter(tokenGen()).filter(log()));
    }

    private WebClient webReactiveClient(Consumer<WebClient.Builder> consumer){
        var builder = WebClient.builder().baseUrl("http://localhost:8085");
        consumer.accept(builder);
        return builder.build();
    }

    private ExchangeFilterFunction tokenGen() {
        return (request, next) -> {
            // Create modified request with the new header
            var modifiedRequest = ClientRequest.from(request)
                                              .header("auth-token", "PRIME")
                                              .build();
            return next.exchange(modifiedRequest); // Proceed with the modified request
        };
    }

    private ExchangeFilterFunction log() {
        return (request, next) -> {
            System.out.println("URL: "+ request.url());
            System.out.println("Method: "+ request.method());
            return next.exchange(request); // Proceed with the modified request
        };
    }

}