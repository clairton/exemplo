package br.eti.clairton.agenda.controller;

import static br.com.caelum.vraptor.controller.HttpMethod.PUT;
import static org.junit.Assert.assertEquals;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.TestRule;

import br.com.caelum.vraptor.test.VRaptorIntegration;
import br.com.caelum.vraptor.test.VRaptorTestResult;
import br.com.caelum.vraptor.test.http.Parameters;
import br.eti.clairton.agenda.FixtureRule;

public class PessoaControllerIntegrationTest extends VRaptorIntegration {
	@ClassRule
	public static TestRule rule = new FixtureRule();

	@Test
	public void testIndex() {
		VRaptorTestResult result = navigate().get("/pessoas?nome=Clairton").execute();
		assertEquals(200, result.getResponse().getStatus());
		assertEquals("{\"pessoas\":[{\"nome\":\"Clairton\",\"sobrenome\":\"Heinzen\",\"telefones\":[1,2],\"id\":1,\"links\":[{\"rel\":\"remove\"},{\"rel\":\"update\"},{\"rel\":\"show\"},{\"rel\":\"index\"},{\"rel\":\"edit\"},{\"rel\":\"new\"},{\"rel\":\"create\"}]}],\"meta\":{\"total\":1,\"page\":0},\"links\":[]}", result.getResponseBody());
	}
	
	@Test
	public void testUpdate() {
		String json = "{\"pessoa\":{\"nome\":\"Joazinho\",\"sobrenome\":\"Silva\",\"telefones\":[{\"id\":1001,\"prefixo\":49,\"numero\":88888888}],\"id\":1001,\"links\":[{\"rel\":\"remove\"},{\"rel\":\"update\"},{\"rel\":\"show\"},{\"rel\":\"index\"},{\"rel\":\"edit\"},{\"rel\":\"new\"},{\"rel\":\"create\"}]}}";
		VRaptorTestResult result = navigate()
													.to("/pessoas/1001", PUT, new Parameters())
													.setContent(json)
													.addHeader("content-type", "application/json")
													.execute();
		result.wasStatus(200);
		assertEquals("{\"pessoa\":{\"nome\":\"Joazinho\",\"sobrenome\":\"Silva\",\"telefones\":[1001],\"id\":1001,\"links\":[{\"rel\":\"remove\"},{\"rel\":\"update\"},{\"rel\":\"show\"},{\"rel\":\"index\"},{\"rel\":\"edit\"},{\"rel\":\"new\"},{\"rel\":\"create\"}]}}", result.getResponseBody());
	}
	
	
	@Test
	public void testCreateInvalid() {
		String json = "{\"pessoa\":{\"nome\":\"\"}}";
		VRaptorTestResult result = navigate()
													.post("/pessoas")
													.setContent(json)
													.addHeader("content-type", "application/json")
													.execute();
		result.wasStatus(422);
		assertEquals("{\"errors\":{\"nome\":[\"tamanho deve estar entre 1 e 200\"]}}", result.getResponseBody());
	}

}
