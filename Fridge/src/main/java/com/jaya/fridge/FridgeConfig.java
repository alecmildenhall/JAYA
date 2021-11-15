package com.jaya.fridge;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class FridgeConfig {

    @Bean
    CommandLineRunner commandLineRunner(FridgeRepository repository){
        return args -> {
            Food carrot1 = new Food(43L, "carrot", 3L, 4L);
            Food carrot2 = new Food(43L, "banana", 50L);
            Food carrot3 = new Food(43L, "water", 50L);

            repository.saveAll(
                List.of(carrot1, carrot2, carrot3)
            );

        };
    }
}
