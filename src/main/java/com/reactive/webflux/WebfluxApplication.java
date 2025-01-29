package com.reactive.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.IntStream;

@SpringBootApplication(scanBasePackages = "com.reactive.webflux.${sec}")
@EnableR2dbcRepositories(basePackages = "com.reactive.webflux.${sec}")
public class WebfluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxApplication.class, args);
	}

}
