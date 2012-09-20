#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.frontend.controller;

import ${package}.controller.ApplicationBaseController;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.model.entity.impl.VulpeBaseSimpleEntity;

@SuppressWarnings({ "serial", "unchecked" })
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("frontend.IndexController")
@Controller(type = ControllerType.FRONTEND)
public class FrontendController extends ApplicationBaseController<VulpeBaseSimpleEntity, Long> {

}