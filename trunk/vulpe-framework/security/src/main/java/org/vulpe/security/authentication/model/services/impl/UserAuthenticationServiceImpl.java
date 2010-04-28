package org.vulpe.security.authentication.model.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.vulpe.security.authentication.data.AuthenticationResponse;
import org.vulpe.security.authentication.model.dao.UserAuthenticationDAO;
import org.vulpe.security.authentication.model.services.UserAuthenticationService;
import org.vulpe.security.exception.VulpeSecurityException;

/**
 * It uses <code>UserAuthenticationDAO</code> for authentication.
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * @version 1.0
 * @since 1.0
 *
 */
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

	@Qualifier("UserAuthenticationDAO")
	@Autowired
	private transient UserAuthenticationDAO authenticationDAO;

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.vulpe.security.authentication.model.services.
	 * UserAuthenticationService#authenticateUser(java.lang.String,
	 * java.lang.String)
	 */
	public AuthenticationResponse authenticateUser(final String userId,
			final String password) throws VulpeSecurityException {
		return authenticationDAO.authenticateUser(userId, password);
	}

	public void setAuthenticationDAO(final UserAuthenticationDAO authenticationDAO) {
		this.authenticationDAO = authenticationDAO;
	}

}