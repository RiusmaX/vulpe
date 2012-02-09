package br.teste.vraptor_teste.frontend.controller;

import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.controller.vraptor.VulpeVRaptorController;
import org.vulpe.model.entity.impl.VulpeBaseSimpleEntity;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;

@SuppressWarnings("serial")
@Resource
@Path("/frontend/Index")
@Controller(type = ControllerType.FRONTEND)
public class IndexController extends VulpeVRaptorController<VulpeBaseSimpleEntity, Long> {

}
