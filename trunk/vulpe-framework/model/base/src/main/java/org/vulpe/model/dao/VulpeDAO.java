/**
 * Vulpe Framework - Quick and Smart ;)
 * Copyright (C) 2011 Active Thread
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos da Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; tanto a versão 2 da
 * Licença como (a seu critério) qualquer versão mais nova.
 *
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 *
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para a Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.
 */
/**
 * Vulpe Framework - Quick and Smart ;)
 * Copyright (C) 2011 Active Thread
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.vulpe.model.dao;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.util.List;

import org.vulpe.commons.beans.Paging;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.model.entity.Parameter;
import org.vulpe.model.entity.VulpeEntity;

/**
 * Default Interface of DAO for MAIN's
 *
 * @author <a href="mailto:fabio.viana@vulpe.org">Fábio Viana</a>
 */
@SuppressWarnings("rawtypes")
public interface VulpeDAO<ENTITY extends VulpeEntity<ID>, ID extends Serializable & Comparable> {

	/**
	 * Make ENTITY merge.
	 *
	 * @param entity
	 * @return ENTITY
	 */
	<T> T merge(T entity);

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
	Paging<ENTITY> paging(ENTITY entity, Integer pageSize, Integer page) throws VulpeApplicationException;

	/**
	 * Updates ENTITY.
	 *
	 * @param entity
	 * @return
	 * @throws VulpeApplicationException
	 */
	ENTITY update(ENTITY entity) throws VulpeApplicationException;

	/**
	 * Remove ENTITY.
	 *
	 * @param entity
	 * @throws VulpeApplicationException
	 */
	void delete(ENTITY entity) throws VulpeApplicationException;

	/**
	 * Remove list of ENTITY.
	 *
	 * @param entity
	 * @throws VulpeApplicationException
	 */
	void delete(List<ENTITY> entities) throws VulpeApplicationException;

	/**
	 * Returns ENTITY by id.
	 *
	 * @param id
	 * @return ENTITY
	 * @throws VulpeApplicationException
	 */
	ENTITY find(ENTITY entity) throws VulpeApplicationException;

	/**
	 * Execute procedure.
	 *
	 * @param name
	 *            Full name of procedure
	 * @param parameters
	 *            List of parameters
	 * @return
	 * @throws VulpeApplicationException
	 */
	CallableStatement executeProcedure(final String name, List<Parameter> parameters) throws VulpeApplicationException;

	/**
	 * Execute function.
	 *
	 * @param name
	 *            Full name of function
	 * @param returnType
	 *            Return Type
	 * @param parameters
	 *            List of parameters
	 * @return
	 * @throws VulpeApplicationException
	 */
	CallableStatement executeFunction(final String name, final int returType, List<Parameter> parameters)
			throws VulpeApplicationException;

	/**
	 * Execute Callable Statement (Procedure or Function).
	 *
	 * @param name
	 *            Full name of procedure or function
	 * @param returnType
	 *            Return Type of function, if case
	 * @param parameters
	 *            List of parameters
	 * @return
	 * @throws VulpeApplicationException
	 */
	CallableStatement executeCallableStatement(final String name, final Integer returnType,
			final List<Parameter> parameters) throws VulpeApplicationException;

	/**
	 *
	 * @param entity
	 * @return
	 * @throws VulpeApplicationException
	 */
	boolean exists(final ENTITY entity) throws VulpeApplicationException;

	/**
	 *
	 * @param entity
	 * @return
	 * @throws VulpeApplicationException
	 */
	boolean notExistEquals(final ENTITY entity) throws VulpeApplicationException;

	/**
	 *
	 * @param entity
	 */
	void updateSomeAttributes(final ENTITY entity);

	/**
	 *
	 * @param entity
	 * @param ids
	 */
	void updateSomeAttributes(final ENTITY entity, final List<ID> ids);

	/**
	 *
	 * @param list
	 */
	void updateSomeAttributes(final List<ENTITY> list);
}