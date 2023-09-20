package com.matera.bootcamp.controller;

import com.matera.bootcamp.model.Conta;
import com.matera.bootcamp.service.ContaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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
        return  ResponseEntity.ok(contaService.criar(conta)); //http 200
    }

    /**
     * GET: Busca conta por Id
     * http://localhost:8080/contas/1
     * @param id
     * @return Conta
     */
    @GetMapping("/{id}")
    public ResponseEntity<Conta> buscaPorId(@PathVariable Long id){
        List<Conta> contas = contaService.getContas();
        Optional<Conta> contaOptional = contas.stream()
                .filter(conta -> conta.getId().equals(id))
                .findFirst();
        if(contaOptional.isPresent()){
            Conta  conta = contaOptional.get();
            return ResponseEntity.ok(conta);
        }else{
            return  ResponseEntity.notFound().build(); //http 404
        }
    }

}