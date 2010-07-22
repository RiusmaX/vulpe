package org.vulpe.controller.vraptor.view;

import javax.servlet.http.HttpServletRequest;

import org.vulpe.commons.VulpeConstants.View.Layout;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.caelum.vraptor.view.AcceptHeaderToFormat;
import br.com.caelum.vraptor.view.DefaultPathResolver;

@Component
public class VulpePathResolver extends DefaultPathResolver {

	private final HttpServletRequest request;
	private final AcceptHeaderToFormat acceptHeaderToFormat;

	public VulpePathResolver(HttpServletRequest request, AcceptHeaderToFormat acceptHeaderToFormat) {
		super(request, acceptHeaderToFormat);
		this.request = request;
		this.acceptHeaderToFormat = acceptHeaderToFormat;
	}

	@Override
	protected String getPrefix() {
		return Layout.PROTECTED_JSP + request.getAttribute("vulpeModuleURI");
	}

	@Override
	public String pathFor(ResourceMethod method) {
		String acceptedHeader = request.getHeader("Accept");
		String format = "html";

		if (acceptedHeader != null) {
			format = acceptHeaderToFormat.getFormat(acceptedHeader);
		}

		String formatParam = request.getParameter("_format");
		if (formatParam != null) {
			format = formatParam;
		}

		String suffix = "";
		if (format != null && !format.equals("html")) {
			suffix = "." + format;
		}
		String name = method.getResource().getType().getSimpleName();
		String folderName = extractControllerFromName(name);
		@SuppressWarnings("unused")
		String uri = getPrefix() + folderName + "/" + method.getMethod().getName() + suffix + "."
				+ getExtension();
		return Layout.PROTECTED_JSP_COMMONS + Layout.BODY_JSP;
	}

}
