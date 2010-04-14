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
import org.vulpe.common.Constants;
import org.vulpe.common.ReflectUtil;
import org.vulpe.common.model.services.VulpeServiceLocator;
import org.vulpe.exception.VulpeSystemException;
import org.vulpe.model.entity.VulpeBaseEntity;
import org.vulpe.model.services.Services;

public class EntityType implements UserType, ParameterizedType {
	/**
	 * Classe de retorno
	 */
	private transient Class<? extends VulpeBaseEntity<?>> returnedClass = null;
	/**
	 * Service class
	 */
	private transient Class<? extends Services> serviceClass = null;
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

	/**
	 * Tipo que o UserType retorna
	 */
	@SuppressWarnings("unchecked")
	public Class returnedClass() {
		return returnedClass;
	}

	/**
	 * Compara dois valores formatados
	 */
	public boolean equals(final Object obj0, final Object obj1)
			throws HibernateException {
		final VulpeBaseEntity<?> entity1 = (VulpeBaseEntity<?>) obj0;
		final VulpeBaseEntity<?> entity2 = (VulpeBaseEntity<?>) obj1;
		if (entity1 == null) {
			return entity2 == null;
		}
		if (entity2 == null) {
			return entity1 == null;
		}
		return entity1.equals(entity2);
	}

	/**
	 * Obtem o valor do banco, adiciona a máscara e retorna o valor formatado
	 */
	public Object nullSafeGet(final ResultSet resultSet, final String[] names,
			final Object owner) throws HibernateException, SQLException {
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

	/**
	 * Pega o valor com a máscara, remove a máscara e seta no banco
	 */
	public void nullSafeSet(final PreparedStatement pstm, final Object value,
			final int index) throws HibernateException, SQLException {
		final VulpeBaseEntity<?> entity = (VulpeBaseEntity<?>) value;
		if (entity == null || entity.getId() == null) {
			pstm.setNull(index, type);
		} else {
			pstm.setObject(index, entity.getId());
		}
	}

	/**
	 * Copia uma nova instancia do valor
	 */
	public Object deepCopy(final Object value) throws HibernateException {
		if (value == null) {
			return null;
		} else {
			try {
				final VulpeBaseEntity<?> origin = (VulpeBaseEntity<?>) value;
				final VulpeBaseEntity<?> destination = this.returnedClass
						.newInstance();
				ReflectUtil.getInstance().copy(destination, origin);
				return destination;
			} catch (Exception e) {
				throw new VulpeSystemException(e);
			}
		}
	}

	/**
	 * Indica que o valor é mutável
	 */
	public boolean isMutable() {
		return true;
	}

	/**
	 * Serializa o valor original
	 */
	public Serializable disassemble(final Object value)
			throws HibernateException {
		return (VulpeBaseEntity<?>) value;
	}

	/**
	 * Deserializa o valor em cache
	 */
	public Object assemble(final Serializable cached, final Object owner)
			throws HibernateException {
		return cached;
	}

	/**
	 * Retorna um possível subistituto
	 */
	public Object replace(final Object original, final Object target,
			final Object owner) throws HibernateException {
		return original;
	}

	/**
	 * Retorna o hashCode do valor formatado
	 */
	public int hashCode(final Object obj) throws HibernateException {
		return obj.hashCode();
	}

	/**
	 * Seta os parametros de configuração da máscara
	 */
	@SuppressWarnings("unchecked")
	public void setParameterValues(final Properties props) {
		try {
			this.returnedClass = (Class<? extends VulpeBaseEntity<?>>) Class
					.forName(props.getProperty("returnedClass"));
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
		try {
			this.serviceClass = (Class<? extends Services>) Class.forName(props
					.getProperty("serviceClass"));
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
		this.idClass = (Class<? extends Serializable>) ReflectUtil
				.getInstance().getFieldClass(this.returnedClass, "id");

		final String type = (String) props.get("type");
		if (StringUtils.isNotEmpty(type)) {
			try {
				this.type = Integer.parseInt(type);
			} catch (Exception e) {
				throw new VulpeSystemException(e);
			}
		}
	}

	protected VulpeBaseEntity<?> invokeFind(final Object identifier) {
		try {
			final Services services = VulpeServiceLocator.getInstance().lookup(
					this.serviceClass);
			final Method method = services.getClass().getMethod(
					Constants.Action.FIND.concat(this.returnedClass
							.getSimpleName()), this.idClass);
			return (VulpeBaseEntity<?>) method.invoke(services, identifier);
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
	}
}