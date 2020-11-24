package com.gasparbarancelli.produtos.model;

import javax.persistence.NoResultException;

public class ProdutoNotFoundException extends NoResultException {

    ProdutoNotFoundException(Long id) {
        super("Produto não pode ser encontrado: ID[" + id + "]");
    }

}
