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
package org.vulpe.security.web.authentication.logout;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import org.vulpe.commons.VulpeConstants.View;

public class VulpeLogoutFilter extends GenericFilterBean {

	// ~ Instance fields
	// ================================================================================================

	private String filterProcessesUrl = "/j_spring_security_logout";
	private List<LogoutHandler> handlers;
	private LogoutSuccessHandler logoutSuccessHandler;

	// ~ Constructors
	// ===================================================================================================

	/**
	 * Constructor which takes a <tt>LogoutSuccessHandler</tt> instance to
	 * determine the target destination after logging out. The list of
	 * <tt>LogoutHandler</tt>s are intended to perform the actual logout
	 * functionality (such as clearing the security context, invalidating the
	 * session, etc.).
	 */
	public VulpeLogoutFilter(LogoutSuccessHandler logoutSuccessHandler, LogoutHandler... handlers) {
		Assert.notEmpty(handlers, "LogoutHandlers are required");
		this.handlers = Arrays.asList(handlers);
		Assert.notNull(logoutSuccessHandler, "logoutSuccessHandler cannot be null");
		this.logoutSuccessHandler = logoutSuccessHandler;
	}

	public VulpeLogoutFilter(String logoutSuccessUrl, LogoutHandler... handlers) {
		Assert.notEmpty(handlers, "LogoutHandlers are required");
		this.handlers = Arrays.asList(handlers);
		Assert.isTrue(!StringUtils.hasLength(logoutSuccessUrl)
				|| UrlUtils.isValidRedirectUrl(logoutSuccessUrl), logoutSuccessUrl
				+ " isn't a valid redirect URL");
		SimpleUrlLogoutSuccessHandler urlLogoutSuccessHandler = new SimpleUrlLogoutSuccessHandler();
		if (StringUtils.hasText(logoutSuccessUrl)) {
			urlLogoutSuccessHandler.setDefaultTargetUrl(logoutSuccessUrl);
		}
		logoutSuccessHandler = urlLogoutSuccessHandler;
	}

	// ~ Methods
	// ========================================================================================================

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		if (requiresLogout(request, response)) {
			final String vulpeCurrentLayout = (String) request.getSession().getAttribute(
					View.CURRENT_LAYOUT);
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();

			if (logger.isDebugEnabled()) {
				logger.debug("Logging out user '" + auth
						+ "' and transferring to logout destination");
			}

			for (LogoutHandler handler : handlers) {
				handler.logout(request, response, auth);
			}

			request.getSession(true).setAttribute(View.CURRENT_LAYOUT, vulpeCurrentLayout);
			//request.getSession(true).setAttribute(Security.LOGOUT_EXECUTED, true);
			logoutSuccessHandler.onLogoutSuccess(request, response, auth);

			return;
		}

		chain.doFilter(request, response);
	}

	/**
	 * Allow subclasses to modify when a logout should take place.
	 *
	 * @param request
	 *            the request
	 * @param response
	 *            the response
	 *
	 * @return <code>true</code> if logout should occur, <code>false</code>
	 *         otherwise
	 */
	protected boolean requiresLogout(HttpServletRequest request, HttpServletResponse response) {
		String uri = request.getRequestURI();
		int pathParamIndex = uri.indexOf(';');

		if (pathParamIndex > 0) {
			// strip everything from the first semi-colon
			uri = uri.substring(0, pathParamIndex);
		}

		int queryParamIndex = uri.indexOf('?');

		if (queryParamIndex > 0) {
			// strip everything from the first question mark
			uri = uri.substring(0, queryParamIndex);
		}

		if ("".equals(request.getContextPath())) {
			return uri.endsWith(filterProcessesUrl);
		}

		return uri.endsWith(request.getContextPath() + filterProcessesUrl);
	}

	public void setFilterProcessesUrl(String filterProcessesUrl) {
		Assert.isTrue(UrlUtils.isValidRedirectUrl(filterProcessesUrl), filterProcessesUrl
				+ " isn't a valid value for" + " 'filterProcessesUrl'");
		this.filterProcessesUrl = filterProcessesUrl;
	}

	protected String getFilterProcessesUrl() {
		return filterProcessesUrl;
	}
}
