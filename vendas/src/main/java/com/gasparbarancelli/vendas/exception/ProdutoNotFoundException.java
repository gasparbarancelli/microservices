package com.gasparbarancelli.vendas.exception;

import javax.persistence.NoResultException;

public class ProdutoNotFoundException extends NoResultException {

    public ProdutoNotFoundException(Long id) {
        super("Produto não pode ser encontrado: ID[" + id + "]");
    }

}
