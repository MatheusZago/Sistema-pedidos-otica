package com.matheusluizago.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@Configuration
public class WebConfiguration {

    public void addCorsMapping(CorsRegistry registry){

        registry.addMapping("/*")
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("GET", "POST", "POST", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

}
