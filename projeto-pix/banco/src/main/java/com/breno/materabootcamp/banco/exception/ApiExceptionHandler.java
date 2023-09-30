package com.breno.materabootcamp.banco.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
* Intercepta o lançamento de execções para promover um tratamento unificado de exceções para todo o projeto
*/
@ControllerAdvice
public class ApiExceptionHandler {

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
    
    @ExceptionHandler(ValorInvalidoException.class)
    public ResponseEntity valorInvalido(ValorInvalidoException e) {
        Problema problema = new Problema(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(problema);
    }
    
    @ExceptionHandler(BancoInexistenteException.class)
    public ResponseEntity valorInvalido(BancoInexistenteException e) {
        Problema problema = new Problema(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(problema);
    }
}
