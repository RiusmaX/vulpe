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
import org.vulpe.commons.helper.VulpeCacheHelper;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.config.annotations.VulpeProject;

public class VulpeFilter extends CharacterEncodingFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		final VulpeContext vulpeContext = VulpeContext.getInstance();
		if (vulpeContext != null) {
			vulpeContext.setLocale(request.getLocale());
			vulpeContext.setRequest((HttpServletRequest) request);
			vulpeContext.setResponse((HttpServletResponse) response);
			vulpeContext.setSession(vulpeContext.getRequest().getSession());
		}
		setEncoding(VulpeConfigHelper.get(VulpeProject.class).characterEncoding());
		setForceEncoding(true);
		super.doFilterInternal(request, response, filterChain);
	}

	@Override
	protected void initFilterBean() throws ServletException {
		VulpeCacheHelper.getInstance().put(VulpeConstants.CURRENT_SERVLET_CONTEXT,
				getFilterConfig().getServletContext());
		super.initFilterBean();
	}
}
