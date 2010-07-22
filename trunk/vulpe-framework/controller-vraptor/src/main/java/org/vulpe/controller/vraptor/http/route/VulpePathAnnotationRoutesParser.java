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

}
