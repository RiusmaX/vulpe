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
