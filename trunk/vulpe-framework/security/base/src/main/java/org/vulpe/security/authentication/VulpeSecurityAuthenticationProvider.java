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
import org.vulpe.commons.util.VulpeDigestUtil;
import org.vulpe.security.authentication.data.VulpeAuthenticationResponse;
import org.vulpe.security.authentication.model.services.VulpeUserAuthenticationService;
import org.vulpe.security.commons.VulpeSecurityConstants;
import org.vulpe.security.exception.VulpeSecurityException;
import org.vulpe.security.model.entity.User;
import org.vulpe.security.model.entity.UserRole;

/**
 * This is a customized <code>AuthenticationProvider</code> used for dynamic
 * data based authentication. Here authentication is externalized and is
 * performed by a common authentication service.
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
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
	 * @seeorg.springframework.security.authentication.AuthenticationProvider#
	 * authenticate(org.springframework.security.core.Authentication)
	 */
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
		VulpeAuthenticationResponse authResponse = null;
		try {
			authResponse = authenticationService.authenticateUser(authentication.getPrincipal().toString(),
					VulpeDigestUtil.encrypt(authentication.getCredentials().toString(), "md5"));
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

			user = new org.springframework.security.core.userdetails.User(authentication.getPrincipal().toString(),
					authentication.getCredentials().toString(), true, true, true, true, list);
		} else {
			throw new BadCredentialsException("Bad Credentials", ((Object) (user)));
		}

		final UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(user, authentication
				.getCredentials(), user.getAuthorities());
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
