package com.gasparbarancelli.vendas.dto;

import java.math.BigDecimal;
import java.util.Set;

public class EmailVendaDto {

    private Long id;
    private String email;
    private BigDecimal total;
    private BigDecimal desconto;
    private Set<EmailVendaItemDto> itens;

    @Deprecated
    public EmailVendaDto() {
    }

    public EmailVendaDto(Long id, String email, BigDecimal total, BigDecimal desconto, Set<EmailVendaItemDto> itens) {
        this.id = id;
        this.email = email;
        this.total = total;
        this.desconto = desconto;
        this.itens = itens;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public Set<EmailVendaItemDto> getItens() {
        return itens;
    }
}
