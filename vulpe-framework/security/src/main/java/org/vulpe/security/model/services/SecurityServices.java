package org.vulpe.security.model.services;

import java.util.List;

import org.vulpe.common.beans.Paging;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.services.Services;
import org.vulpe.security.model.entity.Role;
import org.vulpe.security.model.entity.SecureResource;
import org.vulpe.security.model.entity.User;


public interface SecurityServices extends Services {

	User findUser(Long long0) throws VulpeApplicationException;

	void deleteUser(User user0)
			throws VulpeApplicationException;

	void deleteUser(List<User> list0)
			throws VulpeApplicationException;

	List<User> readUser(User user0)
			throws VulpeApplicationException;

	User createUser(User user0)
			throws VulpeApplicationException;

	void updateUser(User user0)
			throws VulpeApplicationException;

	Paging<User> pagingUser(User user0,
			Integer integer1, Integer integer2) throws VulpeApplicationException;

	List<User> persistUser(List<User> list0)
			throws VulpeApplicationException;

	Role findRole(Long long0) throws VulpeApplicationException;

	void deleteRole(Role role0)
			throws VulpeApplicationException;

	void deleteRole(List<Role> list0)
			throws VulpeApplicationException;

	List<Role> readRole(Role role0)
			throws VulpeApplicationException;

	Role createRole(Role role0)
			throws VulpeApplicationException;

	void updateRole(Role role0)
			throws VulpeApplicationException;

	Paging<Role> pagingRole(Role role0,
			Integer integer1, Integer integer2) throws VulpeApplicationException;

	List<Role> persistRole(List<Role> list0)
			throws VulpeApplicationException;
	
	SecureResource findSecureResource(Long long0) throws VulpeApplicationException;

	void deleteSecureResource(SecureResource secureResource0)
			throws VulpeApplicationException;

	void deleteSecureResource(List<SecureResource> list0)
			throws VulpeApplicationException;

	List<SecureResource> readSecureResource(SecureResource secureResource0)
			throws VulpeApplicationException;

	SecureResource createSecureResource(SecureResource secureResource0)
			throws VulpeApplicationException;

	void updateSecureResource(SecureResource secureResource0)
			throws VulpeApplicationException;

	Paging<SecureResource> pagingSecureResource(SecureResource secureResource0,
			Integer integer1, Integer integer2) throws VulpeApplicationException;

	List<SecureResource> persistSecureResource(List<SecureResource> list0)
			throws VulpeApplicationException;
}
