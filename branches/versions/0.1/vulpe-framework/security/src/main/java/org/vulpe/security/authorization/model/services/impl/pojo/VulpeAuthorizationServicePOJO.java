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
package org.vulpe.security.authorization.model.services.impl.pojo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.vulpe.security.authorization.model.dao.VulpeAuthorizationDAO;
import org.vulpe.security.authorization.model.services.VulpeAuthorizationService;
import org.vulpe.security.model.entity.Role;
import org.vulpe.security.model.entity.SecureResource;

@Service("VulpeAuthorizationService")
public class VulpeAuthorizationServicePOJO implements VulpeAuthorizationService {

	@Qualifier("VulpeAuthorizationDAO")
	@Autowired
	private VulpeAuthorizationDAO authorizationDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.vulpe.security.authorization.model.services.
	 * VulpeAuthorizationService#getSecureObject(java.lang.String)
	 */
	public SecureResource getSecureObject(final String secObjectName) {
		return authorizationDAO.getSecureObject(secObjectName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.vulpe.security.authorization.model.services. VulpeAuthorizationService
	 * #getSecureObjectRoles(org.vulpe.security.authentication .model.entity.SecureResource)
	 */
	public List<Role> getSecureObjectRoles(final SecureResource secureObject) {
		return authorizationDAO.getSecureObjectRoles(secureObject);
	}

	public VulpeAuthorizationDAO getAuthorizationDAO() {
		return authorizationDAO;
	}

	public void setAuthorizationDAO(final VulpeAuthorizationDAO authorizationDAO) {
		this.authorizationDAO = authorizationDAO;
	}

}
