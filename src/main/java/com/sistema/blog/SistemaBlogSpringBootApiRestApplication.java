package com.sistema.blog;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Entry point for the Blog System Spring Boot REST API application.
 */
@SpringBootApplication
public class SistemaBlogSpringBootApiRestApplication {

    /**
     * Registers a {@link ModelMapper} bean used throughout the application
     * to convert between entities and DTOs.
     *
     * @return a new {@link ModelMapper} instance
     */
    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /**
     * Bootstraps and launches the Spring Boot application.
     *
     * @param args command-line arguments passed to the JVM
     */
    public static void main(String[] args) {
        SpringApplication.run(SistemaBlogSpringBootApiRestApplication.class, args);
    }

}
