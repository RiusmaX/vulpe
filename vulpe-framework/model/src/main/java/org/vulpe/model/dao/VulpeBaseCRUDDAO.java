package org.vulpe.model.dao;

import java.io.Serializable;
import java.util.List;

import org.vulpe.common.beans.Paging;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.entity.VulpeBaseEntity;


/**
 * Default Interface of DAO for CRUD's
 *
 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
 */
@SuppressWarnings("unchecked")
public interface VulpeBaseCRUDDAO<ENTITY extends VulpeBaseEntity<ID>, ID extends Serializable & Comparable>
		extends VulpeBaseDAO {
	/**
	 * Creates ENTITY
	 *
	 * @param entity
	 * @return ENTITY
	 */
	ENTITY create(ENTITY entity) throws VulpeApplicationException;

	/**
	 * Returns list of ENTITY
	 *
	 * @param entity
	 * @return List of ENTITY filter by parameters of ENTITY
	 */
	List<ENTITY> read(ENTITY entity) throws VulpeApplicationException;

	/**
	 * Returns list of ENTITY with paging.
	 *
	 * @param entity
	 * @param pageSize
	 *            Page size
	 * @param page
	 *            Current page
	 * @return
	 * @throws VulpeApplicationException
	 */
	Paging<ENTITY> paging(ENTITY entity, Integer pageSize, Integer page)
			throws VulpeApplicationException;

	/**
	 * Updates ENTITY.
	 *
	 * @param entity
	 */
	void update(ENTITY entity) throws VulpeApplicationException;

	/**
	 * Remove ENTITY.
	 *
	 * @param entity
	 */
	void delete(ENTITY entity) throws VulpeApplicationException;

	/**
	 * Remove list of ENTITY.
	 *
	 * @param entity
	 */
	void delete(List<ENTITY> entities) throws VulpeApplicationException;

	/**
	 * Returns ENTITY by id.
	 *
	 * @param id
	 * @return ENTITY
	 */
	ENTITY find(ID id) throws VulpeApplicationException;
}