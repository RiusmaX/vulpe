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
package org.vulpe.controller.vraptor.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.vulpe.commons.VulpeConstants.View;
import org.vulpe.commons.cache.VulpeCacheHelper;
import org.vulpe.controller.vraptor.util.VRaptorControllerUtil;

import br.com.caelum.vraptor.VRaptor;

public class VulpeVRaptorFilter extends VRaptor {
	
	private transient FilterConfig filterConfig = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.apache.struts2.dispatcher.FilterDispatcher#init(javax.servlet.
	 * FilterConfig)
	 */
	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
		this.filterConfig = filterConfig;
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		final VulpeCacheHelper cache = VulpeCacheHelper.getInstance();
		cache.put(View.APPLICATION_LOCALE, request.getLocale());
		VRaptorControllerUtil.setServletContext(filterConfig.getServletContext());

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		super.doFilter(request, response, chain);
	}
}