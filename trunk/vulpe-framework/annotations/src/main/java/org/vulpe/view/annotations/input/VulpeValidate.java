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
package org.vulpe.view.annotations.input;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation represent input validation on view.
 *
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.ANNOTATION_TYPE, ElementType.FIELD })
public @interface VulpeValidate {

	/**
	 * Type of validation on input
	 */
	VulpeValidateType type() default VulpeValidateType.NONE;

	/**
	 * Scope of validation on input
	 */
	VulpeValidateScope[] scope() default { VulpeValidateScope.ALL };

	/**
	 * Scope of required validation on input
	 */
	VulpeValidateScope[] requiredScope() default { VulpeValidateScope.ALL };

	/**
	 * Minimum value to input
	 */
	int min() default 0;

	/**
	 * Maximum value to input
	 */
	int max() default 0;

	/**
	 * Minimum length to input
	 */
	int minlength() default 0;

	/**
	 * Maximum length to input
	 */
	int maxlength() default 0;

	/**
	 * Range to numbers
	 */
	int[] range() default { 0, 0 };

	/**
	 * Mask to input.<br>
	 * Example: 99/99/9999
	 */
	String mask() default "";

	/**
	 * Date pattern to input.<br>
	 * Example: dd/MM/yyyy
	 */
	String datePattern() default "dd/MM/yyyy";

	/**
	 * Types for input validation
	 */
	enum VulpeValidateType {
		ARRAY, DATE, DOUBLE, EMAIL, FLOAT, INTEGER, LONG, NONE, STRING
	}

	/**
	 * Scope for input validation
	 */
	enum VulpeValidateScope {
		ALL, CRUD, DETAIL, SELECT, TABULAR
	}
}