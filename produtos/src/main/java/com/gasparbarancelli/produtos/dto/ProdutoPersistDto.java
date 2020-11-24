package com.gasparbarancelli.produtos.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ProdutoPersistDto {

    @NotNull
    private String descricao;

    @NotNull
    private BigDecimal valor;

    @Deprecated
    public ProdutoPersistDto() {
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return "ProdutoPersistDto{" +
                "descricao='" + descricao + '\'' +
                ", valor=" + valor +
                '}';
    }
}
