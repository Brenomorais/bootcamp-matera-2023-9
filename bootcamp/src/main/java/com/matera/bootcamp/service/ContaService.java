package com.matera.bootcamp.service;

import com.matera.bootcamp.exception.ContaInvalidaException;
import com.matera.bootcamp.model.Conta;
import com.matera.bootcamp.repository.ContaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Service
public class ContaService {

    private final ContaRepository contaRepository;

    public Conta criarOuAutalizar(Conta conta) throws ContaInvalidaException {
        if(isNull(conta.getAgencia())){
            throw new ContaInvalidaException(String.format("A agencia da conta não foi informada!"));
        }
        return contaRepository.save(conta);
    }
    public List<Conta> getContas(){
        return contaRepository.findAll();
    }

    public Optional<Conta> buscaPorId(Long id){
        return  contaRepository.findById(id);
    }

    public void deletarConta(Long id) throws ContaInvalidaException{
        if(!contaRepository.existsById(id)){
            throw new IllegalArgumentException("Conta não encontrada.");
        }
        contaRepository.deleteById(id);
    }

    public void delete(Conta conta) throws ContaInvalidaException{
        contaRepository.delete(conta);
    }
}