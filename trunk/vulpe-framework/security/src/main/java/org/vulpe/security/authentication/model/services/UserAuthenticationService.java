package org.vulpe.security.authentication.model.services;

import org.vulpe.security.authentication.data.AuthenticationResponse;
import org.vulpe.security.exception.VulpeSecurityException;

/**
 * A business service to authenticate the user.
 * 
 * <DL>
 * <DT><B>History: </B>
 * <DD>Sep 26, 2007</DD>
 * </DL>
 * 
 * @author ShriKant
 * @version 1.0, Sep 26, 2007
 * 
 * @since v1.0, Sep 26, 2007
 * 
 */
public interface UserAuthenticationService {
	/**
	 * Performs authentication based on passed <code>userId</code> and
	 * <code>password</code> information.
	 * 
	 * @param userId
	 *            the user id
	 * @param password
	 *            password for the user
	 * @return <code>AuthenticationResponse</code> a response containing
	 *         authentication status and user profile information.
	 * @throws VulpeSecurityException
	 * 
	 */
	AuthenticationResponse authenticateUser(String userId, String password)
			throws VulpeSecurityException;
}
