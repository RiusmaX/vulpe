package org.vulpe.controller.vraptor.view;

import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.view.AcceptHeaderToFormat;
import br.com.caelum.vraptor.view.DefaultPathResolver;

@Component
public class VulpePathResolver extends DefaultPathResolver {

	public VulpePathResolver(HttpServletRequest request, AcceptHeaderToFormat acceptHeaderToFormat) {
		super(request, acceptHeaderToFormat);
	}

	@Override
	protected String getPrefix() {
		return "/WEB-INF/protected-jsps/";
	}

	@Override
	protected String extractControllerFromName(String baseName) {
		if (baseName.endsWith("Action")) {
			return baseName.substring(0, baseName.lastIndexOf("Action"));
		} else if (baseName.endsWith("CRUDAction")) {
			return baseName.substring(0, baseName.lastIndexOf("CRUDAction"));
		} else if (baseName.endsWith("SelectAction")) {
			return baseName.substring(0, baseName.lastIndexOf("SelectAction"));
		} else if (baseName.endsWith("TabularAction")) {
			return baseName.substring(0, baseName.lastIndexOf("TabularAction"));
		} else if (baseName.endsWith("ReportAction")) {
			return baseName.substring(0, baseName.lastIndexOf("ReportAction"));
		}
		return baseName;
	}

}
