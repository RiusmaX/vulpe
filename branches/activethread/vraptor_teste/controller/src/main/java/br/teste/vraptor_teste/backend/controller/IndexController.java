package br.teste.vraptor_teste.backend.controller;

import org.apache.log4j.Logger;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.controller.vraptor.AbstractVulpeVRaptorSimpleController;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;

@SuppressWarnings("serial")
@Resource
@Path("/backend/Index")
@Controller(controllerType = ControllerType.BACKEND)
public class IndexController extends AbstractVulpeVRaptorSimpleController {

	Logger log = Logger.getLogger(IndexController.class);

	@Get
	public void teste() {
		result.redirectTo("http://globo.com");
	}
}
