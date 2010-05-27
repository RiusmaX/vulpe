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
package org.vulpe.security.web.access.intercept;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.vulpe.security.authorization.model.services.AuthorizationService;
import org.vulpe.security.model.entity.Role;
import org.vulpe.security.model.entity.SecureResource;

/**
 * This <code>FilterInvocationDefinitionSource</code> implementation uses
 * database and in turn is used for dynamic authorization.
 * 
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * @version 1.0
 * @since 1.0
 * 
 */
public class DatabaseDrivenFilterInvocationDefinitionSource implements
		FilterInvocationSecurityMetadataSource {

	private AuthorizationService authorizationService;

	public Collection<ConfigAttribute> lookupAttributes(final String url) {
		String newUrl = url;
		// Strip anything after a question mark symbol, as per SEC-161. See also
		// SEC-321
		final int firstQuestionMarkIndex = newUrl.indexOf("?");

		if (firstQuestionMarkIndex != -1) {
			newUrl = newUrl.substring(0, firstQuestionMarkIndex);
		}
		final SecureResource secureObject = authorizationService.getSecureObject(newUrl);
		if (secureObject == null) {// if secure object not exist in database
			return null;
		}
		// retrieving roles associated with this secure object
		final List<Role> secureObjectRoles = authorizationService
				.getSecureObjectRoles(secureObject);
		// creating ConfigAttributeDefinition
		if (secureObjectRoles != null && !secureObjectRoles.isEmpty()) {
			final StringBuffer roles = new StringBuffer();
			for (Role sor : secureObjectRoles) {
				roles.append(sor.getName()).append(",");
			}
			Collection<ConfigAttribute> list = new ArrayList<ConfigAttribute>();
			ConfigAttribute config = new SecurityConfig(roles.toString().substring(0,
					roles.length() - 1));
			list.add(config);
			return list;
		}
		return null;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.springframework.security.intercept.ObjectDefinitionSource#
	 * getConfigAttributeDefinitions()
	 */
	@SuppressWarnings("unchecked")
	public Collection getConfigAttributeDefinitions() {
		return null;
	}

	public AuthorizationService getAuthorizationService() {
		return authorizationService;
	}

	public void setAuthorizationService(final AuthorizationService authorizationService) {
		this.authorizationService = authorizationService;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.access.SecurityMetadataSource#supports(java
	 * .lang.Class)
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

	@Override
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		if ((object == null) || !this.supports(object.getClass())) {
			throw new IllegalArgumentException("Object must be a FilterInvocation");
		}

		return this.lookupAttributes(((FilterInvocation) object).getRequestUrl());
	}

}
