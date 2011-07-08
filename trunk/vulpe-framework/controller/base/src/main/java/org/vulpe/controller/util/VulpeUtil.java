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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.VulpeConstants.Controller;
import org.vulpe.commons.VulpeConstants.Code.Generator;
import org.vulpe.commons.VulpeConstants.Configuration.Ever;
import org.vulpe.commons.VulpeConstants.Configuration.Now;
import org.vulpe.commons.VulpeConstants.View.Layout;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.commons.beans.ButtonConfig;
import org.vulpe.commons.beans.Tab;
import org.vulpe.commons.helper.VulpeCacheHelper;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.commons.util.VulpeHashMap;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.commons.util.VulpeValidationUtil;
import org.vulpe.controller.AbstractVulpeBaseController;
import org.vulpe.controller.commons.DuplicatedBean;
import org.vulpe.controller.commons.VulpeBaseControllerConfig;
import org.vulpe.controller.commons.VulpeBaseDetailConfig;
import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.model.entity.VulpeEntity;
import org.vulpe.view.annotations.View;

/**
 * Utility class to configuration stuff.
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("unchecked")
public class VulpeUtil<ENTITY extends VulpeEntity<ID>, ID extends Serializable & Comparable> {

	protected static final Logger LOG = Logger.getLogger(VulpeUtil.class);

	private AbstractVulpeBaseController<ENTITY, ID> baseController;

	private VulpeControllerUtil controller;

	public VulpeControllerUtil controller() {
		return controller;
	}

	private VulpeViewUtil view;

	public VulpeViewUtil view() {
		return view;
	}

	private VulpeCacheUtil cache;

	public VulpeCacheUtil cache() {
		return cache;
	}

	public VulpeUtil(final AbstractVulpeBaseController<ENTITY, ID> baseController) {
		this.baseController = baseController;
		this.controller = new VulpeControllerUtil();
		this.view = new VulpeViewUtil();
		this.cache = new VulpeCacheUtil();
	}

	public class VulpeControllerUtil {

		/**
		 * Retrieves controller type
		 * 
		 * @return Controller Type
		 */
		public ControllerType type() {
			final ControllerType type = config().getControllerType();
			baseController.now.put(Now.CONTROLLER_TYPE, type);
			return type;
		}

		public void type(ControllerType controllerType) {
			baseController.now.put(Now.CONTROLLER_TYPE, controllerType);
			config().setControllerType(controllerType);
		}

		public String currentName() {
			String base = "";
			final Component component = baseController.getClass().getAnnotation(Component.class);
			if (component != null) {
				base = component.value().replaceAll("\\.", "/").replace(
						Generator.CONTROLLER_SUFFIX, "");
			}
			return base;
		}

		public String currentKey() {
			return VulpeConfigHelper.getProjectName().concat(".").concat(
					currentName().replace("/", "."));
		}

		public String key() {
			String key = currentKey();
			if (StringUtils.isNotEmpty(config().getViewBaseName())) {
				key = key.substring(0, key.lastIndexOf(".") + 1) + config().getViewBaseName();
			}
			return key;
		}

		public String selectFormKey() {
			return key() + Controller.SELECT_FORM;
		}

		public String selectTableKey() {
			return key() + Controller.SELECT_TABLE;
		}

		public String selectPagingKey() {
			return key() + Controller.SELECT_PAGING;
		}

		public String currentMethodName() {
			return (String) baseController.now.getSelf(Now.CURRENT_METHOD_NAME);
		}

		public void currentMethodName(final String methodName) {
			baseController.now.put(Now.CURRENT_METHOD_NAME, methodName);
		}

		/**
		 * Returns current detail configuration.
		 * 
		 * @since 1.0
		 * @return
		 */
		public VulpeBaseDetailConfig detailConfig() {
			return config().getDetailConfig(baseController.getDetail());
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
									final Object value = VulpeReflectUtil.getFieldValue(object,
											part);
									if (VulpeValidationUtil.isNotEmpty(value)) {
										empty = false;
									}
								}
								return empty;
							} else {
								final Object value = VulpeReflectUtil.getFieldValue(partBean, part);
								if (VulpeValidationUtil.isEmpty(value)) {
									return true;
								}
							}
						} else {
							partBean = VulpeReflectUtil.getFieldValue(partBean == null ? bean
									: partBean, part);
						}
						++count;
					}
				} else {
					final Object value = VulpeReflectUtil.getFieldValue(bean, fieldName);
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
		public boolean duplicatedItem(final Collection<VulpeEntity<?>> beans,
				final VulpeEntity<?> bean, final String[] fieldNames,
				final Collection<DuplicatedBean> duplicatedBeans) {
			int items = 0;
			for (final String fieldName : fieldNames) {
				final Object value = VulpeReflectUtil.getFieldValue(bean, fieldName);
				if (value != null && StringUtils.isNotBlank(value.toString())) {
					int count = 0;
					for (VulpeEntity<?> realBean : beans) {
						final Object valueRealBean = VulpeReflectUtil.getFieldValue(realBean,
								fieldName);
						if (((realBean.getId() != null && realBean.getId().equals(bean.getId())) || (realBean
								.getId() == null && valueRealBean.equals(value)))
								&& count == 0) {
							++count;
							continue;
						}
						if (valueRealBean != null
								&& StringUtils.isNotBlank(valueRealBean.toString())
								&& valueRealBean.equals(value)) {
							++items;
						}
					}
				}
			}
			return (items > 0);
		}

		/**
		 * Checks if exists details for despise.
		 * 
		 * @param ignoreExclud
		 *            (true = add on list [tabular cases], false = remove of
		 *            list) indicate if marked items must be removed or ignored
		 *            on model layer.
		 */
		public List<VulpeEntity<?>> despiseItens(final Collection<VulpeEntity<?>> beans,
				final String despiseFields[], final boolean ignoreExclud) {
			if (beans == null) {
				return null;
			}
			final List<VulpeEntity<?>> excluded = new ArrayList<VulpeEntity<?>>();
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
							excluded.add(entity);
							iterator.remove();
							continue;
						}
					}
				}

				if (despiseItem(bean, despiseFields)) {
					iterator.remove();
				}
			}
			return excluded;
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

			int row = 1;
			for (final VulpeEntity<?> bean : beans) {
				if (bean == null) {
					continue;
				}

				if (duplicatedItem(beans, bean, despiseFields, duplicatedBeans)) {
					duplicatedBeans.add(new DuplicatedBean(bean, row));
				}
				++row;
			}
			return duplicatedBeans;
		}

		/**
		 * 
		 * @param controller
		 * @return
		 */
		public VulpeBaseControllerConfig<ENTITY, ID> config() {
			final String key = currentKey();
			if (baseController.ever.containsKey(key)) {
				final VulpeBaseControllerConfig<ENTITY, ID> config = baseController.ever
						.getSelf(key);
				config.setController(baseController);
				return config;
			}

			final List<VulpeBaseDetailConfig> details = new ArrayList<VulpeBaseDetailConfig>();
			final VulpeBaseControllerConfig<ENTITY, ID> config = new VulpeBaseControllerConfig<ENTITY, ID>(
					baseController, details);
			baseController.ever.put(key, config);

			int count = 0;
			for (final DetailConfig detail : config.getDetailsConfig()) {
				if (!details.contains(detail)) {
					details.add(new VulpeBaseDetailConfig());
				}
				final VulpeBaseDetailConfig detailConfig = details.get(count);
				config.setControllerType(ControllerType.MAIN);
				detailConfig.setupDetail(config, detail);
				++count;
			}
			baseController.now.put(Now.CONTROLLER_CONFIG, config);
			return config;
		}

	}

	public class VulpeViewUtil {

		private VulpeViewContentUtil content;

		public VulpeViewUtil() {
			content = new VulpeViewContentUtil();
			noCache();
			final View view = this.getClass().getAnnotation(View.class);
			if (view != null) {
				focusToField(view.focusToField());
			}
		}

		public void maxInactiveInterval(final int maxInactiveInterval) {
			baseController.ever.put(Ever.MAX_INACTIVE_INTERVAL, maxInactiveInterval);
		}

		public String targetName() {
			return baseController.now.getSelf(Now.TARGET_NAME);
		}

		public void targetName(final String targetName) {
			baseController.now.put(Now.TARGET_NAME, targetName);
		}

		public VulpeBaseDetailConfig targetConfig() {
			return baseController.now.getSelf(Now.TARGET_CONFIG);
		}

		public void targetConfig(final VulpeBaseDetailConfig config) {
			baseController.now.put(Now.TARGET_CONFIG, config);
		}

		public String targetConfigPropertyName() {
			return baseController.now.getSelf(Now.TARGET_CONFIG_PROPERTY_NAME);
		}

		public void targetConfigPropertyName(final String propertyName) {
			baseController.now.put(Now.TARGET_CONFIG_PROPERTY_NAME, propertyName);
		}

		public VulpeViewUtil requireOneFilter() {
			baseController.now.put(Now.REQUIRE_ONE_FILTER, true);
			return this;
		}

		public VulpeViewUtil focusToField(final String field) {
			baseController.now.put(Now.FOCUS_TO_FIELD, field);
			return this;
		}

		public VulpeViewUtil formName(final String formName) {
			baseController.now.put(Now.FORM_NAME, formName);
			return this;
		}

		public void bodyTwice(final ControllerType controllerType) {
			baseController.now.put(Layout.BODY_TWICE, true);
			baseController.now.put(Layout.BODY_TWICE_TYPE, controllerType);
		}
		
		public VulpeViewUtil noCache() {
			baseController.now.put(Now.NO_CACHE, Math.random() * Math.random());
			return this;
		}

		public VulpeHashMap<String, Tab> tabs() {
			if (baseController.now.containsKey(Controller.TABS)) {
				return (VulpeHashMap<String, Tab>) baseController.now.getSelf(Controller.TABS);
			}
			final VulpeHashMap<String, Tab> tabs = new VulpeHashMap<String, Tab>();
			baseController.now.put(Controller.TABS, tabs);
			return tabs;
		}

		public VulpeHashMap<String, ButtonConfig> buttons() {
			if (baseController.now.containsKey(Now.BUTTONS)) {
				return (VulpeHashMap<String, ButtonConfig>) baseController.now.getSelf(Now.BUTTONS);
			}
			final VulpeHashMap<String, ButtonConfig> buttons = new VulpeHashMap<String, ButtonConfig>();
			baseController.now.put(Now.BUTTONS, buttons);
			return buttons;
		}

		public class VulpeViewContentUtil {

			public VulpeViewContentUtil title(String title) {
				baseController.now.put(Now.SHOW_CONTENT_TITLE, true);
				baseController.now.put(Now.CONTENT_TITLE, title);
				return this;
			}

			public VulpeViewContentUtil subtitle(String subtitle) {
				baseController.now.put(Now.SHOW_CONTENT_SUBTITLE, true);
				baseController.now.put(Now.CONTENT_SUBTITLE, subtitle);
				return this;
			}

			public VulpeViewContentUtil titleKey(String titleKey) {
				baseController.now.put(Now.SHOW_CONTENT_TITLE, true);
				baseController.now.put(Now.TITLE_KEY, titleKey);
				return this;
			}

			public VulpeViewContentUtil masterTitleKey(String masterTitleKey) {
				baseController.now.put(Now.MASTER_TITLE_KEY, masterTitleKey);
				return this;
			}

			public VulpeViewContentUtil reportTitleKey(String reportTitleKey) {
				baseController.now.put(Now.REPORT_TITLE_KEY, reportTitleKey);
				return this;
			}
		}

		public VulpeViewContentUtil content() {
			return this.content;
		}
	}

	public class VulpeCacheUtil {

		public VulpeHashMap<String, Object> classes() {
			return VulpeCacheHelper.getInstance().get(VulpeConstants.CACHED_CLASSES);
		}

		public VulpeHashMap<String, Object> enums() {
			return VulpeCacheHelper.getInstance().get(VulpeConstants.CACHED_ENUMS);
		}

		public VulpeHashMap<String, Object> enumsArray() {
			return VulpeCacheHelper.getInstance().get(VulpeConstants.CACHED_ENUMS_ARRAY);
		}

		/**
		 * 
		 * @param entityClass
		 * @param id
		 * @return
		 */
		public <T extends VulpeEntity<?>> T findClass(final Class<T> entityClass, final Long id) {
			final List<T> entities = classes().getSelf(entityClass.getSimpleName());
			for (final T entity : entities) {
				if (entity.getId().equals(id)) {
					return entity;
				}
			}
			return null;
		}

	}

}
