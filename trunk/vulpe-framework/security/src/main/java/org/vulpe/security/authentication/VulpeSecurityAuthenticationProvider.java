package org.vulpe.security.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.vulpe.security.authentication.data.VulpeAuthenticationResponse;
import org.vulpe.security.authentication.model.services.VulpeUserAuthenticationService;
import org.vulpe.security.commons.VulpeDigestUtil;
import org.vulpe.security.commons.VulpeSecurityConstants;
import org.vulpe.security.exception.VulpeSecurityException;
import org.vulpe.security.model.entity.User;
import org.vulpe.security.model.entity.UserRole;

/**
 * This is a customized <code>AuthenticationProvider</code> used for dynamic
 * data based authentication. Here authentication is externalized and is
 * performed by a common authentication service.
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * @version 1.0
 * @since 1.0
 * @see {@link org.springframework.security.providers.AuthenticationProvider}
 *
 */
@SuppressWarnings("unchecked")
public class VulpeSecurityAuthenticationProvider implements AuthenticationProvider {

	@Qualifier("VulpeUserAuthenticationService")
	@Autowired
	private VulpeUserAuthenticationService authenticationService;

	/*
	 * (non-Javadoc)
	 *
	 * @seeorg.springframework.security.providers.AuthenticationProvider#
	 * authenticate(org. acegisecurity.Authentication)
	 */
	public Authentication authenticate(final Authentication authentication)
			throws AuthenticationException {
		VulpeAuthenticationResponse authResponse = null;
		try {
			authResponse = authenticationService.authenticateUser(authentication.getPrincipal()
					.toString(), VulpeDigestUtil.encrypt(
					authentication.getCredentials().toString(), "md5"));
		} catch (VulpeSecurityException e) {
			throw new BadCredentialsException("Exception occurred while executing service", e);
		}
		UserDetails user = null;
		if (authResponse.getAuthStatus() == VulpeSecurityConstants.AUTHENTICATION_SUCCESS) {
			final User userObj = authResponse.getUserDetails();
			final List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

			final List<UserRole> userRoles = userObj.getUserRoles();
			if (userRoles != null) {
				for (UserRole userRole : userRoles) {
					list.add(new GrantedAuthorityImpl(userRole.getRole().getName()));
				}
			}

			user = new org.springframework.security.core.userdetails.User(authentication
					.getPrincipal().toString(), authentication.getCredentials().toString(), true,
					true, true, true, list);
		} else {
			throw new BadCredentialsException("Bad Credentials", ((Object) (user)));
		}

		final UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(
				user, authentication.getCredentials(), user.getAuthorities());
		result.setDetails(authentication.getDetails());
		return result;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.springframework.security.providers.AuthenticationProvider#supports
	 * (java.lang .Class)
	 */
	public boolean supports(final Class authentication) {
		return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
	}

	/**
	 * Sets the authService
	 *
	 * @param authService
	 *            The authService to set.
	 */
	public void setAuthenticationService(final VulpeUserAuthenticationService authService) {
		this.authenticationService = authService;
	}

	public VulpeUserAuthenticationService getAuthenticationService() {
		return authenticationService;
	}
}
