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

	User findUser(Long long0) throws VulpeApplicationException;

	void deleteUser(User user0) throws VulpeApplicationException;

	void deleteUser(List<User> list0) throws VulpeApplicationException;

	List<User> readUser(User user0) throws VulpeApplicationException;

	User createUser(User user0) throws VulpeApplicationException;

	void updateUser(User user0) throws VulpeApplicationException;

	Paging<User> pagingUser(User user0, Integer integer1, Integer integer2)
			throws VulpeApplicationException;

	List<User> persistUser(List<User> list0) throws VulpeApplicationException;

	Role findRole(Long long0) throws VulpeApplicationException;

	void deleteRole(Role role0) throws VulpeApplicationException;

	void deleteRole(List<Role> list0) throws VulpeApplicationException;

	List<Role> readRole(Role role0) throws VulpeApplicationException;

	Role createRole(Role role0) throws VulpeApplicationException;

	void updateRole(Role role0) throws VulpeApplicationException;

	Paging<Role> pagingRole(Role role0, Integer integer1, Integer integer2)
			throws VulpeApplicationException;

	List<Role> persistRole(List<Role> list0) throws VulpeApplicationException;

	SecureResource findSecureResource(Long long0) throws VulpeApplicationException;

	void deleteSecureResource(SecureResource secureResource0) throws VulpeApplicationException;

	void deleteSecureResource(List<SecureResource> list0) throws VulpeApplicationException;

	List<SecureResource> readSecureResource(SecureResource secureResource0)
			throws VulpeApplicationException;

	SecureResource createSecureResource(SecureResource secureResource0)
			throws VulpeApplicationException;

	void updateSecureResource(SecureResource secureResource0) throws VulpeApplicationException;

	Paging<SecureResource> pagingSecureResource(SecureResource secureResource0, Integer integer1,
			Integer integer2) throws VulpeApplicationException;

	List<SecureResource> persistSecureResource(List<SecureResource> list0)
			throws VulpeApplicationException;
}
