package com.breno.materabootcamp.banco.client;

import com.breno.materabootcamp.banco.model.dto.ContaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "bacenClient", url="${api.bacen.url}")
public interface BacenClient {

	@RequestMapping(path = "/contas",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	void criarConta(@RequestBody ContaDTO conta);

}