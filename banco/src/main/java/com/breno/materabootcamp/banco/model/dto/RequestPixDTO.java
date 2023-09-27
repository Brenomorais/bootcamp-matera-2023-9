package com.breno.materabootcamp.banco.model.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class RequestPixDTO {

	private String chaveOrigem;
	private String chaveDestino;
	private BigDecimal valor;
}