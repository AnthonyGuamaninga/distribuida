package com.programacion.distribuida.books.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class RestTemplateConfig {
    @Bean
    @LoadBalanced
    RestTemplateBuilder loadBalancedRestTemplateBuilder() {
        return new RestTemplateBuilder();
    }

}
