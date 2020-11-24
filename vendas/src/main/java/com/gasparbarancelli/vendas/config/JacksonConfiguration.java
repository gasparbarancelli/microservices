package com.gasparbarancelli.vendas.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class JacksonConfiguration {

    private final ObjectMapper objectMapper;

    public JacksonConfiguration(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public ObjectMapper jacksonObjectMapper() {
        objectMapper.registerModule(new JsonNullableModule());
        return objectMapper;
    }

}
