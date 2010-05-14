package org.vulpe.view.annotations.input;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation represent input validation on view.
 * 
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.ANNOTATION_TYPE, ElementType.FIELD })
public @interface VulpeValidate {

	/**
	 * Type of validation on input
	 */
	ValidateType type();

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
	enum ValidateType {
		ARRAY, DATE, DOUBLE, EMAIL, FLOAT, INTEGER, LONG, NONE, STRING
	}

}