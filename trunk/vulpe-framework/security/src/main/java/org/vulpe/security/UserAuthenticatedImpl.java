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
package org.vulpe.security;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.vulpe.common.model.services.VulpeServiceLocator;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.security.model.entity.User;
import org.vulpe.security.model.services.SecurityServices;

@Component("UserAuthenticated")
public class UserAuthenticatedImpl implements UserAuthenticated {

	static final Logger LOG = Logger.getLogger(UserAuthenticatedImpl.class);

	final Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	private User user;

	@Override
	public String getUsername() {
		final String username = principal instanceof UserDetails ? ((UserDetails) principal)
				.getUsername() : principal.toString();
		setUser(username);
		return username;
	}

	protected User getUser() {
		return user;
	}

	protected void setUser(String username) {
		final SecurityServices securityServices = VulpeServiceLocator.getInstance().getService(
				SecurityServices.class);
		User user = new User();
		user.setUsername(username);
		try {
			user = securityServices.readUser(user).get(0);
		} catch (VulpeApplicationException e) {
			LOG.error(e);
		}
		this.user = user;
	}

	@Override
	public String getUserName() {
		return user.getName();
	}

}
