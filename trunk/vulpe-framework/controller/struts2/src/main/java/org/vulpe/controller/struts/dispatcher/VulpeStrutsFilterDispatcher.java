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
package org.vulpe.controller.struts.dispatcher;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.struts2.dispatcher.ng.ExecuteOperations;
import org.apache.struts2.dispatcher.ng.PrepareOperations;
import org.apache.struts2.dispatcher.ng.filter.FilterHostConfig;
import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.VulpeConstants.View.Layout;

/**
 * Implementation of struts2 filter to inject utility classes of generic types
 * and converters.
 *
 * @author <a href="mailto:fabio.viana@vulpe.org">Fábio Viana</a>
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 */
@Component(VulpeConstants.FILTER_DISPATCHER)
public class VulpeStrutsFilterDispatcher extends StrutsPrepareAndExecuteFilter {

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.apache.struts2.dispatcher.FilterDispatcher#doFilter(javax.servlet
	 * .ServletRequest, javax.servlet.ServletResponse,
	 * javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final FilterInvocation filterInvocation = new FilterInvocation(request, response, chain);
		final String url = filterInvocation.getRequestUrl();
		if (url.contains(Layout.JS_CONTEXT) || url.contains(Layout.THEMES_CONTEXT)
				|| url.contains(Layout.CSS_CONTEXT) || url.contains(Layout.IMAGES_CONTEXT)
				|| url.contains(Layout.SUFFIX_JSP)) {
			chain.doFilter(request, response);
			return;
		}
		super.doFilter(request, response, chain);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		final VulpeInitOperations init = new VulpeInitOperations();
		try {
			final FilterHostConfig config = new FilterHostConfig(filterConfig);
			init.initLogging(config);
			final VulpeDispatcher dispatcher = (VulpeDispatcher) init.initDispatcher(config);
			init.initStaticContentLoader(config, dispatcher);

			prepare = new PrepareOperations(filterConfig.getServletContext(), dispatcher);
			execute = new ExecuteOperations(filterConfig.getServletContext(), dispatcher);
			this.excludedPatterns = init.buildExcludedPatternsList(dispatcher);

			postInit(dispatcher, filterConfig);
		} finally {
			init.cleanup();
		}
	}

}