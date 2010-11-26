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

	void updateUser(User user) throws VulpeApplicationException;

	Paging<User> pagingUser(User user, Integer integer1, Integer integer2)
			throws VulpeApplicationException;

	List<User> persistUser(List<User> list) throws VulpeApplicationException;

	Role findRole(Role role) throws VulpeApplicationException;

	void deleteRole(Role role) throws VulpeApplicationException;

	void deleteRole(List<Role> list) throws VulpeApplicationException;

	List<Role> readRole(Role role) throws VulpeApplicationException;

	Role createRole(Role role) throws VulpeApplicationException;

	void updateRole(Role role) throws VulpeApplicationException;

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

	void updateSecureResource(SecureResource secureResource) throws VulpeApplicationException;

	Paging<SecureResource> pagingSecureResource(SecureResource secureResource, Integer integer1,
			Integer integer2) throws VulpeApplicationException;

	List<SecureResource> persistSecureResource(List<SecureResource> list)
			throws VulpeApplicationException;
}
