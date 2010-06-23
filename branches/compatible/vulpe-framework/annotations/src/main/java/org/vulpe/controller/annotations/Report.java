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
 * Report configurations.
 * 
 * @author <a href="mailto:felipe.matos@activethread.com.br">Matos</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Report {

	/**
	 * Template file of report
	 */
	String reportFile() default "";

	/**
	 * Format of report
	 */
	String reportFormat() default "PDF";

	/**
	 * Format to export report
	 */
	String reportDataSource() default "entities";

	/**
	 * Name of report
	 */
	String reportName() default "";

	/**
	 * Download report
	 */
	boolean reportDownload() default false;

	String[] subReports() default {};

}
