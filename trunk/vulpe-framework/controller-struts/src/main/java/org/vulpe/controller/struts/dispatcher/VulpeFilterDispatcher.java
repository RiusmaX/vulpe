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
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import ognl.OgnlRuntime;

import org.apache.struts2.dispatcher.FilterDispatcher;
import org.springframework.security.web.FilterInvocation;
import org.vulpe.commons.VulpeConstants.View.Layout;
import org.vulpe.controller.struts.util.GenericsNullHandler;
import org.vulpe.controller.struts.util.GenericsObjectTypeDeterminer;
import org.vulpe.controller.struts.util.GenericsPropertyAccessor;
import org.vulpe.controller.struts.util.XWorkSetPropertyAccessor;

import com.opensymphony.xwork2.util.ObjectTypeDeterminerFactory;

/**
 * Implementation of struts2 filter to inject utility classes of generic types
 * and converters.
 * 
 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
 */
@SuppressWarnings("unchecked")
public class VulpeFilterDispatcher extends FilterDispatcher {

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.apache.struts2.dispatcher.FilterDispatcher#init(javax.servlet.
	 * FilterConfig)
	 */
	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
		// sets ObjectTypeDeterminer to control generic types
		ObjectTypeDeterminerFactory.setInstance(new GenericsObjectTypeDeterminer());

		// sets access to properties with generics
		OgnlRuntime.setPropertyAccessor(Object.class, new GenericsPropertyAccessor());

		// sets manager of generic types to struts2
		OgnlRuntime.setNullHandler(Object.class, new GenericsNullHandler());

		// sets PropertyAccessor to HashSet
		OgnlRuntime.setPropertyAccessor(Set.class, new XWorkSetPropertyAccessor());
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		FilterInvocation filterInvocation = new FilterInvocation(request, response, chain);
		final String url = filterInvocation.getRequestUrl();
		if (url.contains(Layout.JS_CONTEXT) || url.contains(Layout.THEMES_CONTEXT)
				|| url.contains(Layout.CSS_CONTEXT) || url.contains(Layout.IMAGES_CONTEXT)
				|| url.endsWith(Layout.SUFFIX_JSP)) {
			chain.doFilter(request, response);
			return;
		}
		super.doFilter(request, response, chain);
	}
}