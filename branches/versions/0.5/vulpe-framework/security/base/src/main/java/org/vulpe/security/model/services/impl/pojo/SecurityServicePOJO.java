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
package org.vulpe.security.model.services.impl.pojo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.vulpe.commons.beans.Paging;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.security.model.entity.Role;
import org.vulpe.security.model.entity.SecureResource;
import org.vulpe.security.model.entity.User;
import org.vulpe.security.model.services.SecurityService;
import org.vulpe.security.model.services.manager.RoleManager;
import org.vulpe.security.model.services.manager.SecureResourceManager;
import org.vulpe.security.model.services.manager.UserManager;

@Service("SecurityService")
@Transactional
public class SecurityServicePOJO implements SecurityService {

	@Autowired
	private transient UserManager userManager;

	@Autowired
	private transient RoleManager roleManager;

	@Autowired
	private transient SecureResourceManager secureResourceManager;

	@Transactional(readOnly = true)
	public User findUser(final User user) throws VulpeApplicationException {
		return userManager.find(user);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteUser(final User user) throws VulpeApplicationException {
		userManager.delete(user);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteUser(final List<User> list) throws VulpeApplicationException {
		userManager.delete(list);
	}

	@Transactional(readOnly = true)
	public List<User> readUser(final User user) throws VulpeApplicationException {
		return userManager.read(user);
	}

	@Transactional(readOnly = true)
	public List<User> getUsersByRole(final String roleName) throws VulpeApplicationException {
		return userManager.getUsersByRole(roleName);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public User createUser(final User user) throws VulpeApplicationException {
		return userManager.create(user);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public User updateUser(final User user) throws VulpeApplicationException {
		return userManager.update(user);
	}

	@Transactional(readOnly = true)
	public Paging<User> pagingUser(final User user, final Integer integer1, final Integer integer2)
			throws VulpeApplicationException {
		return userManager.paging(user, integer1, integer2);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<User> persistUser(final List<User> list) throws VulpeApplicationException {
		return userManager.persist(list);
	}

	@Transactional(readOnly = true)
	public Role findRole(final Role role) throws VulpeApplicationException {
		return roleManager.find(role);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteRole(final Role role) throws VulpeApplicationException {
		roleManager.delete(role);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteRole(final List<Role> list) throws VulpeApplicationException {
		roleManager.delete(list);
	}

	@Transactional(readOnly = true)
	public List<Role> readRole(final Role role) throws VulpeApplicationException {
		return roleManager.read(role);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Role createRole(final Role role) throws VulpeApplicationException {
		return roleManager.create(role);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public Role updateRole(final Role role) throws VulpeApplicationException {
		return roleManager.update(role);
	}

	@Transactional(readOnly = true)
	public Paging<Role> pagingRole(final Role role, final Integer integer1, final Integer integer2)
			throws VulpeApplicationException {
		return roleManager.paging(role, integer1, integer2);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<Role> persistRole(final List<Role> list) throws VulpeApplicationException {
		return roleManager.persist(list);
	}

	@Transactional(readOnly = true)
	public SecureResource findSecureResource(final SecureResource secureResource) throws VulpeApplicationException {
		return secureResourceManager.find(secureResource);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteSecureResource(final SecureResource secureResource)
			throws VulpeApplicationException {
		secureResourceManager.delete(secureResource);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteSecureResource(final List<SecureResource> list)
			throws VulpeApplicationException {
		secureResourceManager.delete(list);
	}

	@Transactional(readOnly = true)
	public List<SecureResource> readSecureResource(final SecureResource secureResource)
			throws VulpeApplicationException {
		return secureResourceManager.read(secureResource);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public SecureResource createSecureResource(final SecureResource secureResource)
			throws VulpeApplicationException {
		return secureResourceManager.create(secureResource);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public SecureResource updateSecureResource(final SecureResource secureResource)
			throws VulpeApplicationException {
		return secureResourceManager.update(secureResource);
	}

	@Transactional(readOnly = true)
	public Paging<SecureResource> pagingSecureResource(final SecureResource secureResource,
			final Integer integer1, final Integer integer2) throws VulpeApplicationException {
		return secureResourceManager.paging(secureResource, integer1, integer2);
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public List<SecureResource> persistSecureResource(final List<SecureResource> list)
			throws VulpeApplicationException {
		return secureResourceManager.persist(list);
	}
}