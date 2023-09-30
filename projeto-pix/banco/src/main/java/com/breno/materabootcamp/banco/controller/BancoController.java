package com.breno.materabootcamp.banco.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.breno.materabootcamp.banco.model.Banco;
import com.breno.materabootcamp.banco.service.BancoService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "/bancos")
@RequiredArgsConstructor
public class BancoController {
	
	private final BancoService bancoService;
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping	
	public Banco criarBanco(@RequestBody Banco banco) {
		return bancoService.salvar(banco);
	}

}
