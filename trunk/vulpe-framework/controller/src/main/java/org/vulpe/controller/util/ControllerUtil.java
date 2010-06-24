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
package org.vulpe.controller.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.vulpe.commons.VulpeReflectUtil;
import org.vulpe.commons.VulpeValidationUtil;
import org.vulpe.commons.VulpeConstants.Action;
import org.vulpe.commons.VulpeConstants.View;
import org.vulpe.commons.VulpeConstants.View.Layout;
import org.vulpe.commons.VulpeConstants.View.Logic;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.commons.cache.VulpeCacheHelper;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.controller.VulpeBaseController;
import org.vulpe.controller.VulpeBaseSimpleController;
import org.vulpe.controller.commons.DuplicatedBean;
import org.vulpe.controller.commons.VulpeBaseControllerConfig;
import org.vulpe.controller.commons.VulpeBaseDetailConfig;
import org.vulpe.controller.commons.VulpeBaseSimpleControllerConfig;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.model.entity.VulpeBaseEntity;

/**
 * Utility class to controller
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 *
 */
public class ControllerUtil {

	private static final Logger LOG = Logger.getLogger(ControllerUtil.class);

	private HttpServletRequest request;

	/**
	 * Returns instance of ControllerUtil
	 */
	public static ControllerUtil getInstance(HttpServletRequest request) {
		final VulpeCacheHelper cache = VulpeCacheHelper.getInstance();
		ControllerUtil controllerUtil = cache.get(ControllerUtil.class);
		if (controllerUtil == null) {
			controllerUtil = new ControllerUtil();
			cache.put(ControllerUtil.class, controllerUtil);
		}
		controllerUtil.setRequest(request);
		return controllerUtil;
	}

	/**
	 * Checks if detail must be despised
	 *
	 * @return returns true if despised
	 */
	public boolean despiseItem(final Object bean, final String[] fieldNames) {
		for (String fieldName : fieldNames) {
			final Object value = VulpeReflectUtil.getInstance().getFieldValue(
					bean, fieldName);
			if (VulpeValidationUtil.getInstance().isEmpty(value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks for duplicated detail
	 *
	 * @param beans
	 * @param bean
	 * @param fieldName
	 * @param duplicatedBeans
	 * @return if duplicated, returns true
	 */
	public boolean duplicatedItem(final Collection<VulpeBaseEntity<?>> beans,
			final VulpeBaseEntity<?> bean, final String[] fieldNames,
			final Collection<DuplicatedBean> duplicatedBeans) {
		int items = 0;
		for (String fieldName : fieldNames) {
			final Object value = VulpeReflectUtil.getInstance().getFieldValue(
					bean, fieldName);
			if (StringUtils.isNotBlank(value.toString())) {
				for (VulpeBaseEntity<?> realBean : beans) {
					final Object valueRealBean = VulpeReflectUtil.getInstance()
							.getFieldValue(realBean, fieldName);
					if (StringUtils.isNotBlank(valueRealBean.toString())
							&& valueRealBean.equals(value)) {
						items++;
					}
				}
			}
		}
		return (items > 1);
	}

	/**
	 * Checks if exists details for despise.
	 *
	 * @param ignoreExclud
	 *            (true = add on list [tabular cases], false = remove of list)
	 *            indicate if marked items must be removed or ignored on model
	 *            layer.
	 */
	@SuppressWarnings("unchecked")
	public void despiseItens(final Collection<VulpeBaseEntity<?>> beans,
			final String despiseFields[], final boolean ignoreExclud) {
		if (beans == null) {
			return;
		}

		for (final Iterator<VulpeBaseEntity<?>> iterator = beans.iterator(); iterator
				.hasNext();) {
			final VulpeBaseEntity<?> bean = iterator.next();
			if (bean == null) {
				iterator.remove();
				continue;
			}

			if (bean instanceof VulpeBaseEntity) {
				final VulpeBaseEntity<?> entity = (VulpeBaseEntity<?>) bean;
				// if item is selected to be delete, then ignore
				if (entity.isSelected()) {
					if (ignoreExclud && entity.getId() != null) {
						beans.add(bean);
					} else {
						iterator.remove();
						continue;
					}
				}
			}

			if (despiseItem(bean, despiseFields)) {
				iterator.remove();
			}
		}
	}

	/**
	 * Checks if exists duplicated details.
	 *
	 * @param beans
	 * @param despiseFields
	 * @return Collection of duplicated beans
	 */
	public Collection<DuplicatedBean> duplicatedItens(
			final Collection<VulpeBaseEntity<?>> beans,
			final String despiseFields[]) {
		final Collection<DuplicatedBean> duplicatedBeans = new ArrayList<DuplicatedBean>();
		if (beans == null) {
			return null;
		}

		int line = 1;
		for (VulpeBaseEntity<?> bean : beans) {
			if (bean == null) {
				continue;
			}

			if (duplicatedItem(beans, bean, despiseFields, duplicatedBeans)) {
				duplicatedBeans.add(new DuplicatedBean(bean, line));
			}
			line++;
		}
		return duplicatedBeans;
	}

	/**
	 *
	 * @return
	 */
	public String getCurrentControllerKey() {
		return VulpeConfigHelper.getProjectName().concat(".").concat(
				getCurrentControllerName().replace("/", "."));
	}

	/**
	 *
	 * @return
	 */
	public String getCurrentControllerName() {
		String base = getRequest().getRequestURI();
		if (base.endsWith(Layout.SUFFIX_JSP)) {
			return getCurrentController().get();
		}
		base = base.replace("/" + VulpeConfigHelper.getProjectName() + "/", "");
		base = base.replace(Logic.AJAX, "");
		getCurrentControllerURI().set(base);
		base = (base.contains(Logic.BACKEND) || base.contains(Logic.FRONTEND) || base
				.contains(View.AUTHENTICATOR)) ? base : base.substring(0,
				StringUtils.lastIndexOf(base, '/'));
		getCurrentController().set(base);
		return base;
	}

	public String getCurrentMethod() {
		String method = null;
		try {
			String base = getCurrentControllerURI().get();
			if (base.startsWith("/")) {
				base = base.substring(1);
			}
			final String[] parts = base.split("/");
			if (parts.length == 2) {
				if (base.contains(Logic.BACKEND)) {
					method = Action.BACKEND;
				} else if (base.contains(Logic.FRONTEND)) {
					method = Action.FRONTEND;
				}
			} else if (base.equals(View.AUTHENTICATOR)) {
				method = Action.DEFINE;
			} else {
				method = parts[parts.length - 1];
			}
		} catch (Exception e) {
			LOG.error(e);
		}
		return method;
	}

	/**
	 *
	 * @param controller
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public VulpeBaseControllerConfig getControllerConfig(
			final VulpeBaseController controller) {
		if (VulpeCacheHelper.getInstance().contains(getCurrentControllerKey())) {
			return VulpeCacheHelper.getInstance()
					.get(getCurrentControllerKey());
		}

		final List<VulpeBaseDetailConfig> details = new ArrayList<VulpeBaseDetailConfig>();
		final VulpeBaseControllerConfig config = new VulpeBaseControllerConfig(
				controller.getClass(), details);
		VulpeCacheHelper.getInstance().put(getCurrentControllerKey(), config);

		int count = 0;
		for (DetailConfig detail : config.getDetailsConfig()) {
			if (!details.contains(detail)) {
				details.add(new VulpeBaseDetailConfig());
			}
			final VulpeBaseDetailConfig detailConfig = details.get(count);
			config.setControllerType(ControllerType.CRUD);
			detailConfig.setupDetail(config, detail);
			count++;
		}
		return config;
	}

	/**
	 *
	 * @param controller
	 * @return
	 */
	public VulpeBaseSimpleControllerConfig getControllerConfig(
			final VulpeBaseSimpleController controller) {
		if (VulpeCacheHelper.getInstance().contains(getCurrentControllerKey())) {
			return VulpeCacheHelper.getInstance()
					.get(getCurrentControllerKey());
		}

		final VulpeBaseSimpleControllerConfig config = new VulpeBaseSimpleControllerConfig(
				controller.getClass());
		VulpeCacheHelper.getInstance().put(getCurrentControllerKey(), config);

		return config;
	}

	private transient final ThreadLocal<String> currentController = new ThreadLocal<String>();

	private transient final ThreadLocal<String> currentControllerURI = new ThreadLocal<String>();

	/**
	 *
	 */
	private transient static final ThreadLocal<ServletContext> CURRENT_SERVLET_CONTEXT = new ThreadLocal<ServletContext>();

	/**
	 *
	 * @return
	 */
	public static ServletContext getServletContext() {
		return CURRENT_SERVLET_CONTEXT.get();
	}

	/**
	 *
	 * @param servletContext
	 */
	public static void setServletContext(final ServletContext servletContext) {
		CURRENT_SERVLET_CONTEXT.set(servletContext);
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public ThreadLocal<String> getCurrentController() {
		return currentController;
	}

	public ThreadLocal<String> getCurrentControllerURI() {
		return currentControllerURI;
	}

}