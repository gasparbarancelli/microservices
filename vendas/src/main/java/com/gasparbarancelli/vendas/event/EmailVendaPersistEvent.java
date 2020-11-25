package com.gasparbarancelli.vendas.event;

import com.gasparbarancelli.vendas.model.Venda;
import org.springframework.context.ApplicationEvent;

public class EmailVendaPersistEvent extends ApplicationEvent {

    private final Venda venda;

    public EmailVendaPersistEvent(Object source, Venda venda) {
        super(source);
        this.venda = venda;
    }

    public Venda getVenda() {
        return venda;
    }

}
