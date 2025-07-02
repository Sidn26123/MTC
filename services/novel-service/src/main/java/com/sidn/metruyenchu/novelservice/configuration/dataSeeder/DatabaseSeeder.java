package com.sidn.metruyenchu.novelservice.configuration.dataSeeder;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@Configuration
@RequiredArgsConstructor
public class DatabaseSeeder {

    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)
    CommandLineRunner logSeederCompletion() {
        return args -> {
            System.out.println("All database seeders have completed execution.");
            System.out.println("Application ready to accept requests.");
        };
    }
}