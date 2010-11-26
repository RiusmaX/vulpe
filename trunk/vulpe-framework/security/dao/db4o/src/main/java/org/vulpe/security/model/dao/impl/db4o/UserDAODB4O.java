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
package org.vulpe.security.model.dao.impl.db4o;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.dao.impl.db4o.VulpeBaseDAODB4O;
import org.vulpe.security.model.dao.UserDAO;
import org.vulpe.security.model.entity.User;

@Repository("UserDAO")
@Transactional
public class UserDAODB4O extends VulpeBaseDAODB4O<User, Long> implements UserDAO {

	@Override
	public List<User> getUsersByRole(String roleName) throws VulpeApplicationException {
		return null;
	}

}