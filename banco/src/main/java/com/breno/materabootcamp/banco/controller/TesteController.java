package com.breno.materabootcamp.banco.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testes")
public class TesteController {
    @GetMapping
    public String hello() {
        return "Olá, Mundo.";
    }
}
