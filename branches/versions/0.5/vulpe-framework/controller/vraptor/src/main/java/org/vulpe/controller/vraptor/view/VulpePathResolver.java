/**
 * Vulpe Framework - Quick and Smart ;)
 * Copyright (C) 2011 Active Thread
 * 
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos da Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; tanto a versão 2 da
 * Licença como (a seu critério) qualquer versão mais nova.
 * 
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * 
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para a Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.
 */
/**
 * Vulpe Framework - Quick and Smart ;)
 * Copyright (C) 2011 Active Thread
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
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
