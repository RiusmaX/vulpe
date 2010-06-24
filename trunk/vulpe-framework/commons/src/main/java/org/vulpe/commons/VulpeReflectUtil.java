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
package org.vulpe.commons;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.vulpe.commons.cache.VulpeCacheHelper;
import org.vulpe.exception.VulpeSystemException;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class VulpeReflectUtil {

	private static final Logger LOG = Logger.getLogger(VulpeReflectUtil.class);

	/**
	 * Returns instance of VulpeReflectUtil.
	 * 
	 * @return
	 */
	public static VulpeReflectUtil getInstance() {
		final VulpeCacheHelper cache = VulpeCacheHelper.getInstance();
		if (!cache.contains(VulpeReflectUtil.class)) {
			cache.put(VulpeReflectUtil.class, new VulpeReflectUtil());
		}
		return cache.get(VulpeReflectUtil.class);
	}

	protected VulpeReflectUtil() {
		// default constructor
	}

	/**
	 * Returns list of fields in class or superclass.
	 * 
	 * @param clazz
	 * @return
	 */
	public List<Field> getFields(final Class<?> clazz) {
		if (VulpeCacheHelper.getInstance().contains(clazz.getName().concat(".fields"))) {
			return VulpeCacheHelper.getInstance().get(clazz.getName().concat(".fields"));
		}
		Class<?> baseClass = clazz;
		final List<Field> list = new ArrayList<Field>();
		while (!baseClass.equals(Object.class)) {
			for (Field field : baseClass.getDeclaredFields()) {
				if (!Modifier.isStatic(field.getModifiers()) && !field.isSynthetic()) {
					list.add(field);
				}
			}
			baseClass = baseClass.getSuperclass();
		}
		VulpeCacheHelper.getInstance().put(clazz.getName().concat(".fields"), list);
		return list;
	}

	/**
	 * Returns list of fields noted by <code>annotationClass</code>.
	 * 
	 * @param clazz
	 * @param annotationClass
	 * @return
	 */
	public List<Field> getFieldsWithAnnotation(final Class<?> clazz,
			final Class<? extends Annotation> annotationClass) {
		final List<Field> filtredFields = new ArrayList<Field>();
		final List<Field> fields = getFields(clazz);
		for (Field field : fields) {
			if (isAnnotationInField(annotationClass, clazz, field)) {
				filtredFields.add(field);
			}
		}
		return filtredFields;
	}

	/**
	 * Returns field of class or superclass.
	 * 
	 * @param clazz
	 * @param fieldName
	 * @return
	 */
	public Field getField(final Class<?> clazz, final String fieldName) {
		Map<String, Field> fieldMap = new HashMap<String, Field>();
		if (VulpeCacheHelper.getInstance().contains(clazz.getName().concat(".fieldMap"))) {
			fieldMap = VulpeCacheHelper.getInstance().get(clazz.getName().concat(".fieldMap"));
		} else {
			final List<Field> fields = getFields(clazz);
			for (Field field : fields) {
				fieldMap.put(field.getName(), field);
			}
			VulpeCacheHelper.getInstance().put(clazz.getName().concat(".fieldMap"), fieldMap);
		}
		return fieldMap.get(fieldName);
	}

	public Class<?> getFieldClass(final Class<?> clazz, final String fieldName) {
		final Field field = getField(clazz, fieldName);
		return field == null ? null : getDeclaredType(clazz, field.getGenericType()).getType();
	}

	/**
	 * Copy attributes from <code>origin</code> to <code>destination</code>.
	 * 
	 * @param destination
	 * @param origin
	 */
	public void copy(final Object destination, final Object origin) {
		final List<Field> fields = getFields(origin.getClass());
		for (Field field : fields) {
			try {
				final Object value = PropertyUtils.getProperty(origin, field.getName());
				if (Collection.class.isAssignableFrom(field.getType())) {
					final Collection valueDes = (Collection) PropertyUtils.getProperty(destination,
							field.getName());
					if (value == null) {
						if (valueDes != null) {
							valueDes.clear();
						}
					} else {
						if (valueDes == null) {
							PropertyUtils.setProperty(destination, field.getName(), value);
						} else {
							valueDes.clear();
							valueDes.addAll((Collection) value);
						}
					}
				} else {
					PropertyUtils.setProperty(destination, field.getName(), value);
				}
			} catch (NoSuchMethodException e) {
				LOG.debug("Method not found.", e);
			} catch (Exception e) {
				throw new VulpeSystemException(e);
			}
		}
	}

	/**
	 * Returns list of methods in class or superclass.
	 * 
	 * @param clazz
	 * @return
	 */
	public List<Method> getMethods(final Class<?> clazz) {
		if (VulpeCacheHelper.getInstance().contains(clazz.getName().concat(".methods"))) {
			return VulpeCacheHelper.getInstance().get(clazz.getName().concat(".methods"));
		}
		Class<?> baseClass = clazz;
		final List<Method> list = new ArrayList<Method>();
		while (!baseClass.equals(Object.class)) {
			for (Method method : baseClass.getDeclaredMethods()) {
				if (!method.isSynthetic() && !method.isBridge()
						&& !Modifier.isStatic(method.getModifiers())) {
					list.add(method);
				}
			}
			baseClass = baseClass.getSuperclass();
		}
		VulpeCacheHelper.getInstance().put(clazz.getName().concat(".methods"), list);
		return list;
	}

	/**
	 * Returns method of class or superclass.
	 * 
	 * @param clazz
	 * @param methodName
	 * @param typeParams
	 * @return
	 */
	public Method getMethod(final Class<?> clazz, final String methodName,
			final Class<?>... typeParams) {
		final List<Method> methods = getMethods(clazz);
		Method methodFirst = null;
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				if ((typeParams == null && method.getParameterTypes() == null)
						|| (typeParams != null && method.getParameterTypes() != null && Arrays
								.equals(typeParams, method.getParameterTypes()))) {
					return method;
				}
				if (typeParams == null && methodFirst == null) {
					methodFirst = method;
				}
			}
		}
		return methodFirst;
	}

	/**
	 * Returns class of Type.
	 * 
	 * @param clazz
	 * @param type
	 * @return
	 */
	public DeclaredType getDeclaredType(final Class<?> clazz, final Type type) {
		if (type == null) {
			return null;
		}

		DeclaredType declaredType = null;
		if (type instanceof Class) {
			declaredType = new DeclaredType();
			declaredType.setType((Class<?>) type);
		} else if (type instanceof ParameterizedType) {
			declaredType = new DeclaredType();
			final ParameterizedType parameterizedType = (ParameterizedType) type;
			final DeclaredType rawType = getDeclaredType(clazz, parameterizedType.getRawType());
			declaredType.setType(rawType.getType());
			for (int i = 0; i < parameterizedType.getActualTypeArguments().length; i++) {
				declaredType.getItems().add(
						getDeclaredType(clazz, parameterizedType.getActualTypeArguments()[i]));
			}
		} else if (type instanceof TypeVariable) {
			return getDeclaredTypeVariable(clazz, (TypeVariable<?>) type);
		} else if (type instanceof WildcardType) {
			declaredType = new DeclaredType();
			final WildcardType wildcardType = (WildcardType) type;
			if (wildcardType.getLowerBounds().length > 0) {
				declaredType.setType(getDeclaredType(clazz, wildcardType.getLowerBounds()[0])
						.getType());
			} else {
				declaredType.setType(null);
			}

			for (int i = 0; i < wildcardType.getUpperBounds().length; i++) {
				declaredType.getTypeItems().add(
						getDeclaredType(clazz, wildcardType.getUpperBounds()[i]));
			}

			return declaredType;
		}
		return declaredType;
	}

	/**
	 * Returns class of TypeVariable.
	 * 
	 * @param clazz
	 * @param typeVariable
	 * @return
	 */
	private DeclaredType getDeclaredTypeVariable(final Class<?> clazz,
			final TypeVariable<?> typeVariable) {
		final int index = getIndexTypeVariable(typeVariable);
		final VariableInfo info = new VariableInfo(index);
		DeclaredType declaredType = getDeclaredTypeVariableDeclared(clazz, clazz.getSuperclass(),
				typeVariable, info);
		if (declaredType == null) {
			if (info.isInSuperclass()) {
				final ParameterizedType pType = (ParameterizedType) info.getFirstClass()
						.getGenericSuperclass();
				final Type type = pType.getActualTypeArguments()[info.getIndex()];
				if (type instanceof TypeVariable) {
					declaredType = getDeclaredType(clazz, ((TypeVariable<?>) type).getBounds()[0]);
				} else {
					declaredType = getDeclaredType(clazz, type);
				}
			} else {
				declaredType = getDeclaredType(clazz,
						info.getFirstClass().getTypeParameters()[info.getIndex()].getBounds()[0]);
			}
			return declaredType;
		}
		return declaredType;
	}

	/**
	 * Returns class of TypeVariable.
	 * 
	 * @param clazz
	 * @param superClass
	 * @param typeVariable
	 * @param info
	 * @return
	 */
	private DeclaredType getDeclaredTypeVariableDeclared(final Class<?> clazz,
			final Class<?> superClass, final TypeVariable<?> typeVariable, final VariableInfo info) {
		if (clazz.equals(Object.class)) {
			return null;
		}

		if (typeVariable.getGenericDeclaration().equals(superClass)
				|| typeVariable.getGenericDeclaration().equals(clazz)) {
			final int index = getIndexTypeVariable(typeVariable);
			if (typeVariable.getGenericDeclaration().equals(clazz)) {
				info.setIndex(index);
				info.setFirstClass((Class<?>) typeVariable.getGenericDeclaration());
				info.setInSuperclass(false);
				return null;
			} else {
				final ParameterizedType pType = (ParameterizedType) clazz.getGenericSuperclass();
				final Type type = pType.getActualTypeArguments()[index];
				if (type instanceof TypeVariable) {
					info.setIndex(getIndexTypeVariable((TypeVariable<?>) type));
					info.setFirstClass(clazz);
					info.setInSuperclass(true);
					return null;
				}
				return getDeclaredType(clazz, type);
			}
		} else {
			final DeclaredType declaredType = getDeclaredTypeVariableDeclared(
					clazz.getSuperclass(), superClass.getSuperclass(), typeVariable, info);
			if (declaredType == null) {
				ParameterizedType parameterizedType = null;
				if (clazz.getGenericSuperclass() instanceof ParameterizedType) {
					parameterizedType = (ParameterizedType) clazz.getGenericSuperclass();
					info.setInSuperclass(true);
					info.setFirstClass(clazz);
				} else {
					parameterizedType = (ParameterizedType) superClass.getGenericSuperclass();
					info.setInSuperclass(false);
					info.setFirstClass(superClass);
				}
				final Type type = parameterizedType.getActualTypeArguments()[info.getIndex()];
				if (type instanceof TypeVariable) {
					info.setIndex(getIndexTypeVariable((TypeVariable<?>) type));
					return null;
				}
				return getDeclaredType(clazz, type);
			} else {
				return declaredType;
			}
		}
	}

	/**
	 * Returns position of TypeVariable in list of getTypeParameters on class.
	 * 
	 * @param typeVariable
	 * @return
	 */
	private int getIndexTypeVariable(final TypeVariable<?> typeVariable) {
		int index = -1;
		for (TypeVariable<?> typeVariable2 : typeVariable.getGenericDeclaration()
				.getTypeParameters()) {
			index++;
			if (typeVariable.getName().equals(typeVariable2.getName())) {
				break;
			}
		}
		return index;
	}

	/**
	 * Auxiliary Class to locate class of TypeVariable
	 */
	private static class VariableInfo {

		private int index;
		private boolean inSuperclass;
		private Class<?> firstClass;

		public Class<?> getFirstClass() {
			return firstClass;
		}

		public void setFirstClass(final Class<?> firstClass) {
			this.firstClass = firstClass;
		}

		public VariableInfo(final int index) {
			this.index = index;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(final int index) {
			this.index = index;
		}

		public boolean isInSuperclass() {
			return inSuperclass;
		}

		public void setInSuperclass(final boolean inSuperclass) {
			this.inSuperclass = inSuperclass;
		}
	}

	/**
	 * Class to store info of Type
	 */
	public static class DeclaredType {
		/**
		 * Generic items classes
		 */
		private List<DeclaredType> items = new ArrayList<DeclaredType>();
		/**
		 * Class of Type
		 */
		private Class<?> type;

		private List<DeclaredType> typeItems = new ArrayList<DeclaredType>();

		public DeclaredType() {
			// default constructor
		}

		public DeclaredType(final Class<?> type) {
			this.type = type;
		}

		public String getAssign() {
			final StringBuilder stringBuilder = new StringBuilder();
			if (isTypeItems()) {
				stringBuilder.append("? extends ");
				for (int i = 0; i < getTypeItems().size(); i++) {
					stringBuilder.append(getTypeItems().get(i).getAssign());
					if (i + 1 < getTypeItems().size()) {
						stringBuilder.append("& ");
					}
				}
			} else {
				stringBuilder.append(getType().getName());
			}

			if (getItems() != null && !getItems().isEmpty()) {
				stringBuilder.append("<");
				for (int i = 0; i < getItems().size(); i++) {
					final DeclaredType item = getItems().get(i);
					stringBuilder.append(item.getAssign());

					if (i + 1 < getItems().size()) {
						stringBuilder.append(",");
					}
				}
				stringBuilder.append(">");
			}
			return stringBuilder.toString();
		}

		public Class<?> getType() {
			return type;
		}

		public void setType(final Class<?> type) {
			this.type = type;
		}

		public List<DeclaredType> getItems() {
			return items;
		}

		public void setItems(final List<DeclaredType> item) {
			this.items = item;
		}

		public List<DeclaredType> getTypeItems() {
			return typeItems;
		}

		public void setTypeItems(final List<DeclaredType> typeItems) {
			this.typeItems = typeItems;
		}

		public boolean isTypeItems() {
			return (getTypeItems() != null && getTypeItems().size() > 0);
		}

		@Override
		public String toString() {
			return getAssign();
		}
	}

	/**
	 * Returns class on index in the parameterized type on <code>clazz</code> or
	 * <code>super</code>.
	 * 
	 * @param clazz
	 * @param index
	 * @return
	 */
	public Class<?> getIndexClass(final Class<?> clazz, final int index) {
		if (clazz.getGenericSuperclass() instanceof ParameterizedType) {
			final ParameterizedType type = (ParameterizedType) clazz.getGenericSuperclass();
			DeclaredType declaredType = null;
			if (type.getActualTypeArguments().length > index + 1) {
				declaredType = getDeclaredType(clazz, type.getActualTypeArguments()[index]);
			} else {
				declaredType = getDeclaredType(clazz,
						type.getActualTypeArguments()[type.getActualTypeArguments().length - 1]);
			}
			return (Class<?>) declaredType.getType();
		} else if (clazz.getGenericSuperclass() instanceof Class) {
			return getIndexClass(clazz.getSuperclass(), index);
		} else {
			return null;
		}
	}

	/**
	 * Checks if class is noted by <code>annotationClass</code>.
	 * 
	 * @param <T>
	 * @param annotationClass
	 * @param clazz
	 * @return
	 */
	public <T extends Annotation> T getAnnotationInClass(final Class<T> annotationClass,
			final Class<?> clazz) {
		Class<?> baseClass = clazz;
		while (baseClass != null && !baseClass.equals(Object.class)) {
			if (baseClass.isAnnotationPresent(annotationClass)) {
				return baseClass.getAnnotation(annotationClass);
			}

			for (Class<?> iClass : baseClass.getInterfaces()) {
				final T tAnnotation = getAnnotationInClass(annotationClass, iClass);
				if (tAnnotation != null) {
					return tAnnotation;
				}
			}

			baseClass = baseClass.getSuperclass();
		}

		return null;
	}

	/**
	 * Checks if class is noted by <code>annotationClass</code>.
	 * 
	 * @param <T>
	 * @param annotationClass
	 * @param clazz
	 * @return
	 */
	public <T extends Annotation> boolean isAnnotationInClass(final Class<T> annotationClass,
			final Class<?> clazz) {
		return getAnnotationInClass(annotationClass, clazz) != null;
	}

	/**
	 * Checks if field is noted by <code>annotationClass</code>.
	 * 
	 * @param <T>
	 * @param annotationClass
	 * @param clazz
	 * @param fieldName
	 * @return
	 */
	public <T extends Annotation> T getAnnotationInField(final Class<T> annotationClass,
			final Class<?> clazz, final String fieldName) {
		final Field field = getField(clazz, fieldName);
		if (field != null && field.isAnnotationPresent(annotationClass)) {
			return field.getAnnotation(annotationClass);
		}

		final String name = fieldName.substring(0, 1).toUpperCase().concat(fieldName.substring(1));

		Method method = getMethod(clazz, "get".concat(name));
		if (method != null && method.isAnnotationPresent(annotationClass)) {
			return method.getAnnotation(annotationClass);
		}

		method = getMethod(clazz, "set".concat(name));
		if (method != null && method.isAnnotationPresent(annotationClass)) {
			return method.getAnnotation(annotationClass);
		}

		return null;
	}

	/**
	 * Checks if field is noted by <code>annotationClass</code>.
	 * 
	 * @param <T>
	 * @param annotationClass
	 * @param clazz
	 * @param field
	 * @return
	 */
	public <T extends Annotation> T getAnnotationInField(final Class<T> annotationClass,
			final Class<?> clazz, final Field field) {
		if (field.isAnnotationPresent(annotationClass)) {
			return field.getAnnotation(annotationClass);
		}

		final String name = field.getName().substring(0, 1).toUpperCase()
				.concat(field.getName().substring(1));

		Method method = getMethod(clazz, "get".concat(name));
		if (method != null && method.isAnnotationPresent(annotationClass)) {
			return method.getAnnotation(annotationClass);
		}

		method = getMethod(clazz, "set".concat(name));
		if (method != null && method.isAnnotationPresent(annotationClass)) {
			return method.getAnnotation(annotationClass);
		}

		return null;
	}

	/**
	 * Checks if field is noted by <code>annotationClass</code>.
	 * 
	 * @param <T>
	 * @param annotationClass
	 * @param clazz
	 * @param fieldName
	 * @return
	 */
	public <T extends Annotation> boolean isAnnotationInField(final Class<T> annotationClass,
			final Class<?> clazz, final String fieldName) {
		return getAnnotationInField(annotationClass, clazz, fieldName) != null;
	}

	/**
	 * Checks if field is noted by <code>annotationClass</code>.
	 * 
	 * @param <T>
	 * @param annotationClass
	 * @param clazz
	 * @param field
	 * @return
	 */
	public <T extends Annotation> boolean isAnnotationInField(final Class<T> annotationClass,
			final Class<?> clazz, final Field field) {
		return getAnnotationInField(annotationClass, clazz, field) != null;
	}

	/**
	 * Sets field value in object.
	 * 
	 * @param object
	 * @param fieldName
	 * @param value
	 */
	public void setFieldValue(final Object object, final String fieldName, final Object value) {
		try {
			final String name = fieldName.substring(0, 1).toUpperCase()
					.concat(fieldName.substring(1));

			Method method = null;
			Class<?> classField = (value == null ? getFieldClass(object.getClass(), fieldName)
					: value.getClass());
			while (classField != null && !classField.equals(Object.class)) {
				method = getMethod(object.getClass(), "set".concat(name), classField);
				if (method != null) {
					break;
				}
				classField = classField.getSuperclass();
			}
			if (method != null) {
				synchronized (method) {
					boolean setou = false;
					if (!method.isAccessible()) {
						method.setAccessible(true);
						setou = true;
					}
					try {
						method.invoke(object, value);
					} catch (Exception e) {
						LOG.error(e);
					} finally {
						if (setou) {
							method.setAccessible(false);
						}
					}
				}
			}
			final Field field = getField(object.getClass(), fieldName);
			if (field != null) {
				synchronized (field) {
					boolean setou = false;
					if (!field.isAccessible()) {
						field.setAccessible(true);
						setou = true;
					}
					try {
						field.set(object, value);
					} finally {
						if (setou) {
							field.setAccessible(false);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
	}

	/**
	 * Returns field value from object.
	 * 
	 * @param <T>
	 * @param object
	 * @param fieldName
	 * @return
	 */
	public <T> T getFieldValue(final Object object, final String fieldName) {
		try {
			final String name = fieldName.substring(0, 1).toUpperCase()
					.concat(fieldName.substring(1));

			final Method method = getMethod(object.getClass(), "get".concat(name));
			if (method != null) {
				synchronized (method) {
					boolean seted = false;
					if (!method.isAccessible()) {
						method.setAccessible(true);
						seted = true;
					}
					try {
						return (T) method.invoke(object);
					} catch (Exception e) {
						LOG.error(e);
					} finally {
						if (seted) {
							method.setAccessible(false);
						}
					}
				}
			}
			final Field field = getField(object.getClass(), fieldName);
			if (field != null) {
				synchronized (field) {
					boolean seted = false;
					if (!field.isAccessible()) {
						field.setAccessible(true);
						seted = true;
					}
					try {
						return (T) field.get(object);
					} finally {
						if (seted) {
							field.setAccessible(false);
						}
					}
				}
			}
			return null;
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
	}

}