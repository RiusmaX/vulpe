#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.backend.controller;

import org.apache.log4j.Logger;

import ${package}.controller.ApplicationBaseSimpleController;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.model.entity.impl.VulpeBaseSimpleEntity;

@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("backend.IndexController")
@Controller(type = ControllerType.BACKEND)
public class IndexController extends ApplicationBaseSimpleController<VulpeBaseSimpleEntity, Long> {

	protected static final Logger LOG = Logger.getLogger(IndexController.class);

}
