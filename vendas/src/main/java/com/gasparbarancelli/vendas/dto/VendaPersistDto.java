package com.gasparbarancelli.vendas.dto;

import org.openapitools.jackson.nullable.JsonNullable;

import javax.validation.constraints.NotNull;
import java.util.Set;

public class VendaPersistDto {

    @NotNull
    private Set<VendaItemPersistDto> itens;

    @NotNull
    private String email;

    private final JsonNullable<String> cupom = JsonNullable.undefined();

    @Deprecated
    public VendaPersistDto() {
    }

    public Set<VendaItemPersistDto> getItens() {
        return itens;
    }

    public String getEmail() {
        return email;
    }

    public JsonNullable<String> getCupom() {
        return cupom;
    }
}
