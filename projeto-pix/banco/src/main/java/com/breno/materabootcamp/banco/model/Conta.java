package com.breno.materabootcamp.banco.model;

import java.math.BigDecimal;

import com.breno.materabootcamp.banco.exception.ContaSemSaldoException;
import com.breno.materabootcamp.banco.exception.ValorInvalidoException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
    
    @ManyToOne
    private Banco banco;
   
    public Conta(){
    }

    public Conta(Long id, String numeroConta, String agencia, Titular titular, BigDecimal saldo) {
        this.id = id;
        this.numeroConta = numeroConta;
        this.agencia = agencia;
        this.titular = titular;
        this.saldo = BigDecimal.ZERO;
    }
    
    private void validar(BigDecimal valor) {
    	final String message = String.format("O valor %s não é válido.", valor);
    	if(valor == null) {
    		throw new ValorInvalidoException(message);
    	}
    	if(this.valorIncorreto(valor)) {
    		throw new ValorInvalidoException(message);
    	}
    }
    
    private boolean valorIncorreto(BigDecimal valor) {
        //NPE
        return valor.compareTo(BigDecimal.ZERO) <= 0;
    }
    
    public void debito(BigDecimal valor){
    	this.validar(valor);
        saldo = saldo.subtract(valor);
        log.info("Conta {}/{} foi debitada com {} valor.", this.agencia, this.numeroConta, valor);
        
    }

    public void credito(BigDecimal valor){
    	this.validar(valor);
        saldo = saldo.add(valor);
        log.info("Conta {}/{} foi creditada com {} valor.", this.agencia, this.numeroConta, valor);
    }

	public void enviarPix(Conta contaDestino, BigDecimal valor) {
		if(this.saldo.compareTo(valor) < 0) {
			throw new ContaSemSaldoException("Conta sem saldo disponível.");
		}
		//faz o debito na contaOrigem
		this.debito(valor);
		//faz o credito na contaDestino
		contaDestino.credito(valor);
	}
}
