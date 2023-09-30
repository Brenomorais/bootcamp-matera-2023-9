package com.brenom.bootcampmatera.bacen.service;

import org.springframework.stereotype.Service;

import com.brenom.bootcampmatera.bacen.model.dto.Conta;
import com.brenom.bootcampmatera.bacen.repository.ContaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ContaService {
	
	private final ContaRepository contaRepository;
	
	public Conta save(Conta conta) {
		return contaRepository.save(conta);
	}
	
	public Conta get(String chavePix) {
		return contaRepository.findByChavePix(chavePix);
	}

}
