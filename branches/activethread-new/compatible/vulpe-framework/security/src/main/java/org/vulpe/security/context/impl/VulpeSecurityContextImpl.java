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
package org.vulpe.security.context.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.security.commons.VulpeSecurityUtil;
import org.vulpe.security.commons.VulpeSecurityConstants.Context;
import org.vulpe.security.commons.VulpeSecurityConstants.Context.ContextDefaults;
import org.vulpe.security.context.VulpeSecurityContext;
import org.vulpe.security.model.entity.Role;
import org.vulpe.security.model.entity.SecureResource;
import org.vulpe.security.model.entity.SecureResourceRole;
import org.vulpe.security.model.entity.User;
import org.vulpe.security.model.entity.UserRole;
import org.vulpe.security.model.services.SecurityServices;

/**
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 */
@Component("VulpeSecurityContext")
public class VulpeSecurityContextImpl extends VulpeSecurityUtil implements VulpeSecurityContext {

	private static final Logger LOG = Logger.getLogger(VulpeSecurityContextImpl.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.security.context.VulpeSecurityContext#initialize()
	 */
	@Override
	public void initialize() {
		try {
			final SecurityServices securityServices = getService(SecurityServices.class);
			final ResourceBundle securityProperties = ResourceBundle.getBundle("security");
			final List<Role> roles = securityServices.readRole(new Role());
			String anonymousRole = ContextDefaults.ANONYMOUS_ROLE;
			String anonymousRoleDescription = ContextDefaults.ANONYMOUS_ROLE_DESCRIPTION;
			String administratorRole = ContextDefaults.ADMINISTRATOR_ROLE;
			String administratorRoleDescription = ContextDefaults.ADMINISTRATOR_ROLE_DESCRIPTION;
			String user = ContextDefaults.ADMINISTRATOR_USER;
			String password = ContextDefaults.ADMINISTRATOR_PASSWORD;
			String name = ContextDefaults.ADMINISTRATOR_NAME;
			String email = ContextDefaults.ADMINISTRATOR_EMAIL;
			String secureResourcesPath = ContextDefaults.SECURE_RESOURCES_PATH;

			if (securityProperties != null) {
				if (securityProperties.containsKey(Context.ANONYMOUS_ROLE)) {
					anonymousRole = securityProperties.getString(Context.ANONYMOUS_ROLE);
				}
				if (securityProperties.containsKey(Context.ANONYMOUS_ROLE_DESCRIPTION)) {
					anonymousRoleDescription = securityProperties
							.getString(Context.ANONYMOUS_ROLE_DESCRIPTION);
				}
				if (securityProperties.containsKey(Context.ADMINISTRATOR_ROLE)) {
					administratorRole = securityProperties.getString(Context.ADMINISTRATOR_ROLE);
				}
				if (securityProperties.containsKey(Context.ADMINISTRATOR_ROLE_DESCRIPTION)) {
					administratorRoleDescription = securityProperties
							.getString(Context.ADMINISTRATOR_ROLE_DESCRIPTION);
				}
				if (securityProperties.containsKey(Context.SECURE_RESOURCES_PATH)) {
					secureResourcesPath = securityProperties
							.getString(Context.SECURE_RESOURCES_PATH);
				}
				if (securityProperties.containsKey(Context.ADMINISTRATOR_USER)) {
					user = securityProperties.getString(Context.ADMINISTRATOR_USER);
				}
				if (securityProperties.containsKey(Context.ADMINISTRATOR_PASSWORD)) {
					password = securityProperties.getString(Context.ADMINISTRATOR_PASSWORD);
				}
				if (securityProperties.containsKey(Context.ADMINISTRATOR_NAME)) {
					name = securityProperties.getString(Context.ADMINISTRATOR_NAME);
				}
				if (securityProperties.containsKey(Context.ADMINISTRATOR_EMAIL)) {
					email = securityProperties.getString(Context.ADMINISTRATOR_EMAIL);
				}
			}
			if (roles == null || roles.isEmpty()) {
				securityServices.createRole(new Role(anonymousRole, anonymousRoleDescription));
				securityServices.createRole(new Role(administratorRole,
						administratorRoleDescription));
			}
			final List<Role> administratorRoles = securityServices.readRole(new Role(
					administratorRole));
			if (administratorRoles != null && !administratorRoles.isEmpty()) {
				final List<SecureResource> secureResources = securityServices
						.readSecureResource(new SecureResource());
				if (secureResources == null || secureResources.isEmpty()) {
					String[] paths = secureResourcesPath.split(",");
					for (String path : paths) {
						final SecureResourceRole secureResourceRole = new SecureResourceRole(
								administratorRoles.get(0));
						final List<SecureResourceRole> secureResourceRoles = new ArrayList<SecureResourceRole>();
						secureResourceRoles.add(secureResourceRole);
						final SecureResource secureResource = new SecureResource(path,
								secureResourceRoles);
						securityServices.createSecureResource(secureResource);
					}
				}
				final List<User> users = securityServices.readUser(new User());
				if (users == null || users.isEmpty()) {
					final UserRole userRole = new UserRole();
					userRole.setRole(administratorRoles.get(0));
					List<UserRole> userRoles = new ArrayList<UserRole>();
					userRoles.add(userRole);
					securityServices.createUser(new User(user, password, name, email, userRoles));
				}
			}
		} catch (VulpeApplicationException e) {
			LOG.error(e);
		}
	}
}
