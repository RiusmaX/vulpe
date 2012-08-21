package org.vulpe.portal.core.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.portal.controller.PortalBaseController;
import org.vulpe.portal.core.model.entity.Download;
import org.vulpe.portal.core.model.services.CoreService;

/**
 * Controller implementation of Download
 */
@Component("core.DownloadController")
@SuppressWarnings({ "serial", "unchecked" })
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = CoreService.class)
public class DownloadController extends PortalBaseController<Download, Long> {

	@Override
	protected void createAfter() {
		super.createAfter();
		entity.setDownloads(0L);
	}
}
