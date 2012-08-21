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
	 * Inicia o registro de tipos b�sicos
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