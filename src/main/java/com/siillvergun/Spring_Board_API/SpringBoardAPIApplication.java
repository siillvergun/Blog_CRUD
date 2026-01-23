package com.siillvergun.Spring_Board_API;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringBoardAPIApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBoardAPIApplication.class, args);
    }
}
