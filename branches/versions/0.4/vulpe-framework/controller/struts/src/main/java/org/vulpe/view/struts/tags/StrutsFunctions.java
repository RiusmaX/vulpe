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
package org.vulpe.view.struts.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.VulpeConstants.Configuration.Ever;
import org.vulpe.commons.VulpeConstants.View.Struts;
import org.vulpe.commons.helper.VulpeCacheHelper;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.controller.commons.EverParameter;
import org.vulpe.controller.commons.VulpeBaseDetailConfig;
import org.vulpe.view.struts.form.beans.SessionPaging;
import org.vulpe.view.tags.Functions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.conversion.impl.XWorkConverter;

@SuppressWarnings( { "unchecked" })
public final class StrutsFunctions extends Functions {

	private static final Logger LOG = Logger.getLogger(StrutsFunctions.class.getName());

	private StrutsFunctions() {
		// default constructor
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
		final StringBuilder link = new StringBuilder();
		link.append(ServletActionContext.getRequest().getContextPath()).append("/").append(
				getEver().<String> getAuto(Ever.CURRENT_CONTROLLER_NAME)).append(
				"/download?now.downloadKey=").append(urlEncode(key));
		if (StringUtils.isNotEmpty(contentType)) {
			link.append("&now.downloadContentType=").append(contentType);
		}
		if (StringUtils.isNotEmpty(contentDisposition)) {
			link.append("&now.downloadContentDisposition=").append(contentDisposition);
		}
		link.append("&now.access=").append(System.currentTimeMillis());
		return link.toString();
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
			saveInSession(key, value, false);
		}

		return linkKey(key, contentType, contentDisposition);
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
		if (VulpeValidationUtil.isNotEmpty(value)) {
			if (expire) {
				getEver().putWeakRef(key, value);
			} else {
				getEver().put(key, value);
			}
		} else {
			getEver().remove(key);
		}
		return value;
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
		if (VulpeValidationUtil.isNotEmpty(value)) {
			final Byte[] bytes = (Byte[]) value;
			byte[] imageData = new byte[bytes.length];
			for (int i = 0; i < bytes.length; i++) {
				imageData[i] = bytes[i].byteValue();
			}
			try {
				saveInSession(key, resizeImageAsJPG(imageData, width), expire);
			} catch (IOException e) {
				LOG.error(e);
			}
		} else {
			getEver().remove(key);
		}
		return value;
	}

	/**
	 * 
	 * @param pageContext
	 * @param pagingName
	 * @param pageSize
	 * @param fullList
	 * @return
	 */
	public static SessionPaging findPaging(final PageContext pageContext, final String pagingName,
			final Long pageSize, final List fullList) {
		SessionPaging paging = (SessionPaging) pageContext.getSession().getAttribute(pagingName);
		if (paging == null && fullList != null) {
			paging = new SessionPaging(pageSize.intValue(), fullList);
			pageContext.getSession().setAttribute(pagingName, paging);
		}
		return paging;
	}

	/**
	 * 
	 * @param value
	 * @return
	 * @throws JspException
	 */
	public static String toString(final Object value) throws JspException {
		return (String) getXWorkConverter().convertValue(
				ActionContext.getContext().getContextMap(), value, String.class);
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

	private static XWorkConverter getXWorkConverter() {
		return VulpeCacheHelper.getInstance().get(Struts.XWORK_CONVERTER);
	}

	public static EverParameter getEver() {
		return EverParameter.getInstance(ServletActionContext.getRequest().getSession());
	}
}
