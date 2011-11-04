package org.jw.mmn.core.controller;

import java.util.List;

import org.jw.mmn.commons.ApplicationConstants.Core;
import org.jw.mmn.controller.ApplicationBaseController;
import org.jw.mmn.core.model.entity.ChangeCongregation;
import org.jw.mmn.core.model.entity.Congregation;
import org.jw.mmn.core.model.entity.Group;
import org.jw.mmn.core.model.services.CoreService;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.VulpeConstants.View;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.exception.VulpeApplicationException;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("core.ChangeCongregationController")
@SuppressWarnings( { "serial", "unchecked" })
@Controller(type = ControllerType.NONE)
public class ChangeCongregationController extends
		ApplicationBaseController<ChangeCongregation, Long> {

	protected List<Congregation> congregations;

	@Override
	protected void postConstruct() {
		super.postConstruct();
		congregations = (List<Congregation>) vulpe.cache().classes().get(
				Congregation.class.getSimpleName());
	}

	public void change() {
		controlResultForward();
		//vulpe.controller().resultPage("core/ChangeCongregation/ChangeCongregation.jsp");
	}

	public void selectValidate() {
		if (entity.getCongregation() != null && entity.getCongregation().getId() != null) {
			for (final Congregation congregation : congregations) {
				if (congregation.getId().equals(entity.getCongregation().getId())) {
					ever.put(Core.SELECTED_CONGREGATION, congregation);
					final Group grupo = new Group();
					grupo.setCongregation(congregation);
					try {
						final List<Group> grupos = vulpe.service(CoreService.class)
								.readGroup(grupo);
						ever.put(Core.GROUPS_OF_SELECTED_CONGREGATION, grupos);
					} catch (VulpeApplicationException e) {
						LOG.error(e);
					}
					final String currentLayout = ever.getAuto(View.CURRENT_LAYOUT);
					final String url = "FRONTEND".equals(currentLayout) ? "/frontend/Index"
							: "/backend/Index";
					vulpe.controller().redirectTo(url);
					return;
				}
			}
		}
		controlResultForward();
	}

}
