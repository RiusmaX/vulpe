package org.vulpe.model.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anota��o utilizada para informar que a entidade � fraca
 * @author <a href="mailto:fabio.viana@activethread.com.br">F�bio Viana</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface WeakEntity {

}