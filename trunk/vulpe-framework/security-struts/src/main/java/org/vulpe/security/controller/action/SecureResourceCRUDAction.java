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
package org.vulpe.security.controller.action;

import org.vulpe.common.annotations.DetailConfig;
import org.vulpe.common.annotations.DetailConfig.CardinalityType;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;
import org.vulpe.controller.struts.action.VulpeBaseAction;
import org.vulpe.security.model.entity.SecureResource;
import org.vulpe.security.model.services.SecurityServices;

@Controller(controllerType = ControllerType.CRUD, serviceClass = SecurityServices.class, detailsConfig = { @DetailConfig(name = "secureResourceRoles", propertyName = "entity.secureResourceRoles", despiseFields = "role", detailNews = 1, cardinalityType = CardinalityType.ONE_OR_MORE) })
@SuppressWarnings("serial")
public class SecureResourceCRUDAction extends VulpeBaseAction<SecureResource, Long> {

}
