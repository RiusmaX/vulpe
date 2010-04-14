package org.vulpe.view.annotations.input;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to represent select input on view.
 * 
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface VulpeSelect {

	String name() default "";

	String items() default "";
	
	String itemKey() default "";
	
	String itemLabel() default "";
	
	boolean autoLoad() default false;
	
	boolean showBlank() default true;
	
	boolean validate() default false;
	
	boolean argument() default false;

	String label() default "";
}