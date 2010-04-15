package org.vulpe.audit.model.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 *
 */
@Target( {FIELD} )
@Retention(RUNTIME)
public @interface IgnoreAuditAttribute {
	
}
