package org.vulpe.model.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;
import org.vulpe.view.annotations.View;
import org.vulpe.view.annotations.View.ViewType;


/**
 * Annotation to indicate code creation of from entity.
 * 
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CodeGenerator {

	Controller[] controller() default @Controller(controllerType = ControllerType.NONE);

	View view() default @View(viewType = { ViewType.NONE });
	
	boolean manager() default false;

	boolean dao() default false;
}