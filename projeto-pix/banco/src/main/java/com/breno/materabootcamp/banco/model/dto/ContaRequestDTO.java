package com.breno.materabootcamp.banco.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContaRequestDTO {

    private String agencia;
    private String numeroConta;
    private String nome;
    private String cpf;
    private int codigo;
}
