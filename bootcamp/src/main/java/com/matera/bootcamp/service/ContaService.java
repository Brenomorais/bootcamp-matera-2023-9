package com.matera.bootcamp.service;

import com.matera.bootcamp.model.Conta;
import com.matera.bootcamp.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ContaService {

    private final ContaRepository contaRepository;

    public void informacoesConta(Conta conta) {
        System.out.println("conta");
    }

    public Conta criarOuAutalizar(Conta conta) {
        contaRepository.save(conta);
        return conta;
    }
    public List<Conta> getContas(){
        return contaRepository.findAll();
    }

    public Optional<Conta> buscaPorId(Long id){
        return  contaRepository.findById(id);
    }

    public Conta atualizarConta(Long id, Conta contaAtualizada) throws  IllegalArgumentException{
        if(!contaRepository.existsById(id)){
            throw new IllegalArgumentException("Conta não encontrada.");
        }
        contaAtualizada.setId(id);
        return contaRepository.save(contaAtualizada);
    }

    public void deletarConta(Long id) throws IllegalArgumentException{
        if(!contaRepository.existsById(id)){
            throw new IllegalArgumentException("Conta não encontrada.");
        }
        contaRepository.deleteById(id);
    }
}