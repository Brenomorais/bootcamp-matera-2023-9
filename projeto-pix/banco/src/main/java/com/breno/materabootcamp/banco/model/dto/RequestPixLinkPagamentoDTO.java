package com.breno.materabootcamp.banco.model.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RequestPixLinkPagamentoDTO {

	private String chavePix;
	private BigDecimal valor = BigDecimal.ZERO;	
	
	public RequestPixLinkPagamentoDTO(String chavePix, BigDecimal valor) {
		super();
		this.chavePix = chavePix;
		this.valor = valor;
	}	
}
