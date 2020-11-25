package com.gasparbarancelli.vendas.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gasparbarancelli.vendas.event.EmailVendaPersistEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import com.gasparbarancelli.vendas.converter.VendaConverter;

@Component
public class EmailVendaPersistQueue implements ApplicationListener<EmailVendaPersistEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailVendaPersistQueue.class);

    private final ObjectMapper objectMapper;

    private final JmsTemplate jmsTemplate;

    public EmailVendaPersistQueue(ObjectMapper objectMapper, JmsTemplate jmsTemplate) {
        this.objectMapper = objectMapper;
        this.jmsTemplate = jmsTemplate;
    }

    @Override
    public void onApplicationEvent(EmailVendaPersistEvent event) {
        try {
            var emailVenda = VendaConverter.toEmailVendaDto(event.getVenda());
            var json = objectMapper.writeValueAsString(emailVenda);
            jmsTemplate.convertAndSend("queue.email.venda.persist", json);
        } catch (JsonProcessingException e) {
            LOGGER.error("Não foi possivel converter o objeto EmailVendaPersistDto para JSON", e);
        }
    }

}
