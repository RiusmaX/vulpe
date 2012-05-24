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
package org.vulpe.security.web.access.intercept;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.vulpe.security.authorization.model.services.VulpeAuthorizationService;
import org.vulpe.security.model.entity.Role;
import org.vulpe.security.model.entity.SecureResource;

/**
 * This <code>FilterInvocationDefinitionSource</code> implementation uses
 * database and in turn is used for dynamic authorization.
 *
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 *
 */
@SuppressWarnings("rawtypes")
public class DatabaseDrivenFilterInvocationDefinitionSource implements
		FilterInvocationSecurityMetadataSource {

	@Qualifier("VulpeAuthorizationService")
	@Autowired
	private VulpeAuthorizationService authorizationService;

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
			final Collection<ConfigAttribute> list = new ArrayList<ConfigAttribute>();
			for (final Role role : secureObjectRoles) {
				final ConfigAttribute configAttribute = new SecurityConfig(role.getName());
				list.add(configAttribute);
			}
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
	public Collection getConfigAttributeDefinitions() {
		return null;
	}

	public VulpeAuthorizationService getAuthorizationService() {
		return authorizationService;
	}

	public void setAuthorizationService(final VulpeAuthorizationService authorizationService) {
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
