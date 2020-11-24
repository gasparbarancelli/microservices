package com.gasparbarancelli.cupons.exception;

public class CupomNotFoundException extends RuntimeException {

    public CupomNotFoundException(String id) {
        super("Cupom não foi encontrado: ID[" + id + "]");
    }

}
