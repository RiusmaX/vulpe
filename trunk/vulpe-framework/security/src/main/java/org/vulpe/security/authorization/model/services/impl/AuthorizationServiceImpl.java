package org.vulpe.security.authorization.model.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.vulpe.security.authorization.model.dao.AuthorizationDAO;
import org.vulpe.security.authorization.model.services.AuthorizationService;
import org.vulpe.security.model.entity.Role;
import org.vulpe.security.model.entity.SecureResource;


public class AuthorizationServiceImpl implements AuthorizationService {

	@Qualifier("AuthorizationDAO")
	@Autowired
	private AuthorizationDAO authorizationDAO;

	/*
	 * (non-Javadoc)
	 *
	 * @seeorg.vulpe.security.authorization.model.services.
	 * AuthorizationService#getSecureObject(java.lang.String)
	 */
	public SecureResource getSecureObject(final String secObjectName) {
		return authorizationDAO.getSecureObject(secObjectName);
	}

	/*
	 * (non-Javadoc)
	 *
	 * @seeorg.vulpe.security.authorization.model.services.
	 * AuthorizationService
	 * #getSecureObjectRoles(org.vulpe.security
	 * .model.entity.SecureResource)
	 */
	public List<Role> getSecureObjectRoles(final SecureResource secureObject) {
		return authorizationDAO.getSecureObjectRoles(secureObject);
	}

	public AuthorizationDAO getAuthorizationDAO() {
		return authorizationDAO;
	}

	public void setAuthorizationDAO(final AuthorizationDAO authorizationDAO) {
		this.authorizationDAO = authorizationDAO;
	}

}
