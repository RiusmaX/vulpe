package org.vulpe.portal.frontend.controller;

import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.VulpeConstants.Configuration.Now;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.entity.impl.VulpeBaseSimpleEntity;
import org.vulpe.portal.commons.ApplicationConstants.Core;
import org.vulpe.portal.commons.model.entity.Status;
import org.vulpe.portal.controller.ApplicationBaseController;
import org.vulpe.portal.core.model.entity.Content;
import org.vulpe.portal.core.model.entity.Download;
import org.vulpe.portal.core.model.entity.Link;
import org.vulpe.portal.core.model.entity.Portal;
import org.vulpe.portal.core.model.entity.Section;
import org.vulpe.portal.core.model.services.CoreService;

@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("frontend.IndexController")
@Controller(type = ControllerType.FRONTEND)
public class IndexController extends ApplicationBaseController<VulpeBaseSimpleEntity, Long> {

	private Long id;

	public void section() {
		try {
			final Section section = getService(CoreService.class).findSection(new Section(getId()));
			now.put(Now.CONTENT_TITLE, section.getName());
			now.put(Now.SHOW_CONTENT_TITLE, true);
			now.put(Now.CONTENT_SUBTITLE, section.getDescription());
			final Content content = new Content();
			content.setSection(section);
			content.setStatus(Status.ACTIVE);
			final List<Content> contents = getService(CoreService.class).readContent(content);
			now.put("contents", contents);
		} catch (VulpeApplicationException e) {
			LOG.error(e);
		}
		controlResultForward();
	}

	public void content() {
		try {
			final Content content = getService(CoreService.class).findContent(new Content(getId()));
			content.increaseView();
			getService(CoreService.class).updateContent(content);
			now.put(Now.CONTENT_TITLE, content.getTitle());
			now.put(Now.SHOW_CONTENT_SUBTITLE, true);
			now.put(Now.CONTENT_SUBTITLE, content.getSubtitle());
			now.put("content", content);
		} catch (VulpeApplicationException e) {
			LOG.error(e);
		}
		controlResultForward();
	}

	public void download() {
		try {
			final Download download = getService(CoreService.class).findDownload(
					new Download(getId()));
			download.increaseDownload();
			getService(CoreService.class).updateDownload(download);
			redirectTo(download.getUrl(), false);
		} catch (VulpeApplicationException e) {
			LOG.error(e);
		}
	}

	public void link() {
		try {
			final Link link = getService(CoreService.class).findLink(new Link(getId()));
			link.increaseClick();
			getService(CoreService.class).updateLink(link);
			redirectTo(link.getUrl(), false);
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
	public void frontend() {
		super.frontend();
		final Portal portal = ever.getSelf(Core.VULPE_PORTAL);
		if (VulpeValidationUtil.isNotEmpty(portal.getHomeSection())) {
			try {
				final Section section = getService(CoreService.class).findSection(
						portal.getHomeSection());
				now.put(Now.CONTENT_TITLE, section.getName());
				now.put(Now.SHOW_CONTENT_TITLE, true);
				now.put(Now.CONTENT_SUBTITLE, section.getDescription());
				final Content content = new Content();
				content.setSection(section);
				content.setStatus(Status.ACTIVE);
				final List<Content> contents = getService(CoreService.class).readContent(content);
				now.put("contents", contents);
			} catch (VulpeApplicationException e) {
				LOG.error(e);
			}
		}
	}
}
