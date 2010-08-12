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
package org.vulpe.view.struts.tags;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.beans.ValueBean;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.controller.commons.VulpeBaseDetailConfig;
import org.vulpe.controller.util.ControllerUtil;
import org.vulpe.view.struts.form.beans.SessionPaging;
import org.vulpe.view.tags.Functions;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.XWorkConverter;

@SuppressWarnings({ "unchecked" })
public final class StrutsFunctions extends Functions {

	private static final Logger LOG = Logger.getLogger(StrutsFunctions.class.getName());

	private StrutsFunctions() {
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
			final Class<?> fieldClass = VulpeReflectUtil.getInstance().getFieldClass(
					bean.getClass(), field.replace(".id", ""));
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

			final Class<?> fieldClass = VulpeReflectUtil.getInstance().getFieldClass(
					bean.getClass(), field.replace(".id", ""));
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
		final String link = ServletActionContext
				.getRequest()
				.getContextPath()
				.concat("/")
				.concat(ControllerUtil.getInstance(ServletActionContext.getRequest())
						.getCurrentControllerName()).concat("/download.action?downloadKey=")
				.concat(urlEncode(key)).concat("&downloadContentType=").concat(contentType)
				.concat("&downloadContentDisposition=").concat(contentDisposition)
				.concat("&access=").concat(String.valueOf(System.currentTimeMillis()));
		return link;
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
			final String keyForm = ControllerUtil.getInstance(ServletActionContext.getRequest())
					.getCurrentControllerKey().concat(VulpeConstants.PARAMS_SESSION_KEY);
			final Map formParams = (Map) ServletActionContext.getRequest().getSession()
					.getAttribute(keyForm);
			if (formParams == null || !formParams.containsKey(key)) {
				saveInSession(key, value, false);
			}
		}

		return linkKey(key, contentType, contentDisposition);
	}

	/**
	 * 
	 * @return
	 */
	private static Map getFormParams() {
		final String keyForm = ControllerUtil.getInstance(ServletActionContext.getRequest())
				.getCurrentControllerKey().concat(VulpeConstants.PARAMS_SESSION_KEY);
		Map formParams = (Map) ServletActionContext.getRequest().getSession().getAttribute(keyForm);
		if (formParams == null) {
			formParams = new HashMap();
			ServletActionContext.getRequest().getSession().setAttribute(keyForm, formParams);
		}
		return formParams;
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
		return (String) XWorkConverter.getInstance().convertValue(
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
}
