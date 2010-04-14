package org.vulpe.audit.model.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author Silverio
 *
 */
@Target( {TYPE} )
@Retention(RUNTIME)
public @interface IgnoreAuditEntity {
	
}
