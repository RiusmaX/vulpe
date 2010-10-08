package org.vulpe.portal.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.vulpe.controller.struts.VulpeStrutsSimpleController;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.portal.commons.ApplicationConstants.Core;
import org.vulpe.portal.commons.model.entity.Status;
import org.vulpe.portal.core.model.entity.Community;
import org.vulpe.portal.core.model.entity.Download;
import org.vulpe.portal.core.model.entity.Link;
import org.vulpe.portal.core.model.entity.Menu;
import org.vulpe.portal.core.model.entity.Portal;
import org.vulpe.portal.core.model.entity.Social;
import org.vulpe.portal.core.model.services.CoreService;

@SuppressWarnings("serial")
public class ApplicationBaseSimpleController extends VulpeStrutsSimpleController {

	protected static final Logger LOG = Logger.getLogger(ApplicationBaseSimpleController.class);

	@Override
	protected void postConstruct() {
		super.postConstruct();
		final List<Portal> portalList = getCachedClass().getSelf(Portal.class.getSimpleName());
		if (portalList != null) {
			for (Portal portal : portalList) {
				if (portal.getStatus().equals(Status.ACTIVE)) {
					setSessionAttribute(Core.VULPE_PORTAL, portal);
				}
			}
		}
		try {
			final List<Menu> menus = getService(CoreService.class).readMenu(new Menu());
			setSessionAttribute(Core.VULPE_PORTAL_MENUS, menus);
			final List<Download> downloads = getService(CoreService.class).readDownload(
					new Download());
			setSessionAttribute(Core.VULPE_PORTAL_DOWNLOADS, downloads);
			final List<Link> links = getService(CoreService.class).readLink(new Link());
			setSessionAttribute(Core.VULPE_PORTAL_LINKS, links);
			final List<Social> social = getService(CoreService.class).readSocial(new Social());
			setSessionAttribute(Core.VULPE_PORTAL_SOCIAL, social);
			final List<Community> communities = getService(CoreService.class).readCommunity(
					new Community());
			setSessionAttribute(Core.VULPE_PORTAL_COMMUNITIES, communities);
		} catch (VulpeApplicationException e) {
			LOG.error(e);
		}
	}
}