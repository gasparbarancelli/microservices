package com.gasparbarancelli.vendas.dto;

import java.math.BigDecimal;

public class EmailVendaItemDto {

    private String produto;
    private BigDecimal quantidade;
    private BigDecimal valor;
    private BigDecimal valorTotal;

    @Deprecated
    public EmailVendaItemDto() {
    }

    public EmailVendaItemDto(String produto, BigDecimal quantidade, BigDecimal valor, BigDecimal valorTotal) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.valor = valor;
        this.valorTotal = valorTotal;
    }

    public String getProduto() {
        return produto;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }
}
