package com.gasparbarancelli.email.dto;

import java.math.BigDecimal;

public class VendaItemDto {

    private String produto;
    private BigDecimal quantidade;
    private BigDecimal valor;
    private BigDecimal valorTotal;

    @Deprecated
    public VendaItemDto() {
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
