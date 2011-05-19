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
package org.vulpe.security.model.dao.impl.jpa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vulpe.commons.VulpeConstants.Security;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.dao.impl.jpa.VulpeBaseDAOJPA;
import org.vulpe.security.model.dao.UserDAO;
import org.vulpe.security.model.entity.User;

@Repository("UserDAO")
@Transactional
public class UserDAOJPA extends VulpeBaseDAOJPA<User, Long> implements UserDAO {

	@SuppressWarnings("unchecked")
	public List<User> getUsersByRole(final String roleName) throws VulpeApplicationException {
		final Map<String, Object> map = new HashMap();
		map.put("name", roleName.startsWith(Security.ROLE_PREFIX) ? roleName : Security.ROLE_PREFIX + roleName);
		return (List<User>) listByNamedQueryAndNamedParams("User.getUsersByRole", map);
	}
}