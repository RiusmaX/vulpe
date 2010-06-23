package org.vulpe.controller.vraptor.http.route;

import br.com.caelum.vraptor.http.route.PathAnnotationRoutesParser;
import br.com.caelum.vraptor.http.route.TypeFinder;
import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.proxy.Proxifier;

@ApplicationScoped
public class VulpePathAnnotationRoutesParser extends PathAnnotationRoutesParser {

	public VulpePathAnnotationRoutesParser(Proxifier proxifier, TypeFinder finder) {
		super(proxifier, finder);
	}

	@Override
	protected String extractControllerNameFrom(Class<?> type) {
		String prefix = extractPrefix(type);
		if ("".equals(prefix)) {
			String baseName = type.getSimpleName();
			if (baseName.endsWith("Controller")) {
				return "/" + baseName.substring(0, baseName.lastIndexOf("Controller"));
			} else if (baseName.endsWith("CRUDController")) {
				return "/" + baseName.substring(0, baseName.lastIndexOf("CRUDController"));
			} else if (baseName.endsWith("SelectController")) {
				return "/" + baseName.substring(0, baseName.lastIndexOf("SelectController"));
			} else if (baseName.endsWith("TabularController")) {
				return "/" + baseName.substring(0, baseName.lastIndexOf("TabularController"));
			} else if (baseName.endsWith("ReportController")) {
				return "/" + baseName.substring(0, baseName.lastIndexOf("ReportController"));
			}
			return "/" + baseName;
		} else {
			return prefix;
		}
	}
}
