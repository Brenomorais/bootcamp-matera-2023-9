package com.matera.bootcamp.model;

import java.math.BigDecimal;

import com.matera.bootcamp.exception.ContaSemSaldoException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Conta {
	
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "NUMERO")
    private String numeroConta;
    
    @Column(name = "AGENCIA")
    private String agencia;
   
    @Column(name = "SALDO")
    private BigDecimal saldo = BigDecimal.ZERO;

    @OneToOne
    private Titular titular;
   
    public Conta(){
    }

    public Conta(Long id, String numeroConta, String agencia, Titular titular, BigDecimal saldo) {
        this.id = id;
        this.numeroConta = numeroConta;
        this.agencia = agencia;
        this.titular = titular;
        this.saldo = BigDecimal.ZERO;
    }
    
    public void debito(BigDecimal valor){
        saldo = saldo.subtract(valor);
    }

    public void credito(BigDecimal valor){
        saldo = saldo.add(valor);
    }

	public void enviarPix(Conta contaDestino, BigDecimal valor) {
		if(this.saldo.compareTo(valor) <= 0) {
			throw new ContaSemSaldoException("Conta sem saldo disponível.");
		}
		//faz o debito na contaOrigem
		this.debito(valor);
		//faz o credito na contaDestino
		contaDestino.credito(valor);
	}
}
