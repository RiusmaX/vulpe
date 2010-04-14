package org.vulpe.security.authorization.model.services;

import java.util.List;

import org.vulpe.security.model.entity.Role;
import org.vulpe.security.model.entity.SecureResource;


public interface AuthorizationService {

	SecureResource getSecureObject(String secObjectName);

	List<Role> getSecureObjectRoles(SecureResource secureObject);

}
