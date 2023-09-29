package com.breno.materabootcamp.banco.service;

import static java.util.Objects.isNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.breno.materabootcamp.banco.model.Conta;
import com.breno.materabootcamp.banco.model.dto.RequestPixLinkPagamentoDTO;
import com.breno.materabootcamp.banco.model.dto.PesponsePixLinkPagamentoDTO;
import com.breno.materabootcamp.banco.model.dto.RequestPixDTO;
import com.breno.materabootcamp.banco.model.dto.ResponsePixDTO;
import com.breno.materabootcamp.banco.repository.ContaRepository;
import org.springframework.stereotype.Service;

import com.breno.materabootcamp.banco.exception.ContaInvalidaException;

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

    public void delete(Conta conta){
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
	
	public ResponsePixDTO pix(RequestPixDTO pixDTO) {

		Conta contaOrigem = contaRepository.findByTitularCpf(pixDTO.getChaveOrigem());

		Conta contaDestino = contaRepository.findByTitularCpf(pixDTO.getChaveDestino());

		contaOrigem.enviarPix(contaDestino, pixDTO.getValor());

		contaRepository.saveAll(List.of(contaOrigem, contaDestino));

		return new ResponsePixDTO(contaOrigem.getSaldo(), contaDestino.getSaldo());
	}
	
	public Optional<Conta> getChavePix(String chavePix) {		
		 return  contaRepository.buscaChavePix(chavePix);
	}
	
	public PesponsePixLinkPagamentoDTO gerarLinkPagamento(RequestPixLinkPagamentoDTO pixPagamento) throws ContaInvalidaException{
		 Optional<Conta> conta =  getChavePix(pixPagamento.getChavePix());
		 
		 if(!conta.isPresent()) {	
			 throw new ContaInvalidaException("Chave Pix não encontrada.");			
		 }
		 
		 StringBuilder linkPagamento = new StringBuilder();
		 
		 linkPagamento.append(pixPagamento.getChavePix());
		 linkPagamento.append(";");
		 linkPagamento.append(pixPagamento.getValor());
		 linkPagamento.append(";");
		 linkPagamento.append("qrcode.pix/");
		 linkPagamento.append(UUID.randomUUID());	 
		 		 
		 return new PesponsePixLinkPagamentoDTO(linkPagamento.toString());
	}
	
}