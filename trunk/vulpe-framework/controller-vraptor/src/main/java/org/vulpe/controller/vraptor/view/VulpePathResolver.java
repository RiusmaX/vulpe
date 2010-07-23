package org.vulpe.controller.vraptor.view;

import org.vulpe.commons.VulpeConstants.View.Layout;

import br.com.caelum.vraptor.http.FormatResolver;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.caelum.vraptor.view.PathResolver;

@Component
public class VulpePathResolver implements PathResolver {

	private final FormatResolver resolver;

	public VulpePathResolver(FormatResolver resolver) {
		this.resolver = resolver;
	}

	public String pathFor(ResourceMethod method) {
		final String format = resolver.getAcceptFormat();

		String suffix = "";
		if (format != null && !format.equals("html")) {
			suffix = "." + format;
		}
		final String name = method.getResource().getType().getSimpleName();
		final String folderName = extractControllerFromName(name);
		@SuppressWarnings("unused")
		final String uri = getPrefix(method.getResource().getType()) + folderName + "/"
				+ method.getMethod().getName() + suffix + "." + getExtension();
		return Layout.PROTECTED_JSP_COMMONS + Layout.BODY_JSP;
	}

	protected String getPrefix(final Class<?> clazz) {
		return Layout.PROTECTED_JSP + getModuleName(clazz);
	}

	protected String getExtension() {
		return "jsp";
	}

	protected String extractControllerFromName(String baseName) {
		baseName = lowerFirstCharacter(baseName);
		if (baseName.endsWith("Controller")) {
			return baseName.substring(0, baseName.lastIndexOf("Controller"));
		}
		return baseName;
	}

	private String lowerFirstCharacter(String baseName) {
		return baseName.toLowerCase().substring(0, 1) + baseName.substring(1, baseName.length());
	}

	protected String getModuleName(final Class<?> clazz) {
		final String className = clazz.getName();
		final String classSimpleName = clazz.getSimpleName();
		final String moduleName = className.substring(0, className.indexOf(".controller."
				.concat(classSimpleName)));
		return moduleName.substring(moduleName.lastIndexOf(".") + 1);
	}

}
