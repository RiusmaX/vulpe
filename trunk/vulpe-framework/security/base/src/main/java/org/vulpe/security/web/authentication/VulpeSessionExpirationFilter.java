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
package org.vulpe.security.web.authentication;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * 
 *
 */
public class VulpeSessionExpirationFilter implements Filter, InitializingBean {

	// ~ Instance fields
	// ================================================================================================

	private String expiredUrl;

	// ~ Methods
	// ========================================================================================================

	public void afterPropertiesSet() throws Exception {
		Assert.hasText(expiredUrl, "ExpiredUrl required");
	}

	/**
	 * Does nothing. We use IoC container lifecycle services instead.
	 */
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		Assert.isInstanceOf(HttpServletRequest.class, request,
				"Can only process HttpServletRequest");
		Assert.isInstanceOf(HttpServletResponse.class, response,
				"Can only process HttpServletResponse");

		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		final HttpServletResponse httpResponse = (HttpServletResponse) response;

		final HttpSession session = httpRequest.getSession(false);

		if (session == null && httpRequest.getRequestedSessionId() != null
				&& !httpRequest.isRequestedSessionIdValid()) {
			String targetUrl = httpRequest.getContextPath() + expiredUrl;
			httpResponse.sendRedirect(httpResponse.encodeRedirectURL(targetUrl));
			return;
		}

		chain.doFilter(request, response);
	}

	/**
	 * Does nothing. We use IoC container lifecycle services instead.
	 * 
	 * @param arg0
	 *            ignored
	 * 
	 * @throws ServletException
	 *             ignored
	 */
	public void init(FilterConfig arg0) throws ServletException {
	}

	public void setExpiredUrl(String expiredUrl) {
		this.expiredUrl = expiredUrl;
	}

}
