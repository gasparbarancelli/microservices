package com.gasparbarancelli.email.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gasparbarancelli.email.dto.VendaDto;
import com.gasparbarancelli.email.model.Email;
import com.gasparbarancelli.email.model.Rotina;
import com.gasparbarancelli.email.service.EmailService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.IOException;

@Component
public class EmailVendaPersistQueue {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailVendaPersistQueue.class);

    private final ObjectMapper objectMapper;

    private final EmailService emailService;

    private final Configuration freemarkerConfiguration;

    public EmailVendaPersistQueue(ObjectMapper objectMapper, EmailService emailService, Configuration freemarkerConfiguration) {
        this.objectMapper = objectMapper;
        this.emailService = emailService;
        this.freemarkerConfiguration = freemarkerConfiguration;
    }

    @JmsListener(destination = "queue.email.venda.persist", containerFactory = "jsaFactory")
    public void onReceiverTopic(String json) {
        try {
            var venda = objectMapper.readValue(json, VendaDto.class);
            var conteudoEmail = getConteudoEmail(venda);
            var tituloEmail = "Sua compra foi realizada com sucesso!!!";
            var email = new Email(venda.getId(), venda.getEmail(), Rotina.VENDA, tituloEmail, conteudoEmail);
            emailService.send(email);
        } catch (IOException e) {
            LOGGER.error("Não foi possivel criar uma nova instancia da venda com base no json recebido", e);
        } catch (TemplateException e) {
            LOGGER.error("Não foi possivel gerar o conteudo do e-mail", e);
        }

    }

    private String getConteudoEmail(VendaDto venda) throws IOException, TemplateException {
        var model = new ModelMap("venda", venda);
        Template template = freemarkerConfiguration.getTemplate("venda.ftl");
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, model);
    }

}
