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
package org.vulpe.view.vraptor.tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.beans.ValueBean;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.controller.commons.VulpeBaseDetailConfig;
import org.vulpe.view.tags.Functions;

@SuppressWarnings( { "unchecked" })
public final class VRaptorFunctions extends Functions {

	private static final Logger LOG = Logger.getLogger(VRaptorFunctions.class.getName());

	private VRaptorFunctions() {
		// default constructor
	}

	/**
	 * 
	 * @param bean
	 * @param field
	 * @return
	 * @throws JspException
	 */
	public static List listInField(final Object bean, final String field) throws JspException {
		try {
			if (bean == null) {
				return null;
			}

			final List list = new ArrayList();
			final Class<?> fieldClass = VulpeReflectUtil.getFieldClass(bean.getClass(), field
					.replace(".id", ""));
			if (fieldClass.isEnum()) {
				String key = null;
				String value = null;
				for (Object item : fieldClass.getEnumConstants()) {
					key = fieldClass.getName().concat(".").concat(item.toString());
					value = findText(key);
					list.add(new ValueBean(item.toString(), value));
				}
			}
			return list;
		} catch (Exception e) {
			throw new JspException(e);
		}
	}

	/**
	 * 
	 * @param bean
	 * @param field
	 * @return
	 * @throws JspException
	 */
	public static Object enumInField(final Object bean, final String field, final Object fieldValue)
			throws JspException {
		try {
			if (bean == null) {
				return null;
			}

			final Class<?> fieldClass = VulpeReflectUtil.getFieldClass(bean.getClass(), field
					.replace(".id", ""));
			if (fieldClass == null) {
				return null;
			}
			if (fieldClass.isEnum()) {
				String key = null;
				String value = null;
				for (Object item : fieldClass.getEnumConstants()) {
					if (item.equals(fieldValue)) {
						key = fieldClass.getName().concat(".").concat(item.toString());
						value = findText(key);
						return value;
					}
				}
			}
			return null;
		} catch (Exception e) {
			throw new JspException(e);
		}
	}

	/**
	 * 
	 * @param key
	 * @param contentType
	 * @param contentDisposition
	 * @return
	 * @throws JspException
	 */
	public static String linkKey(final String key, final String contentType,
			final String contentDisposition) throws JspException {
		// final String link =
		// getRequestInfo().getRequest().getContextPath().concat("/").concat(
		// new
		// ControllerUtil().getCurrentControllerName(null)).concat("/download?now.downloadKey=").concat(urlEncode(key))
		// .concat("&now.downloadContentType=").concat(contentType).concat("&now.downloadContentDisposition=").concat(
		// contentDisposition).concat("&now.access=").concat(String.valueOf(System.currentTimeMillis()));
		// return link;
		return "";
	}

	/**
	 * 
	 * @param pageContext
	 * @param property
	 * @param contentType
	 * @param contentDisposition
	 * @return
	 * @throws JspException
	 */
	public static String linkProperty(final PageContext pageContext, final String property,
			final String contentType, final String contentDisposition) throws JspException {
		String baseName = "entity.";
		final VulpeBaseDetailConfig detailConfig = (VulpeBaseDetailConfig) eval(pageContext,
				"${targetConfig}");
		if (detailConfig != null) {
			final Number index = (Number) eval(pageContext, "${".concat(detailConfig.getBaseName())
					.concat("_status.index}"));
			baseName = eval(pageContext, "${targetConfigPropertyName}").toString().concat("[")
					.concat(index.toString()).concat("].");
		}

		final String key = (property.contains(baseName)) ? property : baseName.concat(property);

		final Object value = getProperty(pageContext, property);
		if (VulpeValidationUtil.isNotEmpty(value)) {
			// final String keyForm = new
			// ControllerUtil().getCurrentControllerKey(null).concat(
			// VulpeConstants.PARAMS_SESSION_KEY);
			// final Map formParams = (Map)
			// getRequestInfo().getRequest().getSession().getAttribute(keyForm);
			// if (formParams == null || !formParams.containsKey(key)) {
			// saveInSession(key, value, false);
			// }
		}

		return linkKey(key, contentType, contentDisposition);
	}

	/**
	 * 
	 * @return
	 */
	private static Map getFormParams() {
		// final String keyForm = new
		// ControllerUtil().getCurrentControllerKey(null).concat(VulpeConstants.PARAMS_SESSION_KEY);
		// Map formParams = (Map)
		// getRequestInfo().getRequest().getSession().getAttribute(keyForm);
		// if (formParams == null) {
		// formParams = new HashMap();
		// getRequestInfo().getRequest().getSession().setAttribute(keyForm,
		// formParams);
		// }
		// return formParams;
		return null;
	}

	/**
	 * 
	 * @param pageContext
	 * @param key
	 * @param contentType
	 * @param contentDisposition
	 * @param width
	 * @param thumbWidth
	 * @return
	 * @throws JspException
	 */
	public static String linkImage(final PageContext pageContext, final String key,
			final String contentType, final String contentDisposition, final Integer width,
			final Integer thumbWidth) throws JspException {
		Object value = getProperty(pageContext, key);
		if (value != null) {
			value = saveImageInSession(key, value, false, thumbWidth);
			saveInSession(key, value, false);
			if (thumbWidth != null && thumbWidth > 0) {
				saveImageInSession(key.concat(VulpeConstants.Upload.Image.THUMB), value, false,
						thumbWidth);
			}
		}
		return linkKey(key, contentType, contentDisposition);
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @param expire
	 * @return
	 */
	public static Object saveInSession(final String key, final Object value, final Boolean expire) {
		final Object newValue = value;
		if (VulpeValidationUtil.isNotEmpty(newValue)) {
			getFormParams().put(key, new Object[] { expire, newValue });
		} else {
			getFormParams().remove(key);
		}
		return newValue;
	}

	/**
	 * 
	 * @param key
	 * @param value
	 * @param expire
	 * @param width
	 * @return
	 */
	public static Object saveImageInSession(final String key, final Object value,
			final Boolean expire, final Integer width) {
		final Object newValue = value;
		if (VulpeValidationUtil.isNotEmpty(newValue)) {
			final Byte[] bytes = (Byte[]) newValue;
			byte[] imageData = new byte[bytes.length];
			for (int i = 0; i < bytes.length; i++) {
				imageData[i] = bytes[i].byteValue();
			}
			try {
				getFormParams().put(key,
						new Object[] { expire, resizeImageAsJPG(imageData, width) });
			} catch (IOException e) {
				LOG.error(e);
			}
		} else {
			getFormParams().remove(key);
		}
		return newValue;
	}

	/**
	 * 
	 * @param value
	 * @return
	 * @throws JspException
	 */
	public static String toString(final Object value) throws JspException {
		return value.toString();
	}

	/**
	 * 
	 * @param pageContext
	 * @param expression
	 * @return
	 * @throws JspException
	 */
	public static String evalString(final PageContext pageContext, final String expression)
			throws JspException {
		try {
			final Object value = eval(pageContext, expression);
			return toString(value);
		} catch (Exception e) {
			throw new JspException(e);
		}
	}

}
