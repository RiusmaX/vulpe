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

/**
 * Tabular view configurations.
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Tabular {

	/**
	 * Fields to despise items in tabular
	 */
	String[] despiseFields() default {};

	/**
	 * Quantity of new records in tabular
	 */
	int newRecords() default 0;

	/**
	 * Quantity of new records in tabular on start
	 */
	int startNewRecords() default 0;

	/**
	 * Name of list
	 */
	String name() default "";

	/**
	 * Name of attribute in the list
	 */
	String propertyName() default "";

	/**
	 * Page size of tabular
	 */
	int pageSize() default 0;

	/**
	 * Show tabular filter
	 */
	boolean showFilter() default false;

}
