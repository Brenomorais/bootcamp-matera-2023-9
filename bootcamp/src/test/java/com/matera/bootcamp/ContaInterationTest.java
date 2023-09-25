package com.matera.bootcamp;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import com.matera.bootcamp.model.Conta;
import com.matera.bootcamp.repository.ContaRepository;

import io.restassured.RestAssured;

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
		
		RestAssured.given()		
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

}
