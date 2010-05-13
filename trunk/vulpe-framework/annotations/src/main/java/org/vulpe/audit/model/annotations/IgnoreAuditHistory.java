package org.vulpe.audit.model.annotations;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * Disable audit history of entity
 * 
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 */
@Target( { TYPE })
@Retention(RUNTIME)
public @interface IgnoreAuditHistory {

}
