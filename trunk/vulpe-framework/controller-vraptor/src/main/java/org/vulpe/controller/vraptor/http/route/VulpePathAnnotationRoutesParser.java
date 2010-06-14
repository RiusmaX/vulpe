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
			if (baseName.endsWith("")) {
				return "/" + baseName.substring(0, baseName.lastIndexOf(""));
			} else if (baseName.endsWith("CRUD")) {
				return "/" + baseName.substring(0, baseName.lastIndexOf("CRUD"));
			} else if (baseName.endsWith("Select")) {
				return "/" + baseName.substring(0, baseName.lastIndexOf("Select"));
			} else if (baseName.endsWith("Tabular")) {
				return "/" + baseName.substring(0, baseName.lastIndexOf("Tabular"));
			} else if (baseName.endsWith("Report")) {
				return "/" + baseName.substring(0, baseName.lastIndexOf("Report"));
			}
			return "/" + baseName;
		} else {
			return prefix;
		}
	}
}
