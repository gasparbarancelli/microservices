package com.gasparbarancelli.email.dto;

import java.math.BigDecimal;
import java.util.Set;

public class VendaDto {

    private Long id;
    private String email;
    private BigDecimal total;
    private BigDecimal desconto;
    private Set<VendaItemDto> itens;

    @Deprecated
    public VendaDto() {
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

    public Set<VendaItemDto> getItens() {
        return itens;
    }
}
