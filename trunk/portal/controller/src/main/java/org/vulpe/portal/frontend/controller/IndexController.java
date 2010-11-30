package org.vulpe.portal.frontend.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.VulpeConstants.Configuration.Now;
import org.vulpe.commons.VulpeConstants.Controller.Forward;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.portal.commons.model.entity.Status;
import org.vulpe.portal.controller.ApplicationBaseSimpleController;
import org.vulpe.portal.core.model.entity.Content;
import org.vulpe.portal.core.model.entity.Download;
import org.vulpe.portal.core.model.entity.Section;
import org.vulpe.portal.core.model.services.CoreService;

@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("frontend.IndexController")
@Controller(type = ControllerType.FRONTEND)
public class IndexController extends ApplicationBaseSimpleController {

	protected final Logger LOG = Logger.getLogger(IndexController.class);

	private Long id;

	public String section() {
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
		return Forward.SUCCESS;
	}

	public String content() {
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
		return Forward.SUCCESS;
	}

	public String download() {
		try {
			final Download download = getService(CoreService.class).findDownload(new Download(getId()));
			download.increaseDownload();
			getService(CoreService.class).updateDownload(download);
			return redirectTo(download.getUrl(), false);
		} catch (VulpeApplicationException e) {
			LOG.error(e);
		}
		return null;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
