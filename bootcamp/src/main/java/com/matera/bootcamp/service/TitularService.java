package com.matera.bootcamp.service;

import org.springframework.stereotype.Service;

import com.matera.bootcamp.model.Titular;
import com.matera.bootcamp.repository.TitularRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TitularService {
	
	private final TitularRepository titularRepository;
	
	public Titular criarOuAtualizar(Titular titular) {
		return titularRepository.save(titular);
	}

}
