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
package org.vulpe.model.entity;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

import org.vulpe.exception.VulpeSystemException;

/**
 * Default class Entity Delegate to transmit data on WebServices-Client
 * 
 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
 */
@SuppressWarnings( { "unchecked", "serial" })
public abstract class AbstractVulpeBaseEntityDelegate<T extends VulpeBaseEntity<ID>, ID extends Serializable & Comparable>
		implements Serializable {

	private T bean;

	public T getBean() {
		if (this.bean == null && getClass().getGenericSuperclass() instanceof ParameterizedType) {
			final ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
			if (type.getActualTypeArguments() != null && type.getActualTypeArguments().length > 0
					&& type.getActualTypeArguments()[0] instanceof Class) {
				try {
					this.bean = (T) ((Class) type.getActualTypeArguments()[0]).newInstance();
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