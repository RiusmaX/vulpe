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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.model.services.Services;

/**
 * Control configurations.
 * 
 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Controller {
	// Basic configuration
	/**
	 * Type of logic
	 */
	ControllerType controllerType();

	/**
	 * Service interface
	 */
	Class<? extends Services> serviceClass() default Services.class;

	// SELECT - configurations
	/**
	 * Page size of selection
	 */
	int pageSize() default 0;

	boolean showReport() default false;

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
	 * Action to redirect after selection
	 */
	String ownerController() default "";

	// TABULAR - configurations
	/**
	 * Fields to despise items in tabular
	 */
	String[] tabularDespiseFields() default {};

	/**
	 * Quantity of new records in tabular
	 */
	int tabularDetailNews() default 1;

	/**
	 * Name of list
	 */
	String tabularName() default "";

	/**
	 * Name of attribute in the list
	 */
	String tabularPropertyName() default "";

	// REPORT - configurations
	Report report() default @Report;

	/**
	 * Controllers type
	 * 
	 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
	 */
	public enum ControllerType {
		CRUD, TABULAR, SELECT, REPORT, BACKEND, FRONTEND, OTHER, NONE
	}
}
