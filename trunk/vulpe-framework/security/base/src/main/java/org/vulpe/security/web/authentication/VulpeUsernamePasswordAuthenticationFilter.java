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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.vulpe.commons.VulpeConstants.Controller.URI;
import org.vulpe.commons.VulpeConstants.View.Layout;
import org.vulpe.commons.util.VulpeReflectUtil;

/**
 *
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 */
public class VulpeUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, authResult);
		changeSavedRequest(request);
	}

	/**
	 *
	 * @param request
	 */
	public void changeSavedRequest(final HttpServletRequest request) {
		final DefaultSavedRequest savedRequest = (DefaultSavedRequest) request.getSession().getAttribute(
				DefaultSavedRequest.SPRING_SECURITY_SAVED_REQUEST_KEY);
		if (savedRequest != null && !savedRequest.getRequestURI().contains(URI.AUTHENTICATOR)) {
			final String url = savedRequest.getRedirectUrl();
			if (url.contains(Layout.JS_CONTEXT) || url.contains(Layout.THEMES_CONTEXT)
					|| url.contains(Layout.CSS_CONTEXT) || url.contains(Layout.IMAGES_CONTEXT)
					|| url.contains(Layout.SUFFIX_JSP)) {
				VulpeReflectUtil.getInstance().setFieldValue(savedRequest, "redirectUrl", "index.jsp");
			}
			request.getSession().setAttribute(DefaultSavedRequest.SPRING_SECURITY_SAVED_REQUEST_KEY, savedRequest);
		}
	}

}
