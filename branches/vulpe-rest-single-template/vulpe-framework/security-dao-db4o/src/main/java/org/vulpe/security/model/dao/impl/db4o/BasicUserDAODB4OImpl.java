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

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.vulpe.model.dao.impl.db4o.VulpeBaseCRUDDAODB4OImpl;
import org.vulpe.security.model.dao.BasicUserDAO;
import org.vulpe.security.model.entity.BasicUser;

@Repository("BasicUserDAO")
@Transactional
public class BasicUserDAODB4OImpl<ENTITY_CLASS extends BasicUser> extends
		VulpeBaseCRUDDAODB4OImpl<ENTITY_CLASS, Long> implements BasicUserDAO<ENTITY_CLASS> {

}