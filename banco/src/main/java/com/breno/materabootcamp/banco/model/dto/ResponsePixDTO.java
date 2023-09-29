package com.breno.materabootcamp.banco.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;

@Data
public class ResponsePixDTO {

	private UUID id;	
	private LocalDateTime dateTime;
	private BigDecimal saldoOrigem;
	private BigDecimal saldoDestino;

	public ResponsePixDTO(BigDecimal saldoOrigem, BigDecimal saldoDestino) {
		this.id = UUID.randomUUID();
		this.dateTime = LocalDateTime.now();
		this.saldoOrigem = saldoOrigem;
		this.saldoDestino = saldoDestino;
	}
}