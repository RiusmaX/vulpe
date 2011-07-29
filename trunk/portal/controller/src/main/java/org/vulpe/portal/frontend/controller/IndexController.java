package org.vulpe.portal.frontend.controller;

import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.entity.impl.VulpeBaseSimpleEntity;
import org.vulpe.portal.commons.ApplicationConstants.Core;
import org.vulpe.portal.commons.model.entity.Status;
import org.vulpe.portal.controller.PortalBaseController;
import org.vulpe.portal.core.model.entity.Content;
import org.vulpe.portal.core.model.entity.Download;
import org.vulpe.portal.core.model.entity.Link;
import org.vulpe.portal.core.model.entity.Portal;
import org.vulpe.portal.core.model.entity.Section;

@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("frontend.IndexController")
@Controller(type = ControllerType.FRONTEND)
public class IndexController extends PortalBaseController<VulpeBaseSimpleEntity, Long> {

	private Long id;

	public void section() {
		loadSection(getId());
		controlResultForward();
	}

	private void loadSection(final Long sectionId) {
		try {
			final Section section = getCoreService().findSection(
					new Section(sectionId));
			vulpe.view().content().title(section.getName().toString()).subtitle(
					section.getDescription().toString());
			final Content content = new Content();
			content.setSection(section);
			content.setStatus(Status.ACTIVE);
			final List<Content> contents = getCoreService().readContent(content);
			now.put("contents", contents);
		} catch (VulpeApplicationException e) {
			LOG.error(e);
		}

	}

	public void content() {
		try {
			final Content content = getCoreService().findContent(new Content(getId()));
			content.increaseView();
			getCoreService().updateContent(content);
			vulpe.view().content().title(content.getTitle().toString()).subtitle(
					content.getSubtitle().toString());
			now.put("content", content);
		} catch (VulpeApplicationException e) {
			LOG.error(e);
		}
		controlResultForward();
	}

	public void download() {
		try {
			final Download download = getCoreService().findDownload(
					new Download(getId()));
			download.increaseDownload();
			getCoreService().updateDownload(download);
			vulpe.controller().redirectTo(download.getUrl(), false);
		} catch (VulpeApplicationException e) {
			LOG.error(e);
		}
	}

	public void link() {
		try {
			final Link link = getCoreService().findLink(new Link(getId()));
			link.increaseClick();
			getCoreService().updateLink(link);
			vulpe.controller().redirectTo(link.getUrl(), false);
		} catch (VulpeApplicationException e) {
			LOG.error(e);
		}
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	@Override
	protected void frontendAfter() {
		super.frontendAfter();
		final Portal portal = ever.getSelf(Core.VULPE_PORTAL);
		if (VulpeValidationUtil.isNotEmpty(portal.getHomeSection())) {
			loadSection(portal.getHomeSection().getId());
		}
	}

}
