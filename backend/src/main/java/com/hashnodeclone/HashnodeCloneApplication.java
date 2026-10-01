package com.hashnodeclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HashnodeCloneApplication {

    public static void main(String[] args) {

        SpringApplication.run(
                HashnodeCloneApplication.class,
                args
        );

        System.out.println("=================================");
        System.out.println("Hashnode Clone Backend Started");
        System.out.println("http://localhost:8081");
        System.out.println("=================================");
    }
}