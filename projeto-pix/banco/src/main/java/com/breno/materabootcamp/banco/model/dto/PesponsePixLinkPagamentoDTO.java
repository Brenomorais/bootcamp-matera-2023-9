package com.breno.materabootcamp.banco.model.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PesponsePixLinkPagamentoDTO {

	private LocalDateTime date;
	private String linkPagamento;

	public PesponsePixLinkPagamentoDTO(String linkPagamento) {		
		this.date = LocalDateTime.now();
		this.linkPagamento = linkPagamento;
	}
	
}
