package com.matera.bootcamp.model;

import lombok.Builder;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Data
@Builder
@Entity
public class Conta {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String numeroConta;
    private String agencia;

    public Conta(){
    }

    public Conta(Long id, String numeroConta, String agencia) {
        this.id = id;
        this.numeroConta = numeroConta;
        this.agencia = agencia;
    }
}
