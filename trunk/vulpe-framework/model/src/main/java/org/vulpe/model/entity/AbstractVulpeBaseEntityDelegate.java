package org.vulpe.model.entity;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.vulpe.exception.VulpeSystemException;

/**
 * Classe padrão de entidadeDelegate utilizada para transmitir dados em
 * WebServices-Client
 *
 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
 */
@SuppressWarnings( { "unchecked", "serial" })
public abstract class AbstractVulpeBaseEntityDelegate<T extends VulpeBaseEntity<ID>, ID extends Serializable & Comparable>
		implements Serializable {

	private T bean;

	/**
	 * Retorna a entidade trafegada
	 */
	public T getBean() {
		if (this.bean == null
				&& getClass().getGenericSuperclass() instanceof ParameterizedType) {
			final ParameterizedType type = (ParameterizedType) getClass()
					.getGenericSuperclass();
			if (type.getActualTypeArguments() != null
					&& type.getActualTypeArguments().length > 0
					&& type.getActualTypeArguments()[0] instanceof Class) {
				try {
					this.bean = (T) ((Class) type.getActualTypeArguments()[0])
							.newInstance();
				} catch (Exception e) {
					throw new VulpeSystemException(e);
				}
			}
		}
		return this.bean;
	}

	public AbstractVulpeBaseEntityDelegate() {
		// default constructor
	}

	public AbstractVulpeBaseEntityDelegate(final T baseEntity) {
		this.bean = baseEntity;
	}
}