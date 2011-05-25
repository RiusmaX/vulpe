package org.jw.mmn.frontend.controller;

import java.util.List;

import org.jw.mmn.commons.ApplicationConstants.Core;
import org.jw.mmn.controller.ApplicationBaseController;
import org.jw.mmn.core.model.entity.Congregation;
import org.jw.mmn.core.model.entity.Group;
import org.jw.mmn.core.model.services.CoreService;
import org.jw.mmn.frontend.model.entity.Index;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.VulpeConstants.View;
import org.vulpe.commons.VulpeConstants.Controller.Forward;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.ExecuteAlways;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.exception.VulpeApplicationException;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("frontend.IndexController")
@SuppressWarnings( { "serial", "unchecked" })
@Controller(type = ControllerType.FRONTEND)
public class IndexController extends ApplicationBaseController<Index, Long> {

	public String teste() {
		controlResultForward();
		return Forward.SUCCESS;
	}

	protected final List<Congregation> congregations = (List<Congregation>) getCachedClasses().get(
			Congregation.class.getSimpleName());

	public void selecionarValidate() {
		if (getEntity().getCongregation() != null && getEntity().getCongregation().getId() != null) {
			for (final Congregation congregation : congregations) {
				if (congregation.getId().equals(getEntity().getCongregation().getId())) {
					ever.put(Core.SELECTED_CONGREGATION, congregation);
					final Group grupo = new Group();
					grupo.setCongregation(congregation);
					try {
						final List<Group> grupos = getService(CoreService.class).readGroup(grupo);
						ever.put(Core.GROUPS_OF_SELECTED_CONGREGATION, grupos);
					} catch (VulpeApplicationException e) {
						LOG.error(e);
					}

					redirectTo("/core/Member/select", true);
				}
			}
		}
		final String currentLayout = getSessionAttribute(View.CURRENT_LAYOUT);
		final String url = "FRONTEND".equals(currentLayout) ? "/frontend/Index" : "/backend/Index";
		redirectTo(url, true);
	}

	@ExecuteAlways
	public void init() {
		final Congregation congragation = getSessionAttribute(Core.SELECTED_CONGREGATION);
		if (congragation != null) {
			setEntity(new Index());
			getEntity().setCongregation(congragation);
		}
	}

}
