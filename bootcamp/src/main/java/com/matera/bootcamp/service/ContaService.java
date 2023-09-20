package com.matera.bootcamp.service;

import com.matera.bootcamp.model.Conta;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContaService {

    private List<Conta> contas = new ArrayList<>();
    private Long idCorrent = 1L;

    public void informacoesConta(Conta conta) {
        System.out.println("conta");
    }

    public Conta criar(Conta conta) {
        conta.setId(idCorrent++);
        contas.add(conta);
        return conta;
    }

    public List<Conta> getContas(){
        return contas;
    }

}