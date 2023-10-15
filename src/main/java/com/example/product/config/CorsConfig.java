package com.example.product.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/v1/products")
//                        ALL Website Using
                        .allowedOrigins("*")
//                        METHOD
                        .allowedMethods("GET");
                registry.addMapping("/api/v1/product/*")
                        .allowedOrigins("*")
                        .allowedMethods("GET");

                registry.addMapping("/api/v1/product/*")
                        .allowedOrigins("*")
                        .allowedMethods("POST", "PUT", "DELETE");
            }
        };
    }
}
