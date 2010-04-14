package org.vulpe.view.annotations.output;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to represent text input on view.
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface VulpeColumn {

	String width() default "";

	String attribute() default "";

	String booleanTo() default "";

	boolean sortable() default false;

	String label() default "";

	String align() default "left";

}