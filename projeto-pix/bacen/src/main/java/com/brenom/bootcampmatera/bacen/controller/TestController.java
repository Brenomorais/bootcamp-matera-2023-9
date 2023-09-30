package com.brenom.bootcampmatera.bacen.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testes")
public class TestController {
	
	@GetMapping
	public String teste() {
		return "API Bacen.";
	}
}
