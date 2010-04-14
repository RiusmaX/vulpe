package org.vulpe.security.authentication.data;

import java.io.Serializable;

import org.vulpe.security.model.entity.User;


/**
 * This class contains the data returned as a result of authentication process.
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * @version 1.0
 * @since 1.0
 *
 */
@SuppressWarnings("serial")
public class AuthenticationResponse implements Serializable {

	/**
	 * authentication status
	 */
	private int authStatus;
	/**
	 * user profile information of the authenticated user.
	 */
	private User userDetails;

	/**
	 * Creates a AuthenticationResponse with <code>authStatus</code> and
	 * <code>userDetails</code> params.
	 *
	 * @param authStatus
	 * @param userDetails
	 *
	 */
	public AuthenticationResponse(final int authStatus, final User userDetails) {
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
