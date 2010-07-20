/**
 * Vulpe Framework - Copyright (c) Active Thread
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.VulpeReflectUtil;
import org.vulpe.commons.model.services.VulpeServiceLocator;
import org.vulpe.exception.VulpeSystemException;
import org.vulpe.model.entity.VulpeEntity;
import org.vulpe.model.services.VulpeService;

@SuppressWarnings("unchecked")
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
				VulpeReflectUtil.getInstance().copy(destination, origin);
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
		this.idClass = (Class<? extends Serializable>) VulpeReflectUtil.getInstance().getFieldClass(
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
			final VulpeService services = VulpeServiceLocator.getInstance().lookup(this.serviceClass);
			final Method method = services.getClass().getMethod(
					VulpeConstants.Action.FIND.concat(this.returnedClass.getSimpleName()), this.idClass);
			return (VulpeEntity<?>) method.invoke(services, identifier);
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
	}
}