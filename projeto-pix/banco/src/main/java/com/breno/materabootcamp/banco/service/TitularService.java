package com.breno.materabootcamp.banco.service;

import com.breno.materabootcamp.banco.model.Titular;
import com.breno.materabootcamp.banco.repository.TitularRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TitularService {
	
	private final TitularRepository titularRepository;
	
	public Titular criarOuAtualizar(Titular titular) {
		return titularRepository.save(titular);
	}

}
