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
package org.vulpe.security.model.services.manager;

import org.springframework.stereotype.Service;
import org.vulpe.model.services.manager.VulpeBaseCRUDManagerImpl;
import org.vulpe.security.model.dao.RoleDAO;
import org.vulpe.security.model.entity.Role;

@Service
public class RoleManager extends VulpeBaseCRUDManagerImpl<Role, Long, RoleDAO> {

}