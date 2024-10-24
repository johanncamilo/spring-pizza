package com.belos.spring_pizza;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class SpringPizzaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringPizzaApplication.class, args);
    }

}
