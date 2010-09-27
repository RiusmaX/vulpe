package org.vulpe.portal.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.vulpe.controller.struts.VulpeStrutsSimpleController;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.portal.core.model.entity.Content;
import org.vulpe.portal.core.model.entity.Portal;
import org.vulpe.portal.core.model.entity.Section;
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
			final List<Section> sections = getService(CoreService.class).readSection(new Section());
			setSessionAttribute("sections", sections);
			final List<Content> contents = getService(CoreService.class).readContent(new Content());
			setSessionAttribute("contents", contents);
		} catch (VulpeApplicationException e) {
			LOG.error(e);
		}
	}
}
