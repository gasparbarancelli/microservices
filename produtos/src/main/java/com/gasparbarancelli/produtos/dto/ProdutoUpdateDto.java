package com.gasparbarancelli.produtos.dto;

import org.openapitools.jackson.nullable.JsonNullable;

import java.math.BigDecimal;

public class ProdutoUpdateDto {

    private final JsonNullable<String> descricao = JsonNullable.undefined();
    private final JsonNullable<BigDecimal> valor = JsonNullable.undefined();

    @Deprecated
    public ProdutoUpdateDto() {
    }

    public JsonNullable<String> getDescricao() {
        return descricao;
    }

    public JsonNullable<BigDecimal> getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return "ProdutoUpdateDto{" +
                "descricao=" + descricao +
                ", valor=" + valor +
                '}';
    }
}
