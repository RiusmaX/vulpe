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
import org.vulpe.controller.struts.util.GenericsNullHandler;
import org.vulpe.controller.struts.util.GenericsObjectTypeDeterminer;
import org.vulpe.controller.struts.util.GenericsPropertyAccessor;
import org.vulpe.controller.struts.util.StrutsControllerUtil;
import org.vulpe.controller.struts.util.XWorkSetPropertyAccessor;

import com.opensymphony.xwork2.util.ObjectTypeDeterminerFactory;

/**
 * Implementation of struts2 filter to inject utility classes of generic types
 * and converters.
 * 
 * @author <a href="mailto:fabio.viana@activethread.com.br">F·bio Viana</a>
 */
public class VulpeFilterDispatcher extends FilterDispatcher {

	private transient FilterConfig filterConfig = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.apache.struts2.dispatcher.FilterDispatcher#init(javax.servlet.
	 * FilterConfig)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void init(final FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
		this.filterConfig = filterConfig;
		// sets ObjectTypeDeterminer to control generic types
		ObjectTypeDeterminerFactory.setInstance(new GenericsObjectTypeDeterminer());

		// sets access to properties with generics
		OgnlRuntime.setPropertyAccessor(Object.class, new GenericsPropertyAccessor());

		// sets manager of generic types to struts2
		OgnlRuntime.setNullHandler(Object.class, new GenericsNullHandler());

		// sets PropertyAccessor to HashSet
		OgnlRuntime.setPropertyAccessor(Set.class, new XWorkSetPropertyAccessor());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.struts2.dispatcher.FilterDispatcher#doFilter(javax.servlet
	 * .ServletRequest, javax.servlet.ServletResponse,
	 * javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response,
			final FilterChain chain) throws IOException, ServletException {
		try {
			StrutsControllerUtil.setServletContext(this.filterConfig.getServletContext());

			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
		} finally {
			super.doFilter(request, response, chain);
		}
	}
}