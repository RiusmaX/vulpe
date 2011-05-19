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
package org.vulpe.security.authorization.model.dao.impl.jpa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.helper.VulpeCacheHelper;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.model.dao.impl.jpa.VulpeBaseDAOJPA;
import org.vulpe.security.authorization.model.dao.VulpeAuthorizationDAO;
import org.vulpe.security.model.entity.Role;
import org.vulpe.security.model.entity.SecureResource;
import org.vulpe.security.model.entity.SecureResourceRole;

//@Repository("VulpeAuthorizationDAO")
@Transactional
public class VulpeAuthorizationDAOJPA extends VulpeBaseDAOJPA<SecureResource, Long> implements
		VulpeAuthorizationDAO {

	private transient final Map<String, SecureResource> secureObjects = new HashMap<String, SecureResource>();

	private transient final Map<SecureResource, List<Role>> secureObjectToRoles = new HashMap<SecureResource, List<Role>>();

	/*
	 * (non-Javadoc)
	 *
	 * @seeorg.vulpe.security.authorization.model.dao.
	 * VulpeAuthorizationDAO#getSecureObject(java.lang.String)
	 */
	public SecureResource getSecureObject(final String securityObjectName) {
		reloadAuthorizationInfo();
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

	@SuppressWarnings("unchecked")
	private void reloadAuthorizationInfo() {
		// List<SecureResource> secureResources = getList(new SecureResource());
		final Map<String, Object> cachedClasses = VulpeCacheHelper.getInstance().get(
				VulpeConstants.CACHED_CLASSES);
		final List<SecureResource> secureResources = (List<SecureResource>) cachedClasses
				.get(SecureResource.class.getSimpleName());
		if (secureResources != null) {
			secureObjects.clear();
			for (SecureResource secureResource : secureResources) {
				secureObjects.put(secureResource.getResourceName(), secureResource);
				if (VulpeValidationUtil.isNotEmpty(
						secureResource.getSecureResourceRoles())) {
					final List<Role> roles = new ArrayList<Role>();
					for (SecureResourceRole secureResourceRole : secureResource
							.getSecureResourceRoles()) {
						roles.add(secureResourceRole.getRole());
					}
					secureObjectToRoles.put(secureResource, roles);
				}
			}
		}
	}
}