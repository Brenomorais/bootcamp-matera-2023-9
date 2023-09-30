package com.breno.materabootcamp.banco.exception;

public class ContaInvalidaException extends RuntimeException {
    
	public ContaInvalidaException(String message){
        super(message);
    }
}
