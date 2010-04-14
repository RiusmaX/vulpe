package org.vulpe.security.authentication.model.dao;

import org.vulpe.model.dao.VulpeBaseCRUDDAO;
import org.vulpe.security.authentication.data.AuthenticationResponse;
import org.vulpe.security.exception.VulpeSecurityException;
import org.vulpe.security.model.entity.User;


/**
 * This DAO is used for user authentication. Depending upon various data access
 * mechanisms (XML, database etc), it's implemented in different ways.
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * @version 1.0
 * @since 1.0
 *
 */
public interface UserAuthenticationDAO extends VulpeBaseCRUDDAO<User, Long> {

	/**
	 * Performs authentication based on passed <code>userid</code> and
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
	 *
	 */
	AuthenticationResponse authenticateUser(final String userId, final String password)
			throws VulpeSecurityException;

}