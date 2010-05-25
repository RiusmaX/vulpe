package org.vulpe.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.vulpe.commons.beans.AbstractVulpeBeanFactory;

@Component("UserAuthentication")
public class UserAuthenticationImpl implements UserAuthentication {

	@Override
	public boolean isAuthenticated() {
		final Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		final boolean autenticated = authentication.isAuthenticated()
				&& !(authentication instanceof AnonymousAuthenticationToken);
		if (autenticated) {
			AfterUserAuthentication afterUserAuthentication = AbstractVulpeBeanFactory
					.getInstance().getBean(AfterUserAuthentication.class.getSimpleName());
			if (afterUserAuthentication != null) {
				afterUserAuthentication.load();
			}
		}
		return autenticated;
	}

}
