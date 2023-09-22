package com.matera.bootcamp.controller;

import com.matera.bootcamp.model.Conta;
import com.matera.bootcamp.service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/contas")
@RequiredArgsConstructor
public class ContaController {

    //Formas de injeção de dependencia com Spring:

	/* 1ºAtributo: Diretamente no atributo da classe.
	@Autowired
	private ContaService contaService;
	 */

	/* 2º Construtor: Esta é a maneira recomendada pela maioria dos desenvolvedores e pelo próprio Spring
	public ContaController(ContaService contaService){
		this.contaService = conta;
	}*/

	/* 3º SetInjection: usando o Setter uma alternativa ao construtor é o uso de setters, menos preferida
	private ContaService contaService;
	@Autowired
	public void setContaService(ContaService contaService) {
        this.contaService = contaService;
    }*/

    //4º Maneira usando a anotacação @RequiredArgsConstructor na classe
    private final ContaService contaService;

    /**
     * GET: Retorna todas as contas
     * @return
     */
    @GetMapping
    public List<Conta> teste(){
        //Conta conta = Conta.builder().id(1L).agencia("123").numeroConta("123456789").build();
        //contaService.criar(conta);
        return  contaService.getContas();
    }

    /**
     * POST: Cria nova conta
     * @param conta
     * @return
     */
    @PostMapping
    public ResponseEntity<Conta> novaConta(@RequestBody Conta conta){
        return  ResponseEntity.ok(contaService.criarOuAutalizar(conta)); //http 200
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
    public ResponseEntity<Void> deletarConta(@PathVariable Long id){
        try{
            contaService.deletarConta(id);
            return ResponseEntity.noContent().build(); // 204 no content
        }catch (Exception e){
            return  ResponseEntity.notFound().build(); // nao achou 404 not found
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
    public ResponseEntity<Conta> autalizarConta(@PathVariable Long id, @RequestBody  Conta contaAtualizada) {
        Optional<Conta> contaOptional = contaService.buscaPorId(id);
        if (contaOptional.isPresent()) {
            contaAtualizada.setId(id);
            contaService.criarOuAutalizar(contaAtualizada);
            return ResponseEntity.ok(contaAtualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}