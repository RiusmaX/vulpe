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
package org.vulpe.security.controller;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.vulpe.commons.annotations.Quantity;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.commons.annotations.Quantity.QuantityType;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Select;
import org.vulpe.controller.struts.VulpeStrutsController;
import org.vulpe.security.model.entity.SecureResource;
import org.vulpe.security.model.services.SecurityService;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Component("security.SecureResourceController")
@Controller(serviceClass = SecurityService.class, detailsConfig = { @DetailConfig(name = "secureResourceRoles", propertyName = "entity.secureResourceRoles", despiseFields = "role", startNewDetails = 1, newDetails = 1, quantity = @Quantity(type = QuantityType.ONE_OR_MORE)) }, select = @Select(pageSize = 5))
@SuppressWarnings("serial")
public class SecureResourceController extends VulpeStrutsController<SecureResource, Long> {

}
