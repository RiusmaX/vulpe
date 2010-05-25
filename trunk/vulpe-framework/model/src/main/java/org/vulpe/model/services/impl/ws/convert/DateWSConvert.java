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

import javax.xml.datatype.XMLGregorianCalendar;

import org.vulpe.commons.VulpeConvertUtil;

/**
 * 
 * 
 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
 */
public class DateWSConvert implements WSConvert<Date, XMLGregorianCalendar> {

	public Date toBean(final XMLGregorianCalendar wsBean) {
		return VulpeConvertUtil.getInstance().convertToDate(wsBean);
	}

	public XMLGregorianCalendar toWSBean(final Date bean) {
		return VulpeConvertUtil.getInstance().convertToXMLDate(bean);
	}
}