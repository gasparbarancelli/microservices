package com.gasparbarancelli.vendas.listener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gasparbarancelli.vendas.model.Produto;
import com.gasparbarancelli.vendas.repository.ProdutoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ProdutoPersistQueue {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoPersistQueue.class);

    private final ObjectMapper objectMapper;

    private final ProdutoRepository produtoRepository;

    public ProdutoPersistQueue(ObjectMapper objectMapper, ProdutoRepository produtoRepository) {
        this.objectMapper = objectMapper;
        this.produtoRepository = produtoRepository;
    }

    @JmsListener(destination = "queue.produto.persist", containerFactory = "jsaFactory")
    public void onReceiverTopic(String json) {
        try {
            var produto = objectMapper.readValue(json, Produto.class);
            produtoRepository.save(produto);
        } catch (JsonProcessingException e) {
            LOGGER.error("Não foi possivel criar uma nova instancia do produto com base no json recebido", e);
        }
    }

}
