package com.breno.materabootcamp.banco.service;

import org.springframework.stereotype.Service;

import com.breno.materabootcamp.banco.model.Banco;
import com.breno.materabootcamp.banco.repository.BancoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BancoService {
	
	private final BancoRepository bancoRepository;
	
	public Banco salvar(Banco banco) {
		return bancoRepository.save(banco);
	}

}
