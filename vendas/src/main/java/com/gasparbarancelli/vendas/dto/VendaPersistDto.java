package com.gasparbarancelli.vendas.dto;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

public class VendaPersistDto {

    @NotNull
    private Set<VendaItemPersistDto> itens = new HashSet<>();

    @Deprecated
    public VendaPersistDto() {
    }

    public Set<VendaItemPersistDto> getItens() {
        return itens;
    }
}
