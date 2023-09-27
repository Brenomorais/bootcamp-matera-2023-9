package com.breno.materabootcamp.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.breno.materabootcamp.exception.ContaSemSaldoException;
import com.breno.materabootcamp.model.dto.RequestPixDTO;
import com.breno.materabootcamp.model.dto.ResponsePixDTO;
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

import com.breno.materabootcamp.exception.ContaInvalidaException;
import com.breno.materabootcamp.model.Conta;
import com.breno.materabootcamp.model.Titular;
import com.breno.materabootcamp.service.ContaService;
import com.breno.materabootcamp.service.TitularService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/contas")
@RequiredArgsConstructor
public class ContaController {

    private final ContaService contaService;    
    private final TitularService titularService;

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
    public ResponseEntity<ResponsePixDTO> pix(@RequestBody RequestPixDTO pixDTO) throws ContaSemSaldoException {
    	ResponsePixDTO responsePixDTO = contaService.pix(pixDTO);
    	return ResponseEntity.status(HttpStatus.OK).body(responsePixDTO);
    }
    
    @PostMapping("/lancamentos/{idConta}/debito/{valor}")
    public ResponseEntity<Conta> debitar(@PathVariable Long  idConta, @PathVariable BigDecimal valor) throws ContaInvalidaException{
    	Conta conta = contaService.debitarConta(idConta, valor);
    	return ResponseEntity.status(HttpStatus.CREATED).body(conta);
    }
    
    @PostMapping("/lancamentos/{idConta}/credito/{valor}")
    public ResponseEntity<Conta> creditar(@PathVariable Long  idConta, @PathVariable BigDecimal valor) throws ContaInvalidaException{
    	Conta conta = contaService.creditarConta(idConta, valor);
    	return ResponseEntity.status(HttpStatus.CREATED).body(conta);
    }

    /**
     * POST: Cria nova conta
     * @param conta
     * @return
     */
    @PostMapping
    public ResponseEntity<Conta> novaConta(@RequestBody Conta conta) throws ContaInvalidaException {
    	Titular titular = conta.getTitular();
    	Titular titularSalvo = titularService.criarOuAtualizar(titular);
        conta.setTitular(titularSalvo);
    	
        return ResponseEntity.status(HttpStatus.CREATED) // 201 created
                .body(contaService.criarOuAutalizar(conta));
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
    public ResponseEntity<Void> deletarConta(@PathVariable Long id) throws ContaInvalidaException {
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
    public ResponseEntity<Conta> autalizar(@PathVariable Long id, @RequestBody  Conta contaAtualizada) throws ContaInvalidaException {
        Optional<Conta> contaOptional = contaService.buscaPorId(id);
        if (contaOptional.isPresent()) {
            contaAtualizada.setId(id);
            contaService.criarOuAutalizar(contaAtualizada);
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
    public ResponseEntity<List<Conta>> criaContas() throws ContaInvalidaException {
    	
    	List<Conta> contas = new ArrayList<>();
    	
		for (int i = 1; i <= 2; i++) {
			Conta conta = new Conta();			
			conta.setNumeroConta("00" + getNumeroAleatorio());
			conta.setAgencia("0141");
			Titular titular = new Titular();
			titular.setCpf("111111111" + getNumeroAleatorio());
			titular.setNome("User"+i);
			
			titular = titularService.criarOuAtualizar(titular);
	    	conta.setTitular(titular);
	    	contas.add(contaService.criarOuAutalizar(conta));
		}    	    	
        return ResponseEntity.status(HttpStatus.CREATED) // 201 created
                .body(contas);
    }
    
    public int getNumeroAleatorio() {
    	return new Random().nextInt((999 - 1) + 1) + 1;
    }

}