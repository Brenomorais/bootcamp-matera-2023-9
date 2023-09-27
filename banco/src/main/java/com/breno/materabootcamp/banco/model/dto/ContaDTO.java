package com.breno.materabootcamp.banco.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ContaDTO {

	private String numeroConta;
	private String agencia;
	private String chavePix;
	
}
