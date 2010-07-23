package br.teste.vraptor_teste.core.controller;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.core.RequestInfo;

@Path("/core/Teste")
@Resource
public class TesteController {

	@Autowired
	private Result result;
	@Autowired
	private RequestInfo requestInfo;
	@Autowired
	private Validator validator;

	public void test() {
		result.include("notice", "teste");
	}


}
