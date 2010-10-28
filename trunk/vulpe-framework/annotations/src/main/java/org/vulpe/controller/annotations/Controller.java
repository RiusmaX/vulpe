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
package org.vulpe.controller.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.model.services.VulpeService;

/**
 * Controller configurations.
 *
 * @author <a href="mailto:fabio.viana@vulpe.org">Fábio Viana</a>
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Controller {
	// Basic configuration
	/**
	 * Type of logic
	 */
	ControllerType controllerType() default ControllerType.ALL;

	/**
	 * Base name to mount view (JSPs)
	 */
	String viewBaseName() default "";

	/**
	 * Service interface
	 */
	Class<? extends VulpeService> serviceClass() default VulpeService.class;

	// CRUD - configurations
	/**
	 * Details configuration
	 */
	DetailConfig[] detailsConfig() default {};

	/**
	 * Configure details in tab-folders
	 */
	boolean detailsInTabs() default true;

	// SELECT and CRUD - control configurations
	/**
	 * Controller to redirect after selection
	 */
	String ownerController() default "";

	// SELECT - configurations
	/**
	 * Select Logic configurations
	 */
	Select select() default @Select;

	// TABULAR - configurations
	/**
	 * Tabular Logic configurations
	 */
	Tabular tabular() default @Tabular;

	// REPORT - configurations
	/**
	 * Report Logic configurations
	 */
	Report report() default @Report;

}
