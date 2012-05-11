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