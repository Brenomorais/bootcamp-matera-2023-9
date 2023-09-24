package com.matera.bootcamp.service;

import static java.util.Objects.isNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.matera.bootcamp.exception.ContaInvalidaException;
import com.matera.bootcamp.model.Conta;
import com.matera.bootcamp.repository.ContaRepository;

import lombok.RequiredArgsConstructor;

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
    
	public Conta debitarConta(Long idConta, BigDecimal valor) throws ContaInvalidaException {
		Optional<Conta> conta = buscaPorId(idConta);
		if(conta.isPresent()) {
			conta.get().debito(valor);
			return contaRepository.save(conta.get());
		}
		throw new ContaInvalidaException("Erro durante operação credito.");
	}
	
	public Conta creditarConta(Long idConta, BigDecimal valor) throws ContaInvalidaException {
		Optional<Conta> conta = buscaPorId(idConta);
		if(conta.isPresent()) {
			conta.get().credito(valor);
			return contaRepository.save(conta.get());
		}
		throw new ContaInvalidaException("Erro durante operação credito.");
	}
}