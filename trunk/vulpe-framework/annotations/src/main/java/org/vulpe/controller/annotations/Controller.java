package org.vulpe.controller.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.vulpe.common.annotations.DetailConfig;
import org.vulpe.model.services.Services;


/**
 * Control configurations.
 *
 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Controller {
	// Basic configuration
	/**
	 * Type of logic
	 */
	ControllerType controllerType();

	/**
	 * Service interface
	 */
	Class<? extends Services> serviceClass() default Services.class;

	// SELECT - configurations
	/**
	 * Page size of selection
	 */
	int pageSize() default 0;

	boolean showReport() default false;

	// CRUD - configurations
	/**
	 * Details configuration
	 */
	DetailConfig[] detailsConfig() default {};

	/**
	 * Configure details in tab-folders
	 */
	boolean detailsInTabs() default true;

	// SELECT and CRUD - control configurations
	/**
	 * Action to redirect after selection
	 */
	String ownerAction() default "";

	// TABULAR - configurations
	/**
	 * Fields to despise items in tabular
	 */
	String[] tabularDespiseFields() default {};

	/**
	 * Quantity of new records in tabular
	 */
	int tabularDetailNews() default 1;

	/**
	 * Name of list
	 */
	String tabularName() default "";

	/**
	 * Name of attribute in the list
	 */
	String tabularPropertyName() default "";

	// REPORT - configurations
	Report report() default @Report;

	/**
	 * Controllers type
	 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
	 */
	public enum ControllerType {
		CRUD, TABULAR, SELECT, REPORT, FRONTEND, OTHER, NONE
	}
}
