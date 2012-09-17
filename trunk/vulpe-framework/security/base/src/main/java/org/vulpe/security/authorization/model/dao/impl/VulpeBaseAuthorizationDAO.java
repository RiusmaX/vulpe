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
package org.vulpe.security.authorization.model.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.helper.VulpeCacheHelper;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.security.authorization.model.dao.VulpeAuthorizationDAO;
import org.vulpe.security.model.entity.Role;
import org.vulpe.security.model.entity.SecureResource;
import org.vulpe.security.model.entity.SecureResourceRole;

@Repository("VulpeAuthorizationDAO")
@Transactional
public class VulpeBaseAuthorizationDAO implements VulpeAuthorizationDAO {

	private transient final Map<SecureResource, List<Role>> secureObjectToRoles = new HashMap<SecureResource, List<Role>>();

	/*
	 * (non-Javadoc)
	 *
	 * @seeorg.vulpe.security.authorization.model.dao.
	 * VulpeAuthorizationDAO#getSecureObject(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public SecureResource getSecureObject(final String securityObjectName) {
		final Map<String, SecureResource> secureObjects = new HashMap<String, SecureResource>();
		final Map<String, Object> cachedClasses = VulpeCacheHelper.getInstance().get(
				VulpeConstants.CACHED_CLASSES);
		final List<SecureResource> secureResources = (List<SecureResource>) cachedClasses
				.get(SecureResource.class.getSimpleName());
		if (secureResources != null) {
			for (SecureResource secureResource : secureResources) {
				secureObjects.put(secureResource.getResourceName(), secureResource);
				if (VulpeValidationUtil.isNotEmpty(secureResource.getSecureResourceRoles())) {
					final List<Role> roles = new ArrayList<Role>();
					for (SecureResourceRole secureResourceRole : secureResource
							.getSecureResourceRoles()) {
						roles.add(secureResourceRole.getRole());
					}
					secureObjectToRoles.put(secureResource, roles);
				}
			}
		}
		final SecureResource secureResource = secureObjects.get(securityObjectName);
		if (secureResource == null) {
			for (SecureResource secureResource2 : secureObjects.values()) {
				if (StringUtils.isNotBlank(secureResource2.getResourceName())) {
					if (secureResource2.getResourceName().startsWith("*")
							&& secureResource2.getResourceName().endsWith("*")) {
						if (securityObjectName.contains(secureResource2.getResourceName()
								.replaceAll("\\*", ""))) {
							return secureResource2;
						}
					} else if (secureResource2.getResourceName().startsWith("*")) {
						if (securityObjectName.endsWith(secureResource2.getResourceName()
								.replaceAll("\\*", ""))) {
							return secureResource2;
						}
					} else {
						if (securityObjectName.startsWith(secureResource2.getResourceName()
								.replaceAll("\\*", ""))) {
							return secureResource2;
						}
					}
				}
			}
		}
		return secureResource;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @seeorg.vulpe.security.authorization.model.dao. VulpeAuthorizationDAO
	 * #getSecureObjectRoles(org.vulpe.security.authentication
	 * .model.entity.SecureResource)
	 */
	public List<Role> getSecureObjectRoles(final SecureResource secureObject) {
		return secureObjectToRoles.get(secureObject);
	}

}