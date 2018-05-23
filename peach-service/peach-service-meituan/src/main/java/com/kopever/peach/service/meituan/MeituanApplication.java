package com.kopever.peach.service.meituan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MeituanApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeituanApplication.class, args);
    }

}
