package com.matera.bootcamp;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import com.matera.bootcamp.model.Conta;
import com.matera.bootcamp.model.dto.ResponsePixDTO;
import com.matera.bootcamp.repository.ContaRepository;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ContaInterationTest {
	
	@Autowired
	private ContaRepository contaRepository;
	
	@LocalServerPort
	private int port;
	
	@BeforeEach
	public void setup() {
		RestAssured.port = port;
		RestAssured.baseURI = "http://localhost";
	}
	
	@Test
	void deveRetornarOkQuandoChamamosTodasAsContas() {
				
		RestAssured.given()		
		.when()
		.get("/contas")
		.then()
		.statusCode(HttpStatus.OK.value())
		.extract();
	}
	
	@Test
	void deveRetornarNotFoundQuandoContaNaoExiste() {
		
		RestAssured
		.given()		
		.when()
		.get("/contas/{id}", 9L)
		.then()
		.statusCode(HttpStatus.NOT_FOUND.value())
		.extract();
	}
	
	@Test
	void deveRetornarOKQuandoBuscaContaPorId() {
		
		Conta contaRequest = new Conta();
		contaRequest.setNumeroConta("123456");
		contaRequest.setAgencia("001");
		contaRequest.setSaldo(BigDecimal.valueOf(100));		
		contaRepository.save(contaRequest);
		
		Conta contaReponse = RestAssured
			.given()		
			.when()
			.get("/contas/{id}", contaRequest.getId())
			.then()
			.statusCode(HttpStatus.OK.value())
			.extract().jsonPath().getObject("", Conta.class);
				
		Assertions.assertEquals(contaRequest.getId(), contaReponse.getId());
	}
	
	@Test
	void deveRetonarErroDeSaldoInsuficiente() {
		
		JsonPath path = RestAssured.given()
                .header("Accept", "application/json")
                .get("/contas/criarConta")
                .andReturn().jsonPath();
		
		List<Conta> contasReponse = path.getList(".", Conta.class);
		
		if (contasReponse.size() == 2) {
			Conta origem = contasReponse.get(0);
			Conta destino = contasReponse.get(1);			
			
			 Map<String, Object> pix = new HashMap<>();
			 pix.put("chaveOrigem", origem.getTitular().getCpf());
			 pix.put("chaveDestino", destino.getTitular().getCpf());
			 pix.put("valor", 1000);
			 
			 RestAssured.given()	     
	            .contentType(ContentType.JSON)
	            .body(pix)
	        .when()
	            .post("/contas/lancamentos/pix")
	        .then()
	        	.statusCode(HttpStatus.CONFLICT.value())
	        	.extract();    			
		}		
	}
	
	
	@Test
	void deveRealizarOKQuandoRealizarPixComSucesso() {
		
		JsonPath path = RestAssured.given()
                .header("Accept", "application/json")
                .get("/contas/criarConta")
                .andReturn().jsonPath();
		
		List<Conta> contasReponse = path.getList(".", Conta.class);
		
		if (contasReponse.size() == 2) {
			Conta origem = contasReponse.get(0);
			Conta destino = contasReponse.get(1);			
			
			//esse saldo só e visiel nesse escopo do teste
			origem.setSaldo(new BigDecimal(1000));
			
			 RestAssured.given()	     
			 	.pathParam("idConta", "1")
				.pathParam("valor", "1000")
			 .when()
			    .post("/contas/lancamentos/{idConta}/credito/{valor}")
			 .then()
			    .statusCode(HttpStatus.CREATED.value())
			    .extract();	 
								
			 Map<String, Object> pix = new HashMap<>();
			 pix.put("chaveOrigem", origem.getTitular().getCpf());
			 pix.put("chaveDestino", destino.getTitular().getCpf());
			 BigDecimal valorPix = new BigDecimal(200);
			 pix.put("valor", valorPix);
			 
			 ResponsePixDTO responsePixDTO = RestAssured.given()	     
	            .contentType(ContentType.JSON)
	            .body(pix)
	        .when()
	            .post("/contas/lancamentos/pix")
	        .then()
	        	.statusCode(HttpStatus.OK.value())
	        	.extract().jsonPath().getObject("", ResponsePixDTO.class);		
			
		}
		
	}
}