package com.breno.materabootcamp.banco.exception;

public class BancoInexistenteException extends RuntimeException{

    public BancoInexistenteException(String message) {
        super(message);
    }
}