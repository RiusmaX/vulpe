package org.vulpe.security.controller.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.FilterChainProxy;
import org.vulpe.common.Constants.Action;
import org.vulpe.common.Constants.Security;
import org.vulpe.common.helper.VulpeConfigHelper;

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
	public void doFilter(final ServletRequest request,
			final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		if (!VulpeConfigHelper.isSecurityEnabled()) {
			chain.doFilter(request, response);
			return;
		}
		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		if (httpRequest != null
				&& httpRequest.getRequestURI().contains(Action.ACTION_SUFFIX)
				&& !httpRequest.getRequestURI().contains(
						Action.URI.AUTHENTICATOR + Action.ACTION_SUFFIX)) {
			httpRequest.getSession().setAttribute(
					Security.VULPE_SECURITY_URL_REQUESTED,
					httpRequest.getRequestURI());
		}
		super.doFilter(request, response, chain);
	}
}
