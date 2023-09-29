package com.breno.materabootcamp.banco.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequenstPagarPixLinkDTO {
	
	private String chaveOrigem;
	private String linkPagamento;
}
