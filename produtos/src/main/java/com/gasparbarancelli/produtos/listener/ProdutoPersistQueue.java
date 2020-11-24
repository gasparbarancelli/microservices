package com.gasparbarancelli.produtos.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gasparbarancelli.produtos.event.ProdutoPersistEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProdutoPersistQueue implements ApplicationListener<ProdutoPersistEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoPersistQueue.class);

    private final ObjectMapper objectMapper;

    private final JmsTemplate jmsTemplate;

    public ProdutoPersistQueue(ObjectMapper objectMapper, JmsTemplate jmsTemplate) {
        this.objectMapper = objectMapper;
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void onApplicationEvent(ProdutoPersistEvent event) {
        try {
            var json = objectMapper.writeValueAsString(event.getProduto());
            jmsTemplate.convertAndSend("queue.produto.persist", json);
        } catch (JsonProcessingException e) {
            LOGGER.error("Não foi possivel converter o objeto produto para JSON", e);
        }
    }

}
