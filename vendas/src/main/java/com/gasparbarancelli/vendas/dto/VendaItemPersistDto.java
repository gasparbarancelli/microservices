package com.gasparbarancelli.vendas.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class VendaItemPersistDto {

    @NotNull
    private Long produto;

    @NotNull
    private BigDecimal quantidade;

    @NotNull
    private BigDecimal valor;

    public VendaItemPersistDto() {
    }

    public Long getProduto() {
        return produto;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

}
