package com.gasparbarancelli.vendas.exception;

import javax.persistence.NoResultException;

public class VendaNotFoundException extends NoResultException {

    public VendaNotFoundException(Long id) {
        super("Venda não foi encontrada: ID[" + id + "]");
    }

}
