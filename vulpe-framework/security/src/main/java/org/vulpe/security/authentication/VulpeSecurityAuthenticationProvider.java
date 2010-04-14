package org.vulpe.security.authentication;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.providers.AuthenticationProvider;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;
import org.springframework.security.userdetails.UserDetails;
import org.vulpe.security.authentication.data.AuthenticationResponse;
import org.vulpe.security.authentication.model.services.UserAuthenticationService;
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
public class VulpeSecurityAuthenticationProvider implements
		AuthenticationProvider {

	private UserAuthenticationService authenticationService;

	/*
	 * (non-Javadoc)
	 *
	 * @seeorg.springframework.security.providers.AuthenticationProvider#
	 * authenticate(org. acegisecurity.Authentication)
	 */
	public Authentication authenticate(final Authentication authentication)
			throws AuthenticationException {
		AuthenticationResponse authResponse = null;
		try {
			authResponse = authenticationService.authenticateUser(
					authentication.getPrincipal().toString(), authentication
							.getCredentials().toString());
		} catch (VulpeSecurityException e) {
			throw new BadCredentialsException(
					"Exception occurred while executing service", e);
		}
		UserDetails user = null;
		if (authResponse.getAuthStatus() == SecurityConstants.AUTHENTICATION_SUCCESS) {
			final User userObj = authResponse.getUserDetails();
			final List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();

			final List<UserRole> userRoles = userObj.getUserRoles();
			if (userRoles != null) {
				for (UserRole userRole : userRoles) {
					list.add(new GrantedAuthorityImpl(userRole.getRole()
							.getName()));
				}
			}

			final GrantedAuthority toReturn[] = { new GrantedAuthorityImpl(
					"demo") };
			user = new org.springframework.security.userdetails.User(
					authentication.getPrincipal().toString(), authentication
							.getCredentials().toString(), true, true, true,
					true, list.isEmpty() ? toReturn
							: (GrantedAuthority[]) (GrantedAuthority[]) list
									.toArray(toReturn));
		} else {
			throw new BadCredentialsException("Bad Credentials",
					((Object) (user)));
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
	@SuppressWarnings("unchecked")
	public boolean supports(final Class authentication) {
		return UsernamePasswordAuthenticationToken.class
				.isAssignableFrom(authentication);
	}

	/**
	 * Sets the authService
	 *
	 * @param authService
	 *            The authService to set.
	 */
	public void setAuthenticationService(
			final UserAuthenticationService authService) {
		this.authenticationService = authService;
	}

	public UserAuthenticationService getAuthenticationService() {
		return authenticationService;
	}
}
