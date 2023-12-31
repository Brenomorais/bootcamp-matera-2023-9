package com.breno.materabootcamp.banco.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.breno.materabootcamp.banco.client.BacenClient;
import com.breno.materabootcamp.banco.exception.BancoInexistenteException;
import com.breno.materabootcamp.banco.exception.ContaInvalidaException;
import com.breno.materabootcamp.banco.model.Banco;
import com.breno.materabootcamp.banco.model.Conta;
import com.breno.materabootcamp.banco.model.Titular;
import com.breno.materabootcamp.banco.model.dto.ContaDTO;
import com.breno.materabootcamp.banco.model.dto.ContaRequestDTO;
import com.breno.materabootcamp.banco.model.dto.PesponsePixLinkPagamentoDTO;
import com.breno.materabootcamp.banco.model.dto.RequenstPagarPixLinkDTO;
import com.breno.materabootcamp.banco.model.dto.RequestPixDTO;
import com.breno.materabootcamp.banco.model.dto.RequestPixLinkPagamentoDTO;
import com.breno.materabootcamp.banco.model.dto.ResponsePixDTO;
import com.breno.materabootcamp.banco.repository.BancoRepository;
import com.breno.materabootcamp.banco.repository.ContaRepository;
import com.breno.materabootcamp.banco.repository.TitularRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ContaService {

    private final ContaRepository contaRepository;
    
    private final BancoRepository bancoRepository;
    
    private final TitularRepository titularRepository;
    
    private final BacenClient bacenClient;

    @Transactional(rollbackFor = Exception.class)
    public Conta criarOuAutalizar(ContaRequestDTO contaRequestDTO) {
    	
    	int codigoBanco = contaRequestDTO.getCodigo();
    	final Banco banco = buscaBanco(codigoBanco);    	    	
    	
    	Titular titularSalvo = setTitular(contaRequestDTO);
    	
		var conta = setConta(contaRequestDTO, banco, titularSalvo);
		
		validaContaExistente(conta);
		Conta contaSalva = contaRepository.save(conta);
		 		 
	     //Usando construtor do lombok
	     ContaDTO contaDTO = ContaDTO.builder()
	    		.numeroConta(contaSalva.getNumeroConta())
	    		.agencia(contaSalva.getAgencia())
	    		.chavePix(titularSalvo.getCpf())
	    		.build();	    		
	    	
	    bacenClient.criarConta(contaDTO);	   
       
        return contaSalva;
    }

	private Banco buscaBanco(int codigoBanco) {
		return bancoRepository.findByCodigo(codigoBanco)
    			.orElseThrow(() -> new BancoInexistenteException("Banco não encontrado."));		
	}

	private Conta setConta(ContaRequestDTO contaRequestDTO, final Banco banco, Titular titularSalvo) {
		Optional<Conta> contaOptional = contaRepository.findByAgenciaAndNumeroConta(contaRequestDTO.getAgencia(), 
				contaRequestDTO.getNumeroConta());
		
		if(contaOptional.isPresent()) {
			throw new ContaInvalidaException("Conta já cadastrada.");
		}
		
		var conta = new Conta();
		conta.setAgencia(String.valueOf(contaRequestDTO.getAgencia()));
		conta.setNumeroConta(contaRequestDTO.getNumeroConta());
		conta.setTitular(titularSalvo);
		conta.setBanco(banco);
		return conta;
	}

	private Titular setTitular(ContaRequestDTO contaRequestDTO) {
		buscaTitular(contaRequestDTO.getCpf());
		
		final Titular titular = new Titular();
    	titular.setCpf(contaRequestDTO.getCpf());
    	titular.setNome(contaRequestDTO.getNome());
		return titularRepository.save(titular);
	}

	private void buscaTitular(String cpfTitular) {
		Optional<Titular> titularOptional = titularRepository.findByCpf(cpfTitular);

		//nao permite mais de uma conta por titular
		if (titularOptional.isPresent()) {
    		throw new ContaInvalidaException("Titular já cadastrado.");
		}
	}
    
    public void validaContaExistente(Conta conta) {
    	Optional<Conta> contaOptional = contaRepository.findByAgenciaAndNumeroConta(conta.getAgencia(), conta.getNumeroConta());
    	
    	if(contaOptional.isPresent()) {
    		throw new ContaInvalidaException("Conta já cadastrada.");
    	}
    }
    
    public List<Conta> getContas(){
        return contaRepository.findAll();
    }

    public Optional<Conta> buscaPorId(Long id){
        return  contaRepository.findById(id);
    }

    public void deletarConta(Long id) throws ContaInvalidaException{
        if(!contaRepository.existsById(id)){
			throw new ContaInvalidaException("Conta não encontrada.");
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
		}else {
			throw new ContaInvalidaException("Conta não encontrada.");
		}
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
	
	// Gera link de pagamento no formato: chavePix;valor;qrcode.pix/uuid"
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
	
	public ResponsePixDTO pagarLinkPagamento(RequenstPagarPixLinkDTO pagarPixLink) {			
		
		String[] linkSplitted = pagarPixLink.getLinkPagamento().split(";");
		
		String chaveDestino = linkSplitted[0];
		BigDecimal valor = new BigDecimal(linkSplitted[1]);
				
		
		RequestPixDTO dataPix = RequestPixDTO.builder()
				.chaveOrigem(pagarPixLink.getChaveOrigem())
				.chaveDestino(chaveDestino)
				.valor(valor).build();
		
		return pix(dataPix);		
	}

	public Conta atualizarParcialmenteConta(Long id, Map<String, Object> camposAtualizados) throws ContaInvalidaException {
		Optional<Conta> contaOptioanl = contaRepository.findById(id);
		
		if(!contaOptioanl.isPresent()) {
			 throw new ContaInvalidaException("Conta não encontrada.");
		}
		
		Conta contaExistente = contaOptioanl.get();
		
        ObjectMapper objectMapper = new ObjectMapper();
        //atualiza o reader com a instancia da contaExistente
        ObjectReader reader = objectMapper.readerForUpdating(contaExistente);
        
        Conta contaAtualizado = new Conta();
		try {
			//atualiza instancia do objeto existe com a instacia do objeto atualizado
			contaAtualizado = reader.readValue(objectMapper.writeValueAsString(camposAtualizados));
		} catch (JsonProcessingException e) {
			log.error("Erro na atualizacao parcial: ", e.getMessage());
			throw new ContaInvalidaException("Falha na atualização");			
		}
        
        return contaRepository.save(contaAtualizado);		
	}

	/**
	 * Padrão do Mongoose permite que você atualize apenas os campos que estão presentes na requisição PUT
	 * 
	 * @param contaRequest
	 * @param contaExistente
	 * @return
	 */
	public Conta atualizar(ContaRequestDTO contaRequest, Conta contaExistente) {
				
    	buscaBanco(contaRequest.getCodigo());       	
    	buscaTitular(contaRequest.getCpf());
		
		Conta contaAtualizada = contaExistente;		
		
		contaAtualizada.setAgencia(contaRequest.getAgencia() != null ? 
				contaRequest.getAgencia() : contaExistente.getAgencia());
		
		contaExistente.setNumeroConta(contaRequest.getNumeroConta() != null ? 
				contaRequest.getNumeroConta() : contaExistente.getNumeroConta());
		
		contaAtualizada.getTitular().setNome(contaRequest.getNome() != null ?
				contaRequest.getNome() :  contaExistente.getTitular().getNome());
		
		contaAtualizada.getTitular().setCpf(contaRequest.getCpf() != null ?
				contaRequest.getCpf() :  contaExistente.getTitular().getCpf());
		
		contaAtualizada.getBanco().setCodigo(contaRequest.getCodigo() != 0 ?
				contaRequest.getCodigo() : contaExistente.getBanco().getCodigo());
			
		contaAtualizada = contaRepository.save(contaAtualizada);		
		return contaAtualizada;
	}
	
}