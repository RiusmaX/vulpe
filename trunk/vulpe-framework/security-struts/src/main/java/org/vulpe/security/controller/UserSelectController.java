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

import org.springframework.stereotype.Component;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;
import org.vulpe.controller.struts.VulpeStrutsController;
import org.vulpe.security.model.entity.User;
import org.vulpe.security.model.services.SecurityServices;

@Component("security.UserSelect")
@Controller(controllerType = ControllerType.SELECT, serviceClass = SecurityServices.class, pageSize = 5)
@SuppressWarnings("serial")
public class UserSelectController extends VulpeStrutsController<User, Long> {

}
