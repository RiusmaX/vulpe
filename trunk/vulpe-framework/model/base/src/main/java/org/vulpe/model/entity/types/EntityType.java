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
package org.vulpe.model.entity.types;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;
import org.vulpe.commons.VulpeServiceLocator;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.controller.VulpeController.Operation;
import org.vulpe.exception.VulpeSystemException;
import org.vulpe.model.entity.VulpeEntity;
import org.vulpe.model.services.VulpeService;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class EntityType implements UserType, ParameterizedType {
	/**
	 * Returned Class
	 */
	private transient Class<? extends VulpeEntity<?>> returnedClass = null;
	/**
	 * Service class
	 */
	private transient Class<? extends VulpeService> serviceClass = null;
	/**
	 * ID of class
	 */
	private transient Class<? extends Serializable> idClass = null;
	/**
	 * Type of column
	 */
	private transient int type = Types.NUMERIC;

	/**
	 * SQL Type of column
	 */
	public int[] sqlTypes() {
		return new int[] { type };
	}

	public Class returnedClass() {
		return returnedClass;
	}

	public boolean equals(final Object obj0, final Object obj1) throws HibernateException {
		final VulpeEntity<?> entity1 = (VulpeEntity<?>) obj0;
		final VulpeEntity<?> entity2 = (VulpeEntity<?>) obj1;
		if (entity1 == null) {
			return entity2 == null;
		}
		if (entity2 == null) {
			return entity1 == null;
		}
		return entity1.equals(entity2);
	}

	public Object nullSafeGet(final ResultSet resultSet, final String[] names, final Object owner)
			throws HibernateException, SQLException {
		try {
			final String value = resultSet.getString(names[0]);
			if (value == null) {
				return null;
			}

			final Object identifier = ConvertUtils.convert(value, this.idClass);
			return invokeFind(identifier);
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
	}

	public void nullSafeSet(final PreparedStatement pstm, final Object value, final int index)
			throws HibernateException, SQLException {
		final VulpeEntity<?> entity = (VulpeEntity<?>) value;
		if (entity == null || entity.getId() == null) {
			pstm.setNull(index, type);
		} else {
			pstm.setObject(index, entity.getId());
		}
	}

	public Object deepCopy(final Object value) throws HibernateException {
		if (value == null) {
			return null;
		} else {
			try {
				final VulpeEntity<?> origin = (VulpeEntity<?>) value;
				final VulpeEntity<?> destination = this.returnedClass.newInstance();
				VulpeReflectUtil.copy(destination, origin);
				return destination;
			} catch (Exception e) {
				throw new VulpeSystemException(e);
			}
		}
	}

	public boolean isMutable() {
		return true;
	}

	public Serializable disassemble(final Object value) throws HibernateException {
		return (VulpeEntity<?>) value;
	}

	public Object assemble(final Serializable cached, final Object owner) throws HibernateException {
		return cached;
	}

	public Object replace(final Object original, final Object target, final Object owner)
			throws HibernateException {
		return original;
	}

	public int hashCode(final Object obj) throws HibernateException {
		return obj.hashCode();
	}

	public void setParameterValues(final Properties props) {
		try {
			this.returnedClass = (Class<? extends VulpeEntity<?>>) Class.forName(props
					.getProperty("returnedClass"));
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
		try {
			this.serviceClass = (Class<? extends VulpeService>) Class.forName(props
					.getProperty("serviceClass"));
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
		this.idClass = (Class<? extends Serializable>) VulpeReflectUtil.getFieldClass(
				this.returnedClass, "id");

		final String type = (String) props.get("type");
		if (StringUtils.isNotEmpty(type)) {
			try {
				this.type = Integer.parseInt(type);
			} catch (Exception e) {
				throw new VulpeSystemException(e);
			}
		}
	}

	protected VulpeEntity<?> invokeFind(final Object identifier) {
		try {
			final VulpeService services = VulpeServiceLocator.getInstance().lookup(
					this.serviceClass);
			final Method method = services.getClass().getMethod(
					Operation.FIND.getValue().concat(this.returnedClass.getSimpleName()),
					this.idClass);
			return (VulpeEntity<?>) method.invoke(services, identifier);
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
	}

	@Override
	public Object nullSafeGet(ResultSet arg0, String[] arg1, SessionImplementor arg2, Object arg3)
			throws HibernateException, SQLException {
		return null;
	}

	@Override
	public void nullSafeSet(PreparedStatement arg0, Object arg1, int arg2, SessionImplementor arg3)
			throws HibernateException, SQLException {

	}
}