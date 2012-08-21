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
package org.vulpe.security.model.services;

import java.util.List;

import org.vulpe.commons.beans.Paging;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.services.VulpeService;
import org.vulpe.security.model.entity.Role;
import org.vulpe.security.model.entity.SecureResource;
import org.vulpe.security.model.entity.User;

public interface SecurityService extends VulpeService {

	User findUser(User user) throws VulpeApplicationException;

	void deleteUser(User user) throws VulpeApplicationException;

	void deleteUser(List<User> list) throws VulpeApplicationException;

	List<User> readUser(User user) throws VulpeApplicationException;

	List<User> getUsersByRole(String roleName) throws VulpeApplicationException;

	User createUser(User user) throws VulpeApplicationException;

	User updateUser(User user) throws VulpeApplicationException;

	Paging<User> pagingUser(User user, Integer integer1, Integer integer2)
			throws VulpeApplicationException;

	List<User> persistUser(List<User> list) throws VulpeApplicationException;

	Role findRole(Role role) throws VulpeApplicationException;

	void deleteRole(Role role) throws VulpeApplicationException;

	void deleteRole(List<Role> list) throws VulpeApplicationException;

	List<Role> readRole(Role role) throws VulpeApplicationException;

	Role createRole(Role role) throws VulpeApplicationException;

	Role updateRole(Role role) throws VulpeApplicationException;

	Paging<Role> pagingRole(Role role, Integer integer1, Integer integer2)
			throws VulpeApplicationException;

	List<Role> persistRole(List<Role> list) throws VulpeApplicationException;

	SecureResource findSecureResource(SecureResource secureResource) throws VulpeApplicationException;

	void deleteSecureResource(SecureResource secureResource) throws VulpeApplicationException;

	void deleteSecureResource(List<SecureResource> list) throws VulpeApplicationException;

	List<SecureResource> readSecureResource(SecureResource secureResource)
			throws VulpeApplicationException;

	SecureResource createSecureResource(SecureResource secureResource)
			throws VulpeApplicationException;

	SecureResource updateSecureResource(SecureResource secureResource) throws VulpeApplicationException;

	Paging<SecureResource> pagingSecureResource(SecureResource secureResource, Integer integer1,
			Integer integer2) throws VulpeApplicationException;

	List<SecureResource> persistSecureResource(List<SecureResource> list)
			throws VulpeApplicationException;
}
