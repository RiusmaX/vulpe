package org.vulpe.common.factory;

/**
 * Interface padr�o de Factory
 * 
 * @author <a href="mailto:fabio.viana@activethread.com.br">F�bio Viana</a>
 * @param <T>
 *            Tipo fabricado
 */
public interface Factory<T> {
	/**
	 * Retorna a instancia de T
	 */
	T instance();
}