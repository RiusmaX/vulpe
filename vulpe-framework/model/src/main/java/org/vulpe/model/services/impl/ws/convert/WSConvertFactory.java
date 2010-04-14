package org.vulpe.model.services.impl.ws.convert;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.vulpe.common.cache.VulpeCacheHelper;
import org.vulpe.model.services.impl.ws.XMLType;

/**
 * Classe utilizada para retornar o conversor de tipos de WebServices <code>
 * XMLGregorianCalendar xmlDate = WSConvertFactory.getInstance().<VulpeDate, XMLGregorianCalendar>getWSConvert(VulpeDate.class.getName()).toWSBean(new VulpeDate(););
 * </code>
 *
 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
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
		xmlTypes.put(Date.class, new XMLType("dateTime",
				"javax.xml.datatype.XMLGregorianCalendar"));
	}

	/**
	 * Inicia o registro de tipos básicos
	 */
	protected void init() {
		converts = new HashMap<String, WSConvert<?, ?>>();
		register(Date.class.getName(), new DateWSConvert());

		xmlTypes = new HashMap<Class<?>, XMLType>();
		register(Date.class, new XMLType("dateTime",
				"javax.xml.datatype.XMLGregorianCalendar"));
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