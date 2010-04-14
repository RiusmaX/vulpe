package org.vulpe.model.dao;

/**
 * Basic interface of DAO.
 *
 * @author <a href="mailto:fabio.viana@activethread.com.br">F�bio Viana</a>
 */
public interface VulpeBaseDAO {

	/**
	 * Make ENTITY merge.
	 *
	 * @param entity
	 * @return ENTITY
	 */
	<T> T merge(final T entity);

}