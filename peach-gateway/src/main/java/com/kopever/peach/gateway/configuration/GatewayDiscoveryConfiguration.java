//package com.kopever.peach.gateway.configuration;
//
//import org.springframework.cloud.client.discovery.DiscoveryClient;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
//import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
//import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * Created by Lullaby on 2018/5/24
// */
//@Configuration
//@EnableDiscoveryClient
//public class GatewayDiscoveryConfiguration {
//
//    @Bean
//    public RouteDefinitionLocator discoveryClientRouteDefinitionLocator(DiscoveryClient discoveryClient) {
//        return new DiscoveryClientRouteDefinitionLocator(discoveryClient, new DiscoveryLocatorProperties());
//    }
//
//}
