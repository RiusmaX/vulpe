package org.vulpe.common.factory;

/**
 * Interface padrão de Factory
 * 
 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
 * @param <T>
 *            Tipo fabricado
 */
public interface Factory<T> {
	/**
	 * Retorna a instancia de T
	 */
	T instance();
}