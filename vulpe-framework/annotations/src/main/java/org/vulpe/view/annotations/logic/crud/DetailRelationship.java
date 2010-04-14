package org.vulpe.view.annotations.logic.crud;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to represent detail relationship.
 * 
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DetailRelationship {

	String name();

	String identifier();

	String description();

	String action();

	int size() default 40;

	int popupWidth() default 600;

}