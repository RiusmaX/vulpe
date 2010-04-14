package org.vulpe.controller.struts.util;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Map;

import org.vulpe.common.ReflectUtil;
import org.vulpe.common.ReflectUtil.DeclaredType;

import com.opensymphony.xwork2.util.DefaultObjectTypeDeterminer;

/**
 * Classe utilizada para corrigir problemas ao determinar a classe de tipos
 * genericos.
 *
 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
 */
public class GenericsObjectTypeDeterminer extends DefaultObjectTypeDeterminer {

	@Override
	@SuppressWarnings("unchecked")
	public Class getElementClass(final Class parentClass,
			final String property, final Object key) {
		Class clazz = super.getElementClass(parentClass, property, key);
		if (clazz == null) {
			final Field field = ReflectUtil.getInstance().getField(parentClass,
					property);
			if (field.getGenericType() instanceof ParameterizedType) {
				final ParameterizedType type = (ParameterizedType) field
						.getGenericType();
				final int index = (Map.class.isAssignableFrom(ReflectUtil
						.getInstance()
						.getDeclaredType(clazz, type.getRawType()).getType()) ? 1
						: 0);
				final DeclaredType declaredType = ReflectUtil.getInstance()
						.getDeclaredType(parentClass, type);
				clazz = declaredType.getItems().get(index).getType();
			}
		}
		return clazz;
	}

}