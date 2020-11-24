package com.gasparbarancelli.cupons.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CupomPersistDto {

    @NotNull
    private String codigo;

    @NotNull
    private BigDecimal desconto;

    @Deprecated
    public CupomPersistDto() {
    }

    public String getCodigo() {
        return codigo;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    @Override
    public String toString() {
        return "CupomPersistDto{" +
                "codigo='" + codigo + '\'' +
                ", desconto=" + desconto +
                '}';
    }
}
