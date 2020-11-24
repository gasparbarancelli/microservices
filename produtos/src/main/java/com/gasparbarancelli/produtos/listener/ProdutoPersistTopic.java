package com.gasparbarancelli.produtos.listener;

import com.gasparbarancelli.produtos.event.ProdutoPersistEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProdutoPersistTopic implements ApplicationListener<ProdutoPersistEvent> {

    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void onApplicationEvent(ProdutoPersistEvent event) {
        jmsTemplate.convertAndSend("topic.produto.persist", event.getProduto());
    }

}
