package com.jaya.fridge;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FridgeConfig {

    @Bean
    CommandLineRunner commandLineRunner(FridgeRepository repository){
        return args -> {
        };
    }
}
