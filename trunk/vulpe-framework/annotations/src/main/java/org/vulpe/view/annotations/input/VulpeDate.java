package org.vulpe.view.annotations.input;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to represent date input on view.
 * 
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface VulpeDate {

	String name() default "";

	int size() default 0;

	int maxlength() default 0;

	boolean required() default false;

	boolean argument() default false;

	String label() default "";
}