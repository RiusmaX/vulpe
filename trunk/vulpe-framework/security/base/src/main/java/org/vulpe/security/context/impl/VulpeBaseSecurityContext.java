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
package org.vulpe.security.context.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.vulpe.commons.VulpeConstants.Security;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.security.authentication.callback.AfterUserAuthenticationCallback;
import org.vulpe.security.commons.VulpeSecurityUtil;
import org.vulpe.security.commons.VulpeSecurityConstants.Context;
import org.vulpe.security.commons.VulpeSecurityConstants.Context.ContextDefaults;
import org.vulpe.security.context.VulpeSecurityContext;
import org.vulpe.security.model.entity.Role;
import org.vulpe.security.model.entity.SecureResource;
import org.vulpe.security.model.entity.SecureResourceRole;
import org.vulpe.security.model.entity.User;
import org.vulpe.security.model.entity.UserRole;
import org.vulpe.security.model.services.SecurityService;

/**
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 */
@SuppressWarnings("serial")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component(Security.SECURITY_CONTEXT)
public class VulpeBaseSecurityContext extends VulpeSecurityUtil implements VulpeSecurityContext {

	private static final Logger LOG = Logger.getLogger(VulpeBaseSecurityContext.class);

	private Map<String, Object> securityMap = new HashMap<String, Object>();

	private List<String> userRoles;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.security.context.VulpeSecurityContext#initialize()
	 */
	@Override
	public void initialize() {
		try {
			final SecurityService securityService = getService(SecurityService.class);
			final ResourceBundle securityProperties = ResourceBundle.getBundle("security");
			final List<Role> roles = securityService.readRole(new Role());
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
				securityService.createRole(new Role(anonymousRole, anonymousRoleDescription));
				securityService
						.createRole(new Role(administratorRole, administratorRoleDescription));
			}
			final List<Role> administratorRoles = securityService.readRole(new Role(
					administratorRole));
			if (administratorRoles != null && !administratorRoles.isEmpty()) {
				final List<SecureResource> secureResources = securityService
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
						securityService.createSecureResource(secureResource);
					}
				}
				final List<User> users = securityService.readUser(new User());
				if (users == null || users.isEmpty()) {
					final UserRole userRole = new UserRole();
					userRole.setRole(administratorRoles.get(0));
					final List<UserRole> userRoles = new ArrayList<UserRole>();
					userRoles.add(userRole);
					securityService.createUser(new User(user, password, name, email, userRoles));
				}
			}
		} catch (VulpeApplicationException e) {
			LOG.error(e);
		}
	}

	@Override
	public boolean isAuthenticated() {
		final Authentication authentication = getAuthentication();
		if (!VulpeConfigHelper.isSecurityEnabled() || authentication == null) {
			return false;
		}
		final boolean autenticated = authentication.isAuthenticated()
				&& !(authentication instanceof AnonymousAuthenticationToken);
		return autenticated;
	}

	public void afterUserAuthenticationCallback() {
		if (isAuthenticated()) {
			try {
				final AfterUserAuthenticationCallback afterUserAuthentication = getBean(AfterUserAuthenticationCallback.class);
				if (afterUserAuthentication != null) {
					afterUserAuthentication.execute();
				}
			} catch (NoSuchBeanDefinitionException e) {
				LOG.error(e);
			}
		}
	}

	public void set(final String key, final Object value) {
		securityMap.put(key, value);
	}

	public Object get(final String key) {
		return securityMap.get(key);
	}

	private User user;

	public String getUsername() {
		final Object principal = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return principal instanceof UserDetails ? ((UserDetails) principal).getUsername()
				: principal.toString();
	}

	@SuppressWarnings("unchecked")
	public User getUser() {
		if (user == null) {
			final SecurityService securityServices = getService(SecurityService.class);
			User user = new User(getUsername());
			try {
				final List<User> users = securityServices.readUser(user);
				if (users != null && !users.isEmpty()) {
					user = users.get(0);
				}
			} catch (VulpeApplicationException e) {
				LOG.error(e);
			}
			this.user = user;
		}
		return user;
	}

	@Override
	public List<String> getUserRoles() {
		if (VulpeValidationUtil.isEmpty(userRoles)) {
			userRoles = new ArrayList<String>();
			if (isAuthenticated()) {
				for (final GrantedAuthority grantedAuthority : getAuthentication().getAuthorities()) {
					userRoles.add(grantedAuthority.getAuthority());
				}
			}
		}
		return userRoles;
	}
}
