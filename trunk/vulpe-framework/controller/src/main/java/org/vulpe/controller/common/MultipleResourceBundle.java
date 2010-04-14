package org.vulpe.controller.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.servlet.ServletContext;

import org.apache.commons.beanutils.locale.LocaleBeanUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.vulpe.controller.util.ControllerUtil;

import com.opensymphony.xwork2.ActionContext;

/**
 * Class to provide multiple Resource Bundle in application.
 * 
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * @version 1.0
 * @since 1.0
 */
public class MultipleResourceBundle extends ResourceBundle {

	private static final Logger LOG = Logger
			.getLogger(MultipleResourceBundle.class);

	private final static String BUNDLES_KEY = MultipleResourceBundle.class
			.getName().concat(".bundles");

	private ServletContext servletContext;
	private Locale locale;

	/**
	 * Gets all bundles in application
	 * 
	 * @return list of bundles in application
	 */
	@SuppressWarnings("unchecked")
	protected List<ResourceBundle> getBundles() {
		if (servletContext == null) {
			servletContext = ControllerUtil.getInstance().getServletContext();
		}
		final Locale requestLocale = ServletActionContext.getRequest() == null ? ActionContext
				.getContext().getLocale()
				: ServletActionContext.getRequest().getLocale();
		if (locale == null
				|| !locale.getLanguage().equals(requestLocale.getLanguage())) {
			locale = requestLocale;
		}
		List<ResourceBundle> list = null;
		if (servletContext != null) {
			list = (List<ResourceBundle>) servletContext
					.getAttribute(BUNDLES_KEY);
			if (list == null) {
				final String modules[] = servletContext.getInitParameter(
						"project.bundle.modules").split(",");
				list = new ArrayList<ResourceBundle>(modules.length);
				for (String module : modules) {
					final ResourceBundle resourceBundle = ResourceBundle
							.getBundle(module, locale);
					list.add(resourceBundle);
				}
				Collections.reverse(list);
				servletContext.setAttribute(BUNDLES_KEY, list);
			}
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.ResourceBundle#getKeys()
	 */
	@Override
	public Enumeration<String> getKeys() {
		final List<ResourceBundle> list = getBundles();
		if (list != null) {
			final Vector<String> listKeys = new Vector<String>();
			for (ResourceBundle resourceBundle : list) {
				final Enumeration<String> enume = resourceBundle.getKeys();
				while (enume.hasMoreElements()) {
					listKeys.add(enume.nextElement());
				}
			}
			return listKeys.elements();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.ResourceBundle#handleGetObject(java.lang.String)
	 */
	@Override
	protected Object handleGetObject(final String key) {
		final List<ResourceBundle> list = getBundles();
		if (list != null) {
			for (ResourceBundle resourceBundle : list) {
				try {
					final Object value = resourceBundle.getObject(key);
					if (value != null) {
						if (value instanceof String) {
							if (!value.toString().startsWith("???")
									&& !value.toString().endsWith("???")) {
								return value;
							}
						}
						return value;
					}
				} catch (MissingResourceException e) {
					LOG.debug(e);
				}
			}
		}
		return key;
	}

	/**
	 * Method to get key description.
	 * 
	 * @param servletContext
	 * @param key
	 * @return
	 */
	public Object getKeyDescription(final ServletContext servletContext,
			final String key) {
		setServletContext(servletContext);
		setLocale(LocaleBeanUtils.getDefaultLocale());
		return getObject(key);
	}

	/**
	 * 
	 * @return
	 */
	public ServletContext getServletContext() {
		return servletContext;
	}

	/**
	 * 
	 * @param servletContext
	 */
	public void setServletContext(final ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/**
	 * 
	 * @param locale
	 */
	public void setLocale(final Locale locale) {
		this.locale = locale;
	}
}