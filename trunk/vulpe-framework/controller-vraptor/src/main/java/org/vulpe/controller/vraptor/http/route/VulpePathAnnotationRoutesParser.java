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
			if (baseName.endsWith("Action")) {
				return "/" + baseName.substring(0, baseName.lastIndexOf("Action"));
			} else if (baseName.endsWith("CRUDAction")) {
				return "/" + baseName.substring(0, baseName.lastIndexOf("CRUDAction"));
			} else if (baseName.endsWith("SelectAction")) {
				return "/" + baseName.substring(0, baseName.lastIndexOf("SelectAction"));
			} else if (baseName.endsWith("TabularAction")) {
				return "/" + baseName.substring(0, baseName.lastIndexOf("TabularAction"));
			} else if (baseName.endsWith("ReportAction")) {
				return "/" + baseName.substring(0, baseName.lastIndexOf("ReportAction"));
			}
			return "/" + baseName;
		} else {
			return prefix;
		}
	}
}
