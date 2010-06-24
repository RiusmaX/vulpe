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
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.swing.ImageIcon;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;
import org.vulpe.controller.commons.MultipleResourceBundle;
import org.vulpe.controller.commons.VulpeBaseDetailConfig;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
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
		return eval(
				pageContext,
				"${".concat(
						(property.contains("entity.") || property.contains("entities")
								|| property.contains("].") ? property : baseName.concat(property)))
						.concat("}"));
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
	 * @param role
	 * @return
	 */
	public static Boolean isRole(final PageContext pageContext, final String role) {
		return ((HttpServletRequest) pageContext.getRequest()).isUserInRole(role);
	}

	/**
	 * 
	 * @return
	 */
	public static Boolean isLogged(final PageContext pageContext) {
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
}