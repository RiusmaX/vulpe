package org.vulpe.security.model.services.impl.pojo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.vulpe.common.beans.Paging;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.security.model.entity.Role;
import org.vulpe.security.model.entity.SecureResource;
import org.vulpe.security.model.entity.User;
import org.vulpe.security.model.services.SecurityServices;
import org.vulpe.security.model.services.manager.RoleManager;
import org.vulpe.security.model.services.manager.SecureResourceManager;
import org.vulpe.security.model.services.manager.UserManager;

@Service("SecurityServices")
@Transactional
public class SecurityServicesPOJOImpl implements SecurityServices {

	@Autowired
	private transient UserManager userManager;

	@Autowired
	private transient RoleManager roleManager;

	@Autowired
	private transient SecureResourceManager secureResourceManager;

	@Transactional(readOnly = true)
	public User findUser(final Long long0) throws VulpeApplicationException {
		return userManager.find(long0);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteUser(final User user0) throws VulpeApplicationException {
		userManager.delete(user0);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteUser(final List<User> list0) throws VulpeApplicationException {
		userManager.delete(list0);
	}

	@Transactional(readOnly = true)
	public List<User> readUser(final User user0) throws VulpeApplicationException {
		return userManager.read(user0);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public User createUser(final User user0) throws VulpeApplicationException {
		return userManager.create(user0);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateUser(final User user0) throws VulpeApplicationException {
		userManager.update(user0);
	}

	@Transactional(readOnly = true)
	public Paging<User> pagingUser(final User user0, final Integer integer1,
			final Integer integer2) throws VulpeApplicationException {
		return userManager.paging(user0, integer1, integer2);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<User> persistUser(final List<User> list0)
			throws VulpeApplicationException {
		return userManager.persist(list0);
	}

	@Transactional(readOnly = true)
	public Role findRole(final Long long0) throws VulpeApplicationException {
		return roleManager.find(long0);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteRole(final Role role0) throws VulpeApplicationException {
		roleManager.delete(role0);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteRole(final List<Role> list0) throws VulpeApplicationException {
		roleManager.delete(list0);
	}

	@Transactional(readOnly = true)
	public List<Role> readRole(final Role role0) throws VulpeApplicationException {
		return roleManager.read(role0);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Role createRole(final Role role0) throws VulpeApplicationException {
		return roleManager.create(role0);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateRole(final Role role0) throws VulpeApplicationException {
		roleManager.update(role0);
	}

	@Transactional(readOnly = true)
	public Paging<Role> pagingRole(final Role role0, final Integer integer1,
			final Integer integer2) throws VulpeApplicationException {
		return roleManager.paging(role0, integer1, integer2);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Role> persistRole(final List<Role> list0)
			throws VulpeApplicationException {
		return roleManager.persist(list0);
	}

	@Transactional(readOnly = true)
	public SecureResource findSecureResource(final Long long0)
			throws VulpeApplicationException {
		return secureResourceManager.find(long0);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteSecureResource(final SecureResource secureResource0)
			throws VulpeApplicationException {
		secureResourceManager.delete(secureResource0);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteSecureResource(final List<SecureResource> list0)
			throws VulpeApplicationException {
		secureResourceManager.delete(list0);
	}

	@Transactional(readOnly = true)
	public List<SecureResource> readSecureResource(
			final SecureResource secureResource0) throws VulpeApplicationException {
		return secureResourceManager.read(secureResource0);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public SecureResource createSecureResource(
			final SecureResource secureResource0) throws VulpeApplicationException {
		return secureResourceManager.create(secureResource0);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateSecureResource(final SecureResource secureResource0)
			throws VulpeApplicationException {
		secureResourceManager.update(secureResource0);
	}

	@Transactional(readOnly = true)
	public Paging<SecureResource> pagingSecureResource(
			final SecureResource secureResource0, final Integer integer1,
			final Integer integer2) throws VulpeApplicationException {
		return secureResourceManager
				.paging(secureResource0, integer1, integer2);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<SecureResource> persistSecureResource(
			final List<SecureResource> list0) throws VulpeApplicationException {
		return secureResourceManager.persist(list0);
	}
}