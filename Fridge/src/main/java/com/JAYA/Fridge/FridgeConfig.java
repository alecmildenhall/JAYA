package com.JAYA.Fridge;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FridgeConfig {

    @Bean
    CommandLineRunner commandLineRunner(FridgeRepository repository){
        return args -> {
            Food carrot1 = new Food(1L, "carrot", 3L, 4L);
            Food carrot2 = new Food(2L, "carrot", 50L);

            repository.saveAll(
                List.of(carrot1, carrot2)
            );

        };
    }
}
