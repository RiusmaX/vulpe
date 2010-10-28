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
package org.vulpe.model.services.impl.ws.convert;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.vulpe.commons.helper.VulpeCacheHelper;
import org.vulpe.model.services.impl.ws.XMLType;

/**
 * 
 * @author <a href="mailto:fabio.viana@vulpe.org">Fábio Viana</a>
 */
public class WSConvertFactory {

	public static WSConvertFactory getInstance() {
		final VulpeCacheHelper cache = VulpeCacheHelper.getInstance();
		if (!cache.contains(WSConvertFactory.class)) {
			cache.put(WSConvertFactory.class, new WSConvertFactory());
		}
		return cache.get(WSConvertFactory.class);
	}

	private transient Map<String, WSConvert<?, ?>> converts;
	private transient Map<Class<?>, XMLType> xmlTypes;

	protected WSConvertFactory() {
		converts = new HashMap<String, WSConvert<?, ?>>();
		converts.put(Date.class.getName(), new DateWSConvert());

		xmlTypes = new HashMap<Class<?>, XMLType>();
		xmlTypes
				.put(Date.class, new XMLType("dateTime", "javax.xml.datatype.XMLGregorianCalendar"));
	}

	/**
	 * Inicia o registro de tipos básicos
	 */
	protected void init() {
		converts = new HashMap<String, WSConvert<?, ?>>();
		register(Date.class.getName(), new DateWSConvert());

		xmlTypes = new HashMap<Class<?>, XMLType>();
		register(Date.class, new XMLType("dateTime", "javax.xml.datatype.XMLGregorianCalendar"));
	}

	/**
	 * Retorna o WSConvert do tipo informado
	 */
	@SuppressWarnings("unchecked")
	public <BEAN, WSBEAN> WSConvert<BEAN, WSBEAN> getWSConvert(final String type) {
		return (WSConvert<BEAN, WSBEAN>) converts.get(type);
	}

	/**
	 * Registra um tipo e seu WSConvert
	 */
	public void register(final String type, final WSConvert<?, ?> converter) {
		converts.put(type, converter);
	}

	/**
	 * Registra um tipo e seu XMLType
	 */
	public void register(final Class<?> type, final XMLType xmlType) {
		xmlTypes.put(type, xmlType);
	}

	/**
	 * Retorna o XMLType do tipo
	 * 
	 * @param type
	 * @return
	 */
	public XMLType getXMLType(final Class<?> type) {
		if (type == null || type.equals(Object.class)) {
			return null;
		}

		if (!xmlTypes.containsKey(type)) {
			return getXMLType(type.getSuperclass());
		}

		return xmlTypes.get(type);
	}
}