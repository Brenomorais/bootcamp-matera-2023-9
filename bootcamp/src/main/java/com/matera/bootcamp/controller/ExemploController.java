package com.matera.bootcamp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/testes")
public class ExemploController {
    @GetMapping
    public String hello() {
        return "Olá, Mundo.";
    }
}
