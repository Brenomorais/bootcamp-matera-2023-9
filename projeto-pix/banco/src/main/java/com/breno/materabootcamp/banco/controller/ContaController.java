package com.breno.materabootcamp.banco.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.breno.materabootcamp.banco.model.Banco;
import com.breno.materabootcamp.banco.model.Conta;
import com.breno.materabootcamp.banco.model.dto.ContaRequestDTO;
import com.breno.materabootcamp.banco.model.dto.PesponsePixLinkPagamentoDTO;
import com.breno.materabootcamp.banco.model.dto.RequenstPagarPixLinkDTO;
import com.breno.materabootcamp.banco.model.dto.RequestPixDTO;
import com.breno.materabootcamp.banco.model.dto.RequestPixLinkPagamentoDTO;
import com.breno.materabootcamp.banco.model.dto.ResponsePixDTO;
import com.breno.materabootcamp.banco.service.BancoService;
import com.breno.materabootcamp.banco.service.ContaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/contas")
@RequiredArgsConstructor
public class ContaController {

    private final ContaService contaService;
    
    private final BancoService bancoService;

    /**
     * GET: Retorna todas as contas
     * @return
     */
    @GetMapping
    public List<Conta> teste(){
        return  contaService.getContas();
    }
    
    /**
     * POST: Realiza um PIX
     * URL: http://localhost:8080/contas/lancamentos/pix
     * @param pixDTO
     * @return
     * @throws ContaInvalidaException 
     */
    @PostMapping("/lancamentos/pix")
    public ResponseEntity<ResponsePixDTO> pix(@RequestBody RequestPixDTO pixDTO) {
    	ResponsePixDTO responsePixDTO = contaService.pix(pixDTO);
    	return ResponseEntity.status(HttpStatus.OK).body(responsePixDTO);
    }
    
    @PostMapping("/lancamentos/pix/pagamento")
    public ResponseEntity<ResponsePixDTO> pix(@RequestBody RequenstPagarPixLinkDTO pixPagamentoDTO) {
    	ResponsePixDTO responsePixDTO = contaService.pagarLinkPagamento(pixPagamentoDTO);
    	return ResponseEntity.status(HttpStatus.OK).body(responsePixDTO);
    }
    
    @GetMapping("/lancamentos/pix/pagamento/{chavePix}/{valor}")
    public ResponseEntity<PesponsePixLinkPagamentoDTO> pixPagamento(@PathVariable String chavePix, @PathVariable BigDecimal valor) {
    	RequestPixLinkPagamentoDTO requestlinkPixPagamentoDTO = new RequestPixLinkPagamentoDTO(chavePix, valor);
    	PesponsePixLinkPagamentoDTO responselinkPixDTO = contaService.gerarLinkPagamento(requestlinkPixPagamentoDTO);
    	return ResponseEntity.status(HttpStatus.OK).body(responselinkPixDTO);
    }
    
    @PostMapping("/lancamentos/{idConta}/debito/{valor}")
    public ResponseEntity<Conta> debitar(@PathVariable Long  idConta, @PathVariable BigDecimal valor) {
    	Conta conta = contaService.debitarConta(idConta, valor);
    	return ResponseEntity.status(HttpStatus.CREATED).body(conta);
    }
    
    @PostMapping("/lancamentos/{idConta}/credito/{valor}")
    public ResponseEntity<Conta> creditar(@PathVariable Long  idConta, @PathVariable BigDecimal valor) {
    	Conta conta = contaService.creditarConta(idConta, valor);
    	return ResponseEntity.status(HttpStatus.CREATED).body(conta);
    }

    /**
     * POST: Cria nova conta
     * @param conta
     * @return
     */
    @PostMapping
    public ResponseEntity<Conta> novaConta(@RequestBody ContaRequestDTO contaRequest) {   	
        return ResponseEntity.status(HttpStatus.CREATED) // 201 created
                .body(contaService.criarOuAutalizar(contaRequest));
    }

    /**
     * GET: Busca conta por Id
     * http://localhost:8080/contas/1
     * @param id
     * @return Conta
     */
    @GetMapping("/{id}")
    public ResponseEntity<Conta> buscaPorId(@PathVariable Long id){
        Optional<Conta> contaOptional = contaService.buscaPorId(id);
        if(contaOptional.isPresent()){
            Conta  conta = contaOptional.get();
            return ResponseEntity.ok(conta); // 200 ok
        }else{
            return  ResponseEntity.notFound().build(); // 404 not found
        }
    }

    /**
     * Delete: Remove uma conta cadastrada
     * http://localhost:8080/contas/1
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarConta(@PathVariable Long id)  {
        Optional<Conta> contaOptional = contaService.buscaPorId(id);
        if (contaOptional.isPresent()) {
            Conta conta = contaOptional.get();
            contaService.delete(conta);
            return ResponseEntity.noContent().build(); //204
        } else {
            return ResponseEntity.notFound().build(); //404
        }
    }

    /**
     * PUT (atualiza de maneira integral): atualiza conta cadastrada
     * PATH: atualiza de maneira parcial entidade
     * http://localhost:8080/contas/1
     * @param id
     * @param contaAtualizada
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<Conta> autalizar(@PathVariable Long id, @RequestBody  ContaRequestDTO contaRequest) {
        Optional<Conta> contaOptional = contaService.buscaPorId(id);
        if (contaOptional.isPresent()) {
            Conta contaAtualizada = contaService.criarOuAutalizar(contaRequest);
            return ResponseEntity.ok(contaAtualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    /**
     * URL: http://localhost:8080/contas/criarConta
     * @return
     * @throws ContaInvalidaException
     */
    @GetMapping("/criarConta")
    public ResponseEntity<List<Conta>> criaContas() {
    	
    	List<Conta> contas = new ArrayList<>();   	
    	
    	Banco banco = new Banco();    	
    	banco.setNome("Banco Teste");
    	Banco bancoSalvo = bancoService.salvar(banco);
    	
		for (int i = 1; i <= 2; i++) {
			ContaRequestDTO conta = new ContaRequestDTO();		
			conta.setAgencia(getNumeroAleatorio()+"");
			conta.setNumeroConta("111" + getNumeroAleatorio());
			conta.setNome("User"+i);
			conta.setCpf("111111111" + getNumeroAleatorio());
			conta.setCodigo(bancoSalvo.getCodigo());		
	    	contas.add(contaService.criarOuAutalizar(conta));
		}    	    	
        return ResponseEntity.status(HttpStatus.CREATED) // 201 created
                .body(contas);
    }
    
    public int getNumeroAleatorio() {
    	return new Random().nextInt((999 - 1) + 1) + 1;
    }

}