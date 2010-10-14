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
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.tuckey.web.filters.urlrewrite.Conf;
import org.tuckey.web.filters.urlrewrite.RewrittenUrl;
import org.tuckey.web.filters.urlrewrite.Rule;
import org.tuckey.web.filters.urlrewrite.UrlRewriteFilter;
import org.tuckey.web.filters.urlrewrite.UrlRewriter;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.VulpeConstants.View.Layout;
import org.vulpe.commons.factory.AbstractVulpeBeanFactory;

/**
 * Vulpe Filter Dispatcher.
 *
 * Add configurations of URL Rewrite Filter.
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 */
@SuppressWarnings("unchecked")
public class VulpeFilterDispatcher implements Filter {

	private static final Logger LOG = Logger.getLogger(VulpeFilterDispatcher.class);

	private static final UrlRewriteFilter URL_REWRITE_FILTER = new UrlRewriteFilter();

	private static UrlRewriter URL_REWRITER = null;

	private static final Filter FILTER_DISPATCHER = AbstractVulpeBeanFactory.getInstance().getBean(
			VulpeConstants.FILTER_DISPATCHER);

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
		initURLRewrite(filterConfig);
		initFilterDispatcher(filterConfig);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest,
	 * javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		final String url = buildRequestUrl(httpRequest);
		if (url.contains(Layout.JS_CONTEXT) || url.contains(Layout.THEMES_CONTEXT)
				|| url.contains(Layout.CSS_CONTEXT) || url.contains(Layout.IMAGES_CONTEXT)
				|| url.contains(Layout.SUFFIX_JSP)) {
			chain.doFilter(request, response);
			return;
		}
		if (!doFilterURLRewrite(request, response, chain, url)) {
			doFilterDispatcher(request, response, chain);
		}
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see javax.servlet.Filter#destroy()
	 */
	@Override
	public void destroy() {
		destroyURLRewrite();
		destroyFilterDispatcher();
	}

	/**
	 * Initiate URL Rewrite Filter.
	 *
	 * @param filterConfig
	 * @throws ServletException
	 */
	private void initURLRewrite(final FilterConfig filterConfig) throws ServletException {
		URL_REWRITE_FILTER.init(filterConfig);
		final ServletContext context = filterConfig.getServletContext();
		String confPath = filterConfig.getInitParameter("confPath");
		if (StringUtils.isEmpty(confPath)) {
			confPath = UrlRewriteFilter.DEFAULT_WEB_CONF_PATH;
		}
		URL confUrl = null;
		try {
			confUrl = context.getResource(confPath);
		} catch (MalformedURLException e) {
			LOG.debug(e);
		}
		String confUrlStr = null;
		if (confUrl != null) {
			confUrlStr = confUrl.toString();
		}
		final InputStream inputStream = context.getResourceAsStream(confPath);
		final Conf conf = new Conf(context, inputStream, confPath, confUrlStr, false);
		URL_REWRITER = new UrlRewriter(conf);
	}

	/**
	 * Initiate Filter Dispatcher.
	 *
	 * @param filterConfig
	 * @throws ServletException
	 */
	private void initFilterDispatcher(final FilterConfig filterConfig) throws ServletException {
		if (FILTER_DISPATCHER != null) {
			FILTER_DISPATCHER.init(filterConfig);
		}
	}

	/**
	 * Filter with URL Rewriter.
	 *
	 * @param request
	 * @param response
	 * @param chain
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	private boolean doFilterURLRewrite(final ServletRequest request,
			final ServletResponse response, final FilterChain chain, final String url)
			throws IOException, ServletException {
		final HttpServletRequest hsRequest = (HttpServletRequest) request;
		final HttpServletResponse hsResponse = (HttpServletResponse) response;
		boolean rewrite = false;
		for (final Rule rule : (List<Rule>) URL_REWRITER.getConf().getRules()) {
			try {
				final RewrittenUrl rewrittenUrl = rule.matches(url, hsRequest, hsResponse);
				if (rewrittenUrl != null) {
					rewrite = true;
					URL_REWRITE_FILTER.doFilter(hsRequest, hsResponse, chain);
					break;
				}
			} catch (InvocationTargetException e) {
				LOG.error(e);
			}
		}
		return rewrite;
	}

	/**
	 * Execute Filter Dispatcher.
	 *
	 * @param request
	 * @param response
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	private void doFilterDispatcher(final ServletRequest request, final ServletResponse response,
			final FilterChain chain) throws IOException, ServletException {
		if (FILTER_DISPATCHER != null) {
			FILTER_DISPATCHER.doFilter(request, response, chain);
		}
	}

	/**
	 * Destroy URL Rewrite Filter.
	 */
	private void destroyURLRewrite() {
		URL_REWRITE_FILTER.destroy();
		URL_REWRITER = null;
	}

	/**
	 * Destroy Filter Dispatcher.
	 */
	private void destroyFilterDispatcher() {
		if (FILTER_DISPATCHER != null) {
			FILTER_DISPATCHER.destroy();
		}
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