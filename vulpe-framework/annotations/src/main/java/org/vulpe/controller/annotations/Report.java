package org.vulpe.controller.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Report configurations.
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Matos</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Report {

	/**
	 * Template file of report
	 */
	String reportFile() default "";

	/**
	 * Format of report
	 */
	String reportFormat() default "PDF";

	/**
	 * Format to export report
	 */
	String reportDataSource() default "entities";

	/**
	 * Name of report
	 */
	String reportName() default "";

	/**
	 * Download report
	 */
	boolean reportDownload() default false;

	String[] subReports() default {};

}
