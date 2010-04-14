package org.vulpe.audit.model.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author Andrei
 *
 */
@Target( {FIELD} )
@Retention(RUNTIME)
public @interface IgnoreAuditAttribute {
	
}
