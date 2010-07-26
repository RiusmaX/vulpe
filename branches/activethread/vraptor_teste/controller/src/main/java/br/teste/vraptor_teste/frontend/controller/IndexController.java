package br.teste.vraptor_teste.frontend.controller;

import org.apache.log4j.Logger;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.controller.vraptor.AbstractVulpeVRaptorSimpleController;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;

@SuppressWarnings("serial")
@Resource
@Path("/frontend/Index")
@Controller(controllerType = ControllerType.FRONTEND)
public class IndexController extends AbstractVulpeVRaptorSimpleController {

	private static final Logger LOG = Logger.getLogger(IndexController.class);

}
