package com.brenom.bootcampmatera.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testes")
public class TestController {
	
	@GetMapping
	public String teste() {
		return "API no ar.";
	}
}
