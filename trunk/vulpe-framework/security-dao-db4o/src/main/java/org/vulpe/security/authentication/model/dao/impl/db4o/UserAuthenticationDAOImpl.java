package org.vulpe.security.authentication.model.dao.impl.db4o;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vulpe.model.dao.impl.db4o.VulpeBaseCRUDDAODB4OImpl;
import org.vulpe.security.authentication.SecurityConstants;
import org.vulpe.security.authentication.data.AuthenticationResponse;
import org.vulpe.security.authentication.model.dao.UserAuthenticationDAO;
import org.vulpe.security.exception.VulpeSecurityException;
import org.vulpe.security.exception.VulpeSecurityInactiveUserException;
import org.vulpe.security.exception.VulpeSecurityInvalidPasswordException;
import org.vulpe.security.exception.VulpeSecurityUserNotFoundException;
import org.vulpe.security.model.entity.User;

/**
 *
 * This <code>UserAuthenticationDAO</code> implementation uses data available in
 * memory to authenticate the user.
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * @version 1.0
 * @since 1.0
 *
 */
@Repository("UserAuthenticationDAO")
@Transactional
public class UserAuthenticationDAOImpl extends VulpeBaseCRUDDAODB4OImpl<User, Long>
		implements UserAuthenticationDAO {

	/*
	 * (non-Javadoc)
	 *
	 * @seeorg.vulpe.security.authentication.model.dao.UserAuthenticationDAO#
	 * authenticateUser(java.lang.String, java.lang.String)
	 */
	public AuthenticationResponse authenticateUser(final String username,
			final String password) throws VulpeSecurityException {
		User user = new User();
		user.setUsername(username);
		user = getObject(user);
		if (user == null) {
			throw new VulpeSecurityUserNotFoundException(username);
		}
		if (!password.equals(user.getPassword())) {
			throw new VulpeSecurityInvalidPasswordException();
		}
		if (!user.isActive()) {
			throw new VulpeSecurityInactiveUserException(username);
		}
		return new AuthenticationResponse(
				SecurityConstants.AUTHENTICATION_SUCCESS, user);
	}

}
