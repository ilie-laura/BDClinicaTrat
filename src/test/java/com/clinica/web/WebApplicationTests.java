package com.clinica.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootApplication(scanBasePackages = "com.clinica.web")

@SpringBootTest
class WebApplicationTests {

public static void main(String[] args) {
    SpringApplication.run(WebApplication.class, args);
}
}
