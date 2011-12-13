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
package org.vulpe.controller;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.VulpeContext;
import org.vulpe.commons.factory.AbstractVulpeBeanFactory;
import org.vulpe.commons.helper.VulpeCacheHelper;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.config.annotations.VulpeProject;
import org.vulpe.controller.commons.ExportDelegate;

/**
 * Vulpe Filter
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 */
public class VulpeFilter extends CharacterEncodingFilter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.filter.CharacterEncodingFilter#doFilterInternal
	 * (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		final VulpeContext vulpeContext = AbstractVulpeBeanFactory.getInstance().getBean(
				VulpeConstants.CONTEXT);
		if (vulpeContext != null) {
			vulpeContext.setLocale(request.getLocale());
		}
		final String encoding = VulpeConfigHelper.get(VulpeProject.class).characterEncoding();
		response.setCharacterEncoding(encoding);
		setEncoding(encoding);
		setForceEncoding(true);
		final String url = buildRequestUrl(request);
		// EverParameter.getInstance(request.getSession()).containsKey(Ever.EXPORT_CONTENT)
		// final String exportType = request.getParameter("exportType");
		if (url.contains("/export/") && (url.endsWith("pdf") || url.endsWith("xls"))) {
			final ContentResponseWrapper wrapper = new ContentResponseWrapper(response);
			super.doFilterInternal(request, wrapper, filterChain);
			ExportDelegate.export(request, response, wrapper);
		} else {
			super.doFilterInternal(request, response, filterChain);
		}
	}

	@Override
	protected void initFilterBean() throws ServletException {
		VulpeCacheHelper.getInstance().put(VulpeConstants.SERVLET_CONTEXT,
				getFilterConfig().getServletContext());
		super.initFilterBean();
	}

	/**
	 * Obtains the web application-specific fragment of the request URL.
	 * <p>
	 * Under normal spec conditions,
	 * 
	 * <pre>
	 * requestURI = contextPath + servletPath + pathInfo
	 * </pre>
	 * 
	 * But the requestURI is not decoded, whereas the servletPath and pathInfo
	 * are (SEC-1255). This method is typically used to return a URL for
	 * matching against secured paths, hence the decoded form is used in
	 * preference to the requestURI for building the returned value. But this
	 * method may also be called using dummy request objects which just have the
	 * requestURI and contextPatth set, for example, so it will fall back to
	 * using those.
	 * 
	 * @return the decoded URL, excluding any server name, context path or
	 *         servlet path
	 * 
	 */
	public static String buildRequestUrl(final HttpServletRequest request) {
		return buildRequestUrl(request.getServletPath(), request.getRequestURI(), request
				.getContextPath(), request.getPathInfo(), request.getQueryString());
	}

	/**
	 * Obtains the web application-specific fragment of the URL.
	 */
	private static String buildRequestUrl(final String servletPath, final String requestURI,
			final String contextPath, final String pathInfo, final String queryString) {
		final StringBuilder url = new StringBuilder();
		if (servletPath != null) {
			url.append(servletPath);
			if (pathInfo != null) {
				url.append(pathInfo);
			}
		} else {
			url.append(requestURI.substring(contextPath.length()));
		}
		if (queryString != null) {
			url.append("?").append(queryString);
		}
		return url.toString();
	}
}
