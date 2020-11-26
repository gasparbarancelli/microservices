package com.gasparbarancelli.gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
public class CorsGlobalConfiguration implements WebFluxConfigurer {
 
    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
          .allowedOrigins("*")
          .allowedMethods("POST", "PUT", "GET", "OPTIONS", "DELETE", "PATCH")
          .allowedHeaders("Origin", "Authorization", "Content-Type", "Accept", "X-CSRF-TOKEN")
          .allowCredentials(true)
          .maxAge(3600);
    }

}
