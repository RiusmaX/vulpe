package org.vulpe.model.services;

import java.util.List;

import org.vulpe.model.entity.VulpeBaseEntity;


/**
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 */
public interface GenericServices extends Services {

	/**
	 *
	 * @param <T>
	 * @param entity
	 * @return
	 */
	<T extends VulpeBaseEntity<?>> List<T> getList(T entity);
}
