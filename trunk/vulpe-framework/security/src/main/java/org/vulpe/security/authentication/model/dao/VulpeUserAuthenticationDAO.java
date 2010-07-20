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
package org.vulpe.security.authentication.model.dao;

import org.vulpe.model.dao.VulpeDAO;
import org.vulpe.security.authentication.data.VulpeAuthenticationResponse;
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
public interface VulpeUserAuthenticationDAO extends VulpeDAO<User, Long> {

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
	VulpeAuthenticationResponse authenticateUser(final String userId, final String password)
			throws VulpeSecurityException;

}