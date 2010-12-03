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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.vulpe.commons.factory.SpringBeanFactory;
import org.vulpe.security.authentication.AuthenticationLoginBypass;

/**
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 */
public class VulpeUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.springframework.security.web.authentication.
	 * AbstractAuthenticationProcessingFilter
	 * #requiresAuthentication(javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		final boolean requires = super.requiresAuthentication(request, response);
		if (requires) {
			final AuthenticationLoginBypass authenticationLoginBypass = SpringBeanFactory.getInstance().getBean(AuthenticationLoginBypass.class.getSimpleName());
			if (authenticationLoginBypass != null) {
				authenticationLoginBypass.bypass();
			}
		}
		return requires;
	}
}
