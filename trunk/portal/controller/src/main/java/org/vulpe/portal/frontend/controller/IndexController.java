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
import org.vulpe.portal.controller.ApplicationBaseSimpleController;
import org.vulpe.portal.core.model.entity.Content;
import org.vulpe.portal.core.model.entity.Section;
import org.vulpe.portal.core.model.services.CoreService;

@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("frontend.IndexController")
@Controller(controllerType = ControllerType.FRONTEND)
public class IndexController extends ApplicationBaseSimpleController {

	protected final Logger LOG = Logger.getLogger(IndexController.class);

	private Long sectionId;
	private Long contentId;

	public String section() {
		try {
			final Section section = getService(CoreService.class).findSection(sectionId);
			now.put(Now.CONTENT_TITLE, section.getName());
			now.put(Now.SHOW_CONTENT_TITLE, true);
			now.put(Now.CONTENT_SUBTITLE, section.getDescription());
			final Content content = new Content();
			content.setSection(section);
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
			final Content content = getService(CoreService.class).findContent(contentId);
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

	public void setSectionId(Long sectionId) {
		this.sectionId = sectionId;
	}

	public Long getSectionId() {
		return sectionId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public Long getContentId() {
		return contentId;
	}
}
