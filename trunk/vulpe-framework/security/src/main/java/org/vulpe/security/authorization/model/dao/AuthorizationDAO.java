package org.vulpe.security.authorization.model.dao;

import java.util.List;

import org.vulpe.security.model.entity.Role;
import org.vulpe.security.model.entity.SecureResource;


public interface AuthorizationDAO {

	SecureResource getSecureObject(String objectName);

	List<Role> getSecureObjectRoles(SecureResource secureObject);

}
