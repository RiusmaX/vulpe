package org.vulpe.portal.core.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;
import org.vulpe.portal.controller.ApplicationBaseController;
import org.vulpe.portal.core.model.entity.Download;
import org.vulpe.portal.core.model.services.CoreService;

/**
 * Controller implementation of Download
 */
@Component("core.DownloadController")
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller(serviceClass = CoreService.class, select = @Select(pageSize = 5))
public class DownloadController extends ApplicationBaseController<Download, Long> {

	@Override
	protected void createAfter() {
		super.createAfter();
		getEntity().setDownloads(0L);
	}
}
