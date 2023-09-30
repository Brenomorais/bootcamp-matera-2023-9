package com.brenom.bootcampmatera.bacen.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.brenom.bootcampmatera.bacen.model.dto.Conta;
import com.brenom.bootcampmatera.bacen.service.ContaService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/contas")
@RequiredArgsConstructor
@Slf4j
public class ContaController {

	private final Map<String, Conta> cacheContas = new HashMap<>();

	private final ContaService contaService;

	@PostMapping
	public ResponseEntity<Conta> novaConta(@RequestBody Conta conta) {
		cacheContas.put(conta.getChavePix(), conta);

		Conta contaCriada = contaService.save(conta);

		return ResponseEntity.ok(contaCriada);
	}

	@GetMapping("/{chavePix}")
	public ResponseEntity<Conta> obterConta(@PathVariable String chavePix) {
		Conta conta = cacheContas.get(chavePix);

		if (conta == null) {
			log.info("Buscando Conta com chavePix {} na base de dados...", chavePix);
			return ResponseEntity.ok(contaService.get(chavePix));
		}

		log.info("A Conta com chavePix {} encontrada no cache.", chavePix);

		return ResponseEntity.ok(conta);
	}

}
