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
package org.vulpe.security.authentication.model.services.impl.pojo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.vulpe.security.authentication.data.VulpeAuthenticationResponse;
import org.vulpe.security.authentication.model.dao.VulpeUserAuthenticationDAO;
import org.vulpe.security.authentication.model.services.VulpeUserAuthenticationService;
import org.vulpe.security.exception.VulpeSecurityException;

/**
 * It uses <code>VulpeUserAuthenticationDAO</code> for authentication.
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * @version 1.0
 * @since 1.0
 *
 */
@Service("VulpeUserAuthenticationService")
public class VulpeUserAuthenticationServicePOJO implements VulpeUserAuthenticationService {

	@Qualifier("VulpeUserAuthenticationDAO")
	@Autowired
	private transient VulpeUserAuthenticationDAO authenticationDAO;

	/*
	 * (non-Javadoc)
	 *
	 * @see org.vulpe.security.authentication.model.services.
	 * UserAuthenticationCallback#authenticateUser(java.lang.String,
	 * java.lang.String)
	 */
	public VulpeAuthenticationResponse authenticateUser(final String userId, final String password)
			throws VulpeSecurityException {
		return authenticationDAO.authenticateUser(userId, password);
	}

	public void setAuthenticationDAO(final VulpeUserAuthenticationDAO authenticationDAO) {
		this.authenticationDAO = authenticationDAO;
	}

}