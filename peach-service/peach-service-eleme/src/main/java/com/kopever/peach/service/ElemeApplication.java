package com.kopever.peach.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ElemeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ElemeApplication.class, args);
    }

}
