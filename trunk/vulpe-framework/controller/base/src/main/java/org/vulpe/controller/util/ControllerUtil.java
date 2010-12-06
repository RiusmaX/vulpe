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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.VulpeContext;
import org.vulpe.commons.VulpeConstants.View;
import org.vulpe.commons.VulpeConstants.View.Logic;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.commons.helper.VulpeCacheHelper;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.controller.VulpeController;
import org.vulpe.controller.VulpeSimpleController;
import org.vulpe.controller.VulpeSimpleController.Operation;
import org.vulpe.controller.commons.DuplicatedBean;
import org.vulpe.controller.commons.VulpeBaseControllerConfig;
import org.vulpe.controller.commons.VulpeBaseDetailConfig;
import org.vulpe.controller.commons.VulpeBaseSimpleControllerConfig;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.model.entity.VulpeEntity;

/**
 * Utility class to controller
 *
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 *
 */
@SuppressWarnings("unchecked")
public class ControllerUtil {

	private static final Logger LOG = Logger.getLogger(ControllerUtil.class);

	private final VulpeContext vulpeContext = VulpeContext.getInstance();

	/**
	 * Returns instance of ControllerUtil
	 */
	public static ControllerUtil getInstance() {
		final VulpeCacheHelper cache = VulpeCacheHelper.getInstance();
		ControllerUtil controllerUtil = cache.get(ControllerUtil.class);
		if (controllerUtil == null) {
			controllerUtil = new ControllerUtil();
			cache.put(ControllerUtil.class, controllerUtil);
		}
		return controllerUtil;
	}

	/**
	 * Checks if detail must be despised
	 *
	 * @return returns true if despised
	 */
	public boolean despiseItem(final Object bean, final String[] fieldNames) {
		for (String fieldName : fieldNames) {
			final String[] fieldParts = fieldName.split("\\.");
			if (fieldParts != null && fieldParts.length > 1) {
				int count = 1;
				Object partBean = null;
				for (String part : fieldParts) {
					if (count == fieldParts.length) {
						if (partBean instanceof Collection) {
							final Collection<Object> objects = (Collection<Object>) partBean;
							boolean empty = true;
							for (Object object : objects) {
								final Object value = VulpeReflectUtil.getInstance().getFieldValue(object, part);
								if (VulpeValidationUtil.isNotEmpty(value)) {
									empty = false;
								}
							}
							return empty;
						} else {
							final Object value = VulpeReflectUtil.getInstance().getFieldValue(partBean, part);
							if (VulpeValidationUtil.isEmpty(value)) {
								return true;
							}
						}
					} else {
						partBean = VulpeReflectUtil.getInstance().getFieldValue(partBean == null ? bean : partBean,
								part);
					}
					++count;
				}
			} else {
				final Object value = VulpeReflectUtil.getInstance().getFieldValue(bean, fieldName);
				if (VulpeValidationUtil.isEmpty(value)) {
					return true;
				}
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
	public boolean duplicatedItem(final Collection<VulpeEntity<?>> beans, final VulpeEntity<?> bean,
			final String[] fieldNames, final Collection<DuplicatedBean> duplicatedBeans) {
		int items = 0;
		for (String fieldName : fieldNames) {
			final Object value = VulpeReflectUtil.getInstance().getFieldValue(bean, fieldName);
			if (value != null && StringUtils.isNotBlank(value.toString())) {
				for (VulpeEntity<?> realBean : beans) {
					if (realBean.getId() != null && realBean.getId().equals(bean.getId())) {
						continue;
					}
					final Object valueRealBean = VulpeReflectUtil.getInstance().getFieldValue(realBean, fieldName);
					if (valueRealBean != null && StringUtils.isNotBlank(valueRealBean.toString())
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
	public void despiseItens(final Collection<VulpeEntity<?>> beans, final String despiseFields[],
			final boolean ignoreExclud) {
		if (beans == null) {
			return;
		}

		for (final Iterator<VulpeEntity<?>> iterator = beans.iterator(); iterator.hasNext();) {
			final VulpeEntity<?> bean = iterator.next();
			if (bean == null) {
				iterator.remove();
				continue;
			}

			if (bean instanceof VulpeEntity) {
				final VulpeEntity<?> entity = (VulpeEntity<?>) bean;
				// if item is selected to be delete, then ignore
				if (entity.isSelected()) {
					if (!ignoreExclud || entity.getId() == null) {
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
	public Collection<DuplicatedBean> duplicatedItens(final Collection<VulpeEntity<?>> beans,
			final String despiseFields[]) {
		final Collection<DuplicatedBean> duplicatedBeans = new ArrayList<DuplicatedBean>();
		if (beans == null) {
			return null;
		}

		int line = 1;
		for (VulpeEntity<?> bean : beans) {
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
		return VulpeConfigHelper.getProjectName().concat(".").concat(getCurrentControllerName().replace("/", "."));
	}

	/**
	 *
	 * @return
	 */
	public String getCurrentControllerName() {
		String base = vulpeContext.getCurrentController();
		if (StringUtils.isEmpty(base)) {
			return StringUtils.isEmpty(getCurrentController().get()) ? "" : getCurrentController().get();
		}
		base = base.replace("/" + VulpeConfigHelper.getProjectName() + "/", "");
		if (base.startsWith("/")) {
			base = base.substring(1);
		}
		base = base.replace(Logic.AJAX, "");
		getCurrentControllerURI().set(base);
		final String last = base.substring(StringUtils.lastIndexOf(base, '/') + 1);
		if (NumberUtils.isNumber(last)) {
			base = base.substring(0, StringUtils.lastIndexOf(base, '/'));
		}
		if (!base.contains(Logic.BACKEND) && !base.contains(Logic.FRONTEND) && !base.contains(View.AUTHENTICATOR)) {
			final String[] parts = base.split("/");
			base = parts[0] + "/" + parts[1];
		}
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
					method = Operation.BACKEND.getValue();
				} else if (base.contains(Logic.FRONTEND)) {
					method = Operation.FRONTEND.getValue();
				}
			} else if (base.equals(View.AUTHENTICATOR)) {
				method = Operation.DEFINE.getValue();
			} else {
				int last = parts.length - 1;
				if (NumberUtils.isNumber(parts[last])) {
					--last;
				}
				method = parts[last];
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
	public VulpeBaseControllerConfig getControllerConfig(final VulpeController controller) {
		final String key = getCurrentControllerKey();
		if (VulpeCacheHelper.getInstance().contains(key)) {
			return VulpeCacheHelper.getInstance().get(key);
		}

		final List<VulpeBaseDetailConfig> details = new ArrayList<VulpeBaseDetailConfig>();
		final VulpeBaseControllerConfig config = new VulpeBaseControllerConfig(controller.getClass(), details);
		VulpeCacheHelper.getInstance().put(key, config);

		int count = 0;
		for (DetailConfig detail : config.getDetailsConfig()) {
			if (!details.contains(detail)) {
				details.add(new VulpeBaseDetailConfig());
			}
			final VulpeBaseDetailConfig detailConfig = details.get(count);
			config.setControllerType(ControllerType.MAIN);
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
	public VulpeBaseSimpleControllerConfig getControllerConfig(final VulpeSimpleController controller) {
		final String key = getCurrentControllerKey();
		if (VulpeCacheHelper.getInstance().contains(key)) {
			return VulpeCacheHelper.getInstance().get(key);
		}

		final VulpeBaseSimpleControllerConfig config = new VulpeBaseSimpleControllerConfig(controller.getClass());
		VulpeCacheHelper.getInstance().put(key, config);

		return config;
	}

	private transient final ThreadLocal<String> currentController = new ThreadLocal<String>();

	private transient final ThreadLocal<String> currentControllerURI = new ThreadLocal<String>();

	/**
	 *
	 * @return
	 */
	public static ServletContext getServletContext() {
		return VulpeCacheHelper.getInstance().get(VulpeConstants.CURRENT_SERVLET_CONTEXT);
	}

	public ThreadLocal<String> getCurrentController() {
		return currentController;
	}

	public ThreadLocal<String> getCurrentControllerURI() {
		return currentControllerURI;
	}

}