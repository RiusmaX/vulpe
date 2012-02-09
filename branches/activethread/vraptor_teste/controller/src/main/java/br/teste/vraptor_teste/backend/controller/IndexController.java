package br.teste.vraptor_teste.backend.controller;

import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.controller.vraptor.VulpeVRaptorController;
import org.vulpe.model.entity.impl.VulpeBaseSimpleEntity;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;

@SuppressWarnings("serial")
@Resource
@Path("/backend/Index")
@Controller(type = ControllerType.BACKEND)
public class IndexController extends VulpeVRaptorController<VulpeBaseSimpleEntity, Long> {

	@Get
	public void teste() {
		result.redirectTo("http://globo.com");
	}
}
