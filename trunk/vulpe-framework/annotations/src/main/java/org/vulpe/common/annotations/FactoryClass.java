package org.vulpe.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.vulpe.common.factory.Factory;


/**
 * Anotação que registra o factory de uma interface.
 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface FactoryClass {
	@SuppressWarnings("unchecked")
	Class<? extends Factory> value();
}