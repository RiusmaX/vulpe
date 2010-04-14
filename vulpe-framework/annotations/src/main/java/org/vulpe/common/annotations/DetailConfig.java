package org.vulpe.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Detail configuration
 * 
 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
public @interface DetailConfig {
	/**
	 * Quantity of news details
	 */
	int detailNews() default 1;

	/**
	 * Attributes to despise details
	 */
	String[] despiseFields();

	/**
	 * View Definition interface
	 */
	String view() default "";

	/**
	 * Detail name
	 */
	String name();

	/**
	 * Detail atribute name
	 */
	String propertyName() default "";

	/**
	 * Parent Detail Name
	 */
	String parentDetailName() default "";
}