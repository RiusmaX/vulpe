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
package org.vulpe.security.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.FilterChainProxy;
import org.vulpe.commons.VulpeConstants.Action;
import org.vulpe.commons.VulpeConstants.Security;
import org.vulpe.commons.helper.VulpeConfigHelper;

/**
 * 
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * @version 1.0
 * @since 1.0
 */
public class VulpeFilterChainProxy extends FilterChainProxy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.web.FilterChainProxy#doFilter(javax.servlet
	 * .ServletRequest, javax.servlet.ServletResponse,
	 * javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response,
			final FilterChain chain) throws IOException, ServletException {
		if (!VulpeConfigHelper.isSecurityEnabled()) {
			chain.doFilter(request, response);
			return;
		}
		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (httpRequest != null
				&& httpRequest.getRequestURI().contains(Action.ACTION_SUFFIX)
				&& !httpRequest.getRequestURI().contains(
						Action.URI.AUTHENTICATOR + Action.ACTION_SUFFIX)) {
			httpRequest.getSession().setAttribute(Security.VULPE_SECURITY_URL_REQUESTED,
					httpRequest.getRequestURI());
		}
		super.doFilter(request, response, chain);
	}
}
