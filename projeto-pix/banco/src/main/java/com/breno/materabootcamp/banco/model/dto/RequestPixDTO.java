package com.breno.materabootcamp.banco.model.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestPixDTO {

	private String chaveOrigem;
	private String chaveDestino;
	private BigDecimal valor;
}