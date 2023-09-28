package com.breno.materabootcamp.banco;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.breno.materabootcamp.banco.model.Conta;
import com.breno.materabootcamp.banco.model.dto.ResponsePixDTO;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.breno.materabootcamp.banco.repository.ContaRepository;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ContaInterationTest {

	@Autowired
	private ContaRepository contaRepository;

	private static WireMockServer wireMockServer;

	@LocalServerPort
	private int port;

	@BeforeEach
	public void setup() {
		RestAssured.port = port;
		RestAssured.baseURI = "http://localhost";
	}

	@BeforeAll
	static void startWireMock() {
		wireMockServer = new WireMockServer(WireMockConfiguration.wireMockConfig().port(8081));

		wireMockServer.start();

		wireMockServer.stubFor(WireMock.post(WireMock.urlEqualTo("/contas"))
				.willReturn(aResponse().withHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE)
						.withStatus(HttpStatus.CREATED.value())
						.withBody("{\r\n"
								+ "    \"numeroConta\": \"002\",\r\n"
								+ "    \"agencia\": \"0141\",\r\n"
								+ "    \"chavePix\": \"11111111111\"\r\n"
								+ "}")));

	}
	
	@AfterAll
	static void stopWireMock() {
		wireMockServer.stop();
	}

	@Test
	void testWireMock() {
		System.out.println(wireMockServer.baseUrl());
		assertTrue(wireMockServer.isRunning());
	}

	@Test
	void deveRetornarOkQuandoChamamosTodasAsContas() {

		RestAssured.given()
			.when().get("/contas")
			.then()
			.statusCode(HttpStatus.OK.value()).extract();
	}

	@Test
	void deveRetornarNotFoundQuandoContaNaoExiste() {
		RestAssured.given().log().all()
			.when().get("/contas/{id}", 9L)
			.then().statusCode(HttpStatus.NOT_FOUND.value())
			.extract();
	}

	@Test
	void deveRetornarOKQuandoBuscaContaPorId() {

		Conta contaRequest = new Conta();
		contaRequest.setNumeroConta("123456");
		contaRequest.setAgencia("001");
		contaRequest.setSaldo(BigDecimal.valueOf(100));
		contaRepository.save(contaRequest);

		Conta contaReponse = RestAssured.given()
				.log().all().when().get("/contas/{id}", contaRequest.getId())
				.then()
				.statusCode(HttpStatus.OK.value())
				.extract().jsonPath().getObject("", Conta.class);

		Assertions.assertEquals(contaRequest.getId(), contaReponse.getId());
	}

	// Teste com mock WireMockServer para servico do Bacen
	@Test
	public void deveRetornarOKCriarUmaConta() {

		String requestConta = "{       \r\n"
		 		+ "    \"numeroConta\": \"999\",\r\n"
		 		+ "    \"agencia\": \"123\",\r\n"
		 		+ "    \"titular\":{\r\n"
		 		+ "       \"nome\": \"user\",\r\n"
		 		+ "       \"cpf\": \"99999999999\"\r\n"
		 		+ "    }\r\n"
		 		+ "}";

		RestAssured.given().log().all()
			.contentType(ContentType.JSON)
			.body(requestConta).when()
			.post("/contas").then()
			.statusCode(HttpStatus.CREATED.value()).extract();
	}

	@Test
	void deveRetornarOkCriarContasTestes() {

		List<Conta> contasReponse = RestAssured.given()
				.when().get("/contas/criarConta")
				.then()
				.statusCode(HttpStatus.CREATED.value())
				.extract()
				.jsonPath().getList(".", Conta.class);

		if (contasReponse.size() == 2) {
			Conta origem = contasReponse.get(0);
			Conta destino = contasReponse.get(1);

			Assertions.assertNotNull(origem);
			Assertions.assertNotNull(destino);
		}
	};

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

			RestAssured.given().log().all().contentType(ContentType.JSON).body(pix).when()
					.post("/contas/lancamentos/pix").then().statusCode(HttpStatus.CONFLICT.value()).extract();
		}
	}

	@Test
	void deveRealizarOKQuandoRealizarPixComSucesso() {

		JsonPath path = RestAssured.given().log().all()
				.header("Accept", "application/json")
				.get("/contas/criarConta")
				.andReturn().jsonPath();

		List<Conta> contasReponse = path.getList(".", Conta.class);

		if (contasReponse.size() == 2) {
			Conta origem = contasReponse.get(0);
			Conta destino = contasReponse.get(1);

			// esse saldo só e visiel nesse escopo do teste
			origem.setSaldo(new BigDecimal(1000));

			RestAssured.given().log().all().pathParam("idConta", origem.getId()).pathParam("valor", "1000")
					.accept(ContentType.JSON).when().post("/contas/lancamentos/{idConta}/credito/{valor}").then()
					.statusCode(HttpStatus.CREATED.value()).extract();

			Map<String, Object> pix = new HashMap<>();
			pix.put("chaveOrigem", origem.getTitular().getCpf());
			pix.put("chaveDestino", destino.getTitular().getCpf());
			BigDecimal valorPix = new BigDecimal(200);
			pix.put("valor", valorPix);

			ResponsePixDTO responsePixDTO = RestAssured.given().log().all().contentType(ContentType.JSON).body(pix)
					.when().post("/contas/lancamentos/pix").then().statusCode(HttpStatus.OK.value()).extract()
					.jsonPath().getObject("", ResponsePixDTO.class);

			Assertions.assertNotNull(responsePixDTO);

		}

	}
}