package com.reactive.webflux.course.config;

import com.reactive.webflux.course.model.Product;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Sinks;

@Configuration
public class SinkConfig {

    @Bean
    public Sinks.Many<Product> sink(){
        return Sinks.many().replay().limit(1);
    }

}