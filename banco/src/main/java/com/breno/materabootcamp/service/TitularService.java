package com.breno.materabootcamp.service;

import com.breno.materabootcamp.repository.TitularRepository;
import org.springframework.stereotype.Service;

import com.breno.materabootcamp.model.Titular;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TitularService {
	
	private final TitularRepository titularRepository;
	
	public Titular criarOuAtualizar(Titular titular) {
		return titularRepository.save(titular);
	}

}
