package org.vulpe.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("UserAuthentication")
public class UserAuthenticationImpl implements UserAuthentication {

	@Override
	public boolean isAuthenticated() {
		final Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		return authentication.isAuthenticated()
				&& !(authentication instanceof AnonymousAuthenticationToken);
	}

}
