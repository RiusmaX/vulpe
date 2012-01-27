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
package org.vulpe.view.tags;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.beans.PropertyDescriptor;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.swing.ImageIcon;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.VulpeConstants.Request;
import org.vulpe.commons.VulpeConstants.Security;
import org.vulpe.commons.VulpeConstants.Configuration.Now;
import org.vulpe.commons.beans.ButtonConfig;
import org.vulpe.commons.beans.ValueBean;
import org.vulpe.commons.helper.VulpeCacheHelper;
import org.vulpe.commons.util.VulpeHashMap;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.controller.commons.EverParameter;
import org.vulpe.controller.commons.MultipleResourceBundle;
import org.vulpe.controller.commons.VulpeBaseDetailConfig;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * 
 */
@SuppressWarnings( { "unchecked" })
public class Functions {

	private static final Logger LOG = Logger.getLogger(Functions.class.getName());

	public Functions() {
		// default constructor
	}

	/**
	 * 
	 * @param bean
	 * @param field
	 * @return
	 */
	public static boolean isFieldInValidator(final Object bean, final String field) {
		return false;
	}

	/**
	 * 
	 * @param pageContext
	 * @param expression
	 * @return
	 * @throws JspException
	 */
	public static Object eval(final PageContext pageContext, final String expression)
			throws JspException {
		try {
			return ExpressionEvaluatorManager.evaluate(null, expression, Object.class, pageContext);
		} catch (Exception e) {
			LOG.error("Expression error: " + expression);
			throw new JspException(e);
		}
	}

	/**
	 * 
	 * @param bean
	 * @return
	 */

	public static List fieldsInBean(final Object bean) {
		final PropertyDescriptor pDescriptor[] = PropertyUtils.getPropertyDescriptors(bean);
		final List list = new ArrayList();
		for (PropertyDescriptor propertyDescriptor : pDescriptor) {
			if (propertyDescriptor.getReadMethod() == null
					|| propertyDescriptor.getWriteMethod() == null) {
				continue;
			}

			list.add(propertyDescriptor.getName());
		}
		return list;
	}

	/**
	 * 
	 * @param url
	 * @return
	 * @throws JspException
	 */
	public static String urlEncode(final String url) throws JspException {
		try {
			if (url == null) {
				return null;
			}

			return URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new JspException(e);
		}
	}

	/**
	 * 
	 * @param string
	 * @param chars
	 * @return
	 */
	public static String clearChars(final String string, final String chars) {
		String newString = string;
		for (int i = 0; i < chars.length(); i++) {
			newString = StringUtils.replace(newString, String.valueOf(chars.charAt(i)), "");
		}
		return newString;
	}

	/**
	 * 
	 * @param pageContext
	 * @param key
	 * @param value
	 * @param scope
	 * @return
	 */
	public static Object put(final PageContext pageContext, final String key, final Object value,
			final Integer scope) {
		pageContext.setAttribute(key, value, scope);
		return value;
	}

	/**
	 * 
	 * @param string
	 * @param begin
	 * @param end
	 * @param replace
	 * @return
	 */
	public static String replaceSequence(final String string, final String begin, final String end,
			final String replace) {
		String aux = "";
		String name = string;
		while (name.indexOf(begin) >= 0 && name.indexOf(end) >= 0) {
			aux = aux.concat(name.substring(0, name.indexOf(begin)));
			aux = aux.concat(replace);
			name = name.substring(name.indexOf(end) + 1);
		}
		return aux.concat(name);
	}

	/**
	 * 
	 * @param pageContext
	 * @param property
	 * @return
	 * @throws JspException
	 */
	public static Object getProperty(final PageContext pageContext, final String property)
			throws JspException {
		String baseName = "entity.";
		final VulpeBaseDetailConfig detailConfig = (VulpeBaseDetailConfig) eval(pageContext,
				"${targetConfig}");
		if (detailConfig != null) {
			baseName = eval(pageContext, "${targetConfigPropertyName}").toString().concat("_item");
		}
		return eval(pageContext, "${".concat(
				(property.contains("entity.") || property.contains("entities")
						|| property.contains("].") ? property : baseName.concat(property))).concat(
				"}"));
	}

	/**
	 * This method takes in an image as a byte array (currently supports GIF,
	 * JPG, PNG and possibly other formats) and resizes it to have a width no
	 * greater than the pMaxWidth parameter in pixels. It converts the image to
	 * a standard quality JPG and returns the byte array of that JPG image.
	 * 
	 * @param imageData
	 *            the image data.
	 * @param maxWidth
	 *            the max width in pixels, 0 means do not scale.
	 * @return the resized JPG image.
	 * @throws IOException
	 *             if the image could not be manipulated correctly.
	 */
	public static byte[] resizeImageAsJPG(final byte[] imageData, final int maxWidth)
			throws IOException {
		// Create an ImageIcon from the image data
		final ImageIcon imageIcon = new ImageIcon(imageData);
		int width = imageIcon.getIconWidth();
		if (width == maxWidth) {
			return imageData;
		}
		int height = imageIcon.getIconHeight();
		LOG.debug("imageIcon width: " + width + "  height: " + height);
		// If the image is larger than the max width, we need to resize it
		if (maxWidth > 0 && width > maxWidth) {
			// Determine the shrink ratio
			final double ratio = (double) maxWidth / imageIcon.getIconWidth();
			LOG.debug("resize ratio: " + ratio);
			height = (int) (imageIcon.getIconHeight() * ratio);
			width = maxWidth;
			LOG.debug("imageIcon post scale width: " + width + "  height: " + height);
		}
		// Create a new empty image buffer to "draw" the resized image into
		final BufferedImage bufferedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		// Create a Graphics object to do the "drawing"
		final Graphics2D g2d = bufferedImage.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
		// Draw the resized image
		g2d.drawImage(imageIcon.getImage(), 0, 0, width, height, null);
		g2d.dispose();
		// Now our buffered image is ready
		// Encode it as a JPEG
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(baos);
		encoder.encode(bufferedImage);
		return baos.toByteArray();
	}

	/**
	 * 
	 * @param requestedRoles
	 * @return
	 */
	public static Boolean hasRole(final PageContext pageContext, final String requestedRoles) {
		final Object springSecurity = getSession(pageContext).getAttribute(
				Security.SPRING_SECURITY_CONTEXT);
		boolean has = false;
		if (springSecurity != null) {
			final Object springSecurityAutentication = VulpeReflectUtil.getFieldValue(
					springSecurity, "authentication");
			if (VulpeValidationUtil.isNotEmpty(springSecurityAutentication)) {
				final Collection<?> authorities = VulpeReflectUtil.getFieldValue(
						springSecurityAutentication, "authorities");
				final String[] roles = requestedRoles.split(",");
				for (final String role : roles) {
					final String fullRole = role.startsWith(Security.ROLE_PREFIX) ? role
							: Security.ROLE_PREFIX + role;
					if (VulpeValidationUtil.isNotEmpty(authorities)) {
						for (Object grantedAuthority : authorities) {
							final String authority = VulpeReflectUtil.getFieldValue(
									grantedAuthority, "authority");
							if (authority.equals(fullRole)) {
								has = true;
								break;
							}
						}
					}
				}
			}
		}
		return has;
	}

	/**
	 * 
	 * @param button
	 * @param controllerType
	 * @return
	 */
	public static String buttonConfig(final PageContext pageContext, final String button,
			final String controllerType) {
		final VulpeHashMap<String, Object> now = (VulpeHashMap<String, Object>) pageContext
				.getRequest().getAttribute(Request.NOW);
		final StringBuilder config = new StringBuilder();
		if (now != null) {
			final VulpeHashMap<String, ButtonConfig> buttons = now.getAuto(Now.BUTTONS);
			if (button != null && buttons.containsKey(button)) {
				final ButtonConfig buttonInfo = buttons.get(button);
				if (buttonInfo.getDisabled() != null && buttonInfo.getDisabled()) {
					config.append("[disabled]");
				} else {
					config.append("[enabled]");
				}
				if (buttonInfo.getRender() != null && buttonInfo.getRender()) {
					config.append("[render]");
				} else {
					config.append("[notRender]");
				}
				if (buttonInfo.getShow() != null && buttonInfo.getShow()) {
					config.append("[show]");
				} else {
					config.append("[hide]");
				}
			} else {
				config.append("[notRender]");
			}
		}
		return config.toString();
	}

	/**
	 * 
	 * @param button
	 * @param controllerType
	 * @return
	 */
	public static Boolean isButtonRender(final PageContext pageContext, final String button,
			final String controllerType) {
		final VulpeHashMap<String, Object> now = (VulpeHashMap<String, Object>) pageContext
				.getRequest().getAttribute(Request.NOW);
		boolean render = false;
		if (now != null) {
			final VulpeHashMap<String, ButtonConfig> buttons = now.getAuto(Now.BUTTONS);
			if (button != null && buttons.containsKey(button)) {
				final ButtonConfig buttonInfo = buttons.get(button);
				render = buttonInfo.getRender() != null && buttonInfo.getRender();
			}
		}
		return render;
	}

	/**
	 * 
	 * @param button
	 * @param controllerType
	 * @return
	 */
	public static Boolean isButtonShow(final PageContext pageContext, final String button,
			final String controllerType) {
		final VulpeHashMap<String, Object> now = (VulpeHashMap<String, Object>) pageContext
				.getRequest().getAttribute(Request.NOW);
		boolean show = false;
		if (now != null) {
			final VulpeHashMap<String, ButtonConfig> buttons = now.getAuto(Now.BUTTONS);
			if (button != null && buttons.containsKey(button)) {
				final ButtonConfig buttonInfo = buttons.get(button);
				show = buttonInfo.getShow() != null && buttonInfo.getShow();
			}
		}
		return show;
	}

	/**
	 * 
	 * @param button
	 * @param controllerType
	 * @return
	 */
	public static Boolean isButtonDisabled(final PageContext pageContext, final String button,
			final String controllerType) {
		final VulpeHashMap<String, Object> now = (VulpeHashMap<String, Object>) pageContext
				.getRequest().getAttribute(Request.NOW);
		boolean disabled = false;
		if (now != null) {
			final VulpeHashMap<String, ButtonConfig> buttons = now.getAuto(Now.BUTTONS);
			if (button != null && buttons.containsKey(button)) {
				final ButtonConfig buttonInfo = buttons.get(button);
				disabled = buttonInfo.getDisabled() != null && buttonInfo.getDisabled();
			}
		}
		return disabled;
	}

	/**
	 * 
	 * @return
	 */
	public static Boolean isAuthenticated(final PageContext pageContext) {
		final Object springSecurity = pageContext.getSession().getAttribute(
				Security.SPRING_SECURITY_CONTEXT);
		if (springSecurity == null) {
			return false;
		}
		final Object springSecurityAutentication = VulpeReflectUtil.getFieldValue(springSecurity,
				"authentication");
		final Boolean authenticated = VulpeReflectUtil.getFieldValue(springSecurityAutentication,
				"authenticated");
		if (authenticated != null && authenticated.booleanValue()) {
			return true;
		}
		return ((HttpServletRequest) pageContext.getRequest()).getUserPrincipal() != null;
	}

	protected static String findText(final String key) {
		return MultipleResourceBundle.getInstance().getString(key);
	}

	/**
	 * 
	 * @param value
	 * @param toValue
	 * @return
	 * @throws JspException
	 */
	public static String booleanTo(final Boolean value, final String toValue) throws JspException {
		final StringTokenizer values = new StringTokenizer(toValue, "|");
		String valueTrue = values.nextToken();
		String valueFalse = values.nextToken();
		char openBrace = "{".charAt(0);
		char closeBrace = "}".charAt(0);
		if (valueTrue.charAt(0) == openBrace
				&& valueTrue.charAt(valueTrue.length() - 1) == closeBrace) {
			valueTrue = findText(valueTrue.substring(1, valueTrue.length() - 1));
		}
		if (valueFalse.charAt(0) == openBrace
				&& valueFalse.charAt(valueFalse.length() - 1) == closeBrace) {
			valueFalse = findText(valueFalse.substring(1, valueFalse.length() - 1));
		}
		if (value) {
			return valueTrue;
		}
		return valueFalse;
	}

	/**
	 * 
	 * @param bean
	 * @param field
	 * @param removeEnumItems
	 * @return
	 * @throws JspException
	 */
	public static List listInField(final Object bean, final String field,
			final String removeEnumItems) throws JspException {
		try {
			if (bean == null) {
				return null;
			}
			final List list = new ArrayList();
			final String[] fieldParts = field.replace(".id", "").split("\\.");
			Class<?> fieldClass = null;
			if (fieldParts.length == 1) {
				fieldClass = VulpeReflectUtil.getFieldClass(bean.getClass(), fieldParts[0]);
			} else {
				int count = 1;
				Class<?> parentClass = bean.getClass();
				for (final String fieldPart : fieldParts) {
					parentClass = VulpeReflectUtil.getFieldClass(parentClass, fieldPart);
					++count;
					if (count == fieldParts.length) {
						fieldClass = VulpeReflectUtil.getFieldClass(parentClass,
								fieldParts[fieldParts.length - 1]);
					}
				}
			}
			if (fieldClass.isEnum()) {
				int count = 0;
				for (final Object item : fieldClass.getEnumConstants()) {
					if (StringUtils.isNotEmpty(removeEnumItems)) {
						boolean remove = false;
						final String[] items = removeEnumItems.split(",");
						for (final String eItem : items) {
							if ((NumberUtils.isNumber(eItem) && Integer.valueOf(eItem) == count)
									|| item.toString().equals(eItem)) {
								remove = true;
								break;
							}
						}
						++count;
						if (remove) {
							continue;
						}
					}
					final String key = fieldClass.getName().concat(".").concat(item.toString());
					final String value = findText(key);
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
	 * @param fieldValue
	 * @return
	 * @throws JspException
	 */
	public static Object enumInField(final Object bean, final String field, final Object fieldValue)
			throws JspException {
		try {
			if (bean == null) {
				return null;
			}
			String[] fieldParts = field.replace(".id", "").split("\\.");
			Class<?> fieldClass = null;
			if (fieldParts.length == 1) {
				fieldClass = VulpeReflectUtil.getFieldClass(bean.getClass(), fieldParts[0]);
			} else {
				int count = 1;
				Class<?> parentClass = bean.getClass();
				for (final String fieldPart : fieldParts) {
					parentClass = VulpeReflectUtil.getFieldClass(parentClass, fieldPart);
					++count;
					if (count == fieldParts.length) {
						fieldClass = VulpeReflectUtil.getFieldClass(parentClass,
								fieldParts[fieldParts.length - 1]);
					}
				}
			}
			if (fieldClass == null) {
				return null;
			}
			if (fieldClass.isEnum()) {
				String key = null;
				String value = null;
				for (final Object item : fieldClass.getEnumConstants()) {
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
	 * @param type
	 * @param fieldValue
	 * @return
	 * @throws JspException
	 */
	public static String enumListInField(final String type, final Object fieldValue)
			throws JspException {
		try {
			final VulpeHashMap<String, Object> map = VulpeCacheHelper.getInstance().get(
					VulpeConstants.CACHED_ENUMS);
			final List<ValueBean> enumeration = map.getAuto(type);
			final StringBuilder list = new StringBuilder();
			if (fieldValue instanceof Collection<?>) {
				final Collection<?> collection = (Collection<?>) fieldValue;
				for (final Object object : collection) {
					for (final ValueBean value : enumeration) {
						if (value.getId().equals(object.toString())) {
							if (StringUtils.isNotBlank(list.toString())) {
								list.append(", ");
							}
							list.append(findText(value.getValue()));
						}
					}
				}
			} else if (fieldValue instanceof String[]) {
				final String[] collection = (String[]) fieldValue;
				for (final String string : collection) {
					for (final ValueBean value : enumeration) {
						if (value.getId().equals(string)) {
							if (StringUtils.isNotBlank(list.toString())) {
								list.append(", ");
							}
							list.append(findText(value.getValue()));
						}
					}
				}
			} else {
				for (final ValueBean value : enumeration) {
					if (value.getId().equals(fieldValue.toString())) {
						list.append(findText(value.getValue()));
					}
				}
			}
			return list.toString();
		} catch (Exception e) {
			throw new JspException(e);
		}
	}

	public static EverParameter ever(final PageContext pageContext) {
		return EverParameter.getInstance(getSession(pageContext));
	}

	public static HttpSession getSession(final PageContext pageContext) {
		return pageContext.getSession();
	}

}