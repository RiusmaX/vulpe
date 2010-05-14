package org.vulpe.view.annotations.input;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to represent checkboxlist inputs on view.
 * 
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface VulpeCheckboxlist {

	String name() default "";

	String list() default "";

	String listKey() default "";

	String listValue() default "";

	String enumeration() default "";

	boolean required() default false;

	boolean argument() default false;

	String label() default "";

}