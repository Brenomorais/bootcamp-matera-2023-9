package com.breno.materabootcamp.exception;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * DTO para trafear mensagem entro o ControllerAdvise
 */
@Data
public class MensagemErro {

    private String mensagem;
    private LocalDateTime timestamp;

    public MensagemErro(String mensagem) {
        this.mensagem = mensagem;
        this.timestamp = LocalDateTime.now();
    }
}
