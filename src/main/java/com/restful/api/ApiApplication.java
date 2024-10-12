package com.restful.api;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication(scanBasePackages = {"com.restful.api"})
public class ApiApplication {
    public static void main(String[] args) {
        run(ApiApplication.class, args);
    }
}