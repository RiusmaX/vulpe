package org.vulpe.model.services.impl.ws.convert;

import java.util.Date;

import javax.xml.datatype.XMLGregorianCalendar;

import org.vulpe.common.ConvertUtil;

/**
 * Classe de conversão de Data pra XMLGregorianCalendar
 *
 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
 */
public class DateWSConvert implements WSConvert<Date, XMLGregorianCalendar> {

	public Date toBean(final XMLGregorianCalendar wsBean) {
		return ConvertUtil.getInstance().convertToDate(wsBean);
	}

	public XMLGregorianCalendar toWSBean(final Date bean) {
		return ConvertUtil.getInstance().convertToXMLDate(bean);
	}
}