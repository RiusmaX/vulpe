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
package org.vulpe.security.authentication.data;

import java.io.Serializable;

import org.vulpe.security.model.entity.User;

/**
 * This class contains the data returned as a result of authentication process.
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 * 
 */
@SuppressWarnings("serial")
public class VulpeAuthenticationResponse implements Serializable {

	/**
	 * authentication status
	 */
	private int authStatus;
	/**
	 * user profile information of the authenticated user.
	 */
	private User userDetails;

	/**
	 * Creates a VulpeAuthenticationResponse with <code>authStatus</code> and
	 * <code>userDetails</code> params.
	 * 
	 * @param authStatus
	 * @param userDetails
	 * 
	 */
	public VulpeAuthenticationResponse(final int authStatus, final User userDetails) {
		this.authStatus = authStatus;
		this.userDetails = userDetails;
	}

	/**
	 * Returns the authStatus
	 * 
	 * @return int
	 */
	public int getAuthStatus() {
		return authStatus;
	}

	/**
	 * Sets the authStatus
	 * 
	 * @param authStatus
	 *            The authStatus to set.
	 */
	public void setAuthStatus(final int authStatus) {
		this.authStatus = authStatus;
	}

	/**
	 * Returns the userDetails
	 * 
	 * @return User
	 */
	public User getUserDetails() {
		return userDetails;
	}

	/**
	 * Sets the userDetails
	 * 
	 * @param userDetails
	 *            The userDetails to set.
	 */
	public void setUserDetails(final User userDetails) {
		this.userDetails = userDetails;
	}
}
