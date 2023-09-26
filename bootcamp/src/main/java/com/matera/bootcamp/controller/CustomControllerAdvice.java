package com.matera.bootcamp.controller;

import com.matera.bootcamp.exception.ContaInvalidaException;
import com.matera.bootcamp.exception.ContaSemSaldoException;
import com.matera.bootcamp.exception.MensagemErro;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
* Intercepta o lançamento de execções para promover um tratamento unificado de exceções para todo o projeto
*/
@ControllerAdvice
public class CustomControllerAdvice {

    @ExceptionHandler(ContaInvalidaException.class)
    public ResponseEntity<MensagemErro> contaInvalidaException(ContaInvalidaException ex) {
        MensagemErro mensagemErro = new MensagemErro(ex.getMessage());
        return new ResponseEntity<>(mensagemErro, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(ContaSemSaldoException.class)
    public ResponseEntity<MensagemErro> contaSemSaldoException(ContaSemSaldoException ex) {
        MensagemErro mensagemErro = new MensagemErro(ex.getMessage());
        return new ResponseEntity<>(mensagemErro, HttpStatus.CONFLICT);
    }
}
