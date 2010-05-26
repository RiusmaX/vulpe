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
package org.vulpe.security.authentication.callback.impl.pojo;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.security.authentication.callback.UserAuthenticatedCallback;
import org.vulpe.security.commons.VulpeSecurityCallbackUtil;
import org.vulpe.security.model.entity.User;
import org.vulpe.security.model.services.SecurityServices;

@Service("UserAuthenticatedCallback")
public class UserAuthenticatedCallbackPOJOImpl extends VulpeSecurityCallbackUtil implements UserAuthenticatedCallback {

	static final Logger LOG = Logger.getLogger(UserAuthenticatedCallbackPOJOImpl.class);

	private User user;

	@Override
	public String getUsername() {
		final Object principal = SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		return principal instanceof UserDetails ? ((UserDetails) principal).getUsername()
				: principal.toString();
	}

	protected User getUser() {
		return user;
	}

	protected void setUser(String username) {
		final SecurityServices securityServices = getService(SecurityServices.class);
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
	public String getName() {
		return user.getName();
	}

	@Override
	public void execute() {
		setUser(getUsername());
	}

	@Override
	public Long getId() {
		return user.getId();
	}

}
