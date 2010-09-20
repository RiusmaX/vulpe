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
package org.vulpe.security.context;

import java.io.Serializable;
import java.util.List;

import org.vulpe.model.entity.VulpeEntity;

/**
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 */
public interface VulpeSecurityContext extends Serializable {

	String USER_AUTHENTICATION = "userAuthentication";

	/**
	 * Initialize context of security creating roles, secure resources and users
	 * if necessary.
	 */
	void initialize();

	boolean isAuthenticated();

	void afterUserAuthenticationCallback();

	<T extends VulpeEntity<Long>> T getUser();

	String getUsername();

	List<String> getUserRoles();

}
