/**
 * Vulpe Framework - Copyright (c) Active Thread
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vulpe.controller.struts.dispatcher;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

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

}