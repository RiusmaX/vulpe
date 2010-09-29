package org.vulpe.portal.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.vulpe.controller.struts.VulpeStrutsSimpleController;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.portal.core.model.entity.Menu;
import org.vulpe.portal.core.model.entity.Portal;
import org.vulpe.portal.core.model.services.CoreService;

@SuppressWarnings("serial")
public class ApplicationBaseSimpleController extends VulpeStrutsSimpleController {

	protected static final Logger LOG = Logger.getLogger(ApplicationBaseSimpleController.class);

	@Override
	protected void postConstruct() {
		super.postConstruct();
		final List<Portal> portalList = getCachedClass().getSelf(Portal.class.getSimpleName());
		if (portalList != null) {
			setSessionAttribute("vulpePortal", portalList.get(0));
		}
		try {
			final List<Menu> menus = getService(CoreService.class).readMenu(new Menu());
			setSessionAttribute("menus", menus);
		} catch (VulpeApplicationException e) {
			LOG.error(e);
		}
	}
}
