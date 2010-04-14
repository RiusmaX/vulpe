package org.vulpe.model.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.FIELD, ElementType.METHOD })
public @interface Param {

	/**
	 * Operator.
	 *
	 * @return
	 */
	OperatorType operator() default OperatorType.EQUAL;

	/**
	 * Attribute name.
	 *
	 * @return
	 */
	String name() default "";

	/**
	 * Only use with JPA.
	 * @return
	 */
	String alias() default "obj";

	public enum OperatorType {

		EQUAL("="),
		GREATER(">"),
		SMALLER("<"),
		GREATER_OR_EQUAL(">="),
		SMALLER_OR_EQUAL("<=");

		private String value;

		private OperatorType(final String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

}