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
public @interface VulpeSelectPopup {

	String name() default "";

	boolean validate() default false;
	
	boolean argument() default false;

	String label() default "";

	String identifier();

	String description();
	
	String action();
	
	int size() default 40;
	
	int popupWidth() default 600;

}