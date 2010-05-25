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

import org.apache.commons.lang.StringUtils;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.VulpeReflectUtil;
import org.vulpe.commons.VulpeValidationUtil;
import org.vulpe.commons.cache.VulpeCacheHelper;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.controller.VulpeBaseController;
import org.vulpe.controller.VulpeBaseSimpleController;
import org.vulpe.controller.common.DuplicatedBean;
import org.vulpe.controller.common.VulpeBaseActionConfig;
import org.vulpe.controller.common.VulpeBaseDetailConfig;
import org.vulpe.controller.common.VulpeBaseSimpleActionConfig;
import org.vulpe.model.entity.VulpeBaseEntity;

/**
 * Utility class to controller
 * 
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * 
 */
public abstract class ControllerUtil {

	/**
	 *
	 */
	protected ControllerUtil() {
		// default constructor
	}

	/**
	 * Checks if detail must be despised
	 * 
	 * @return returns true if despised
	 */
	public boolean despiseItem(final Object bean, final String[] fieldNames) {
		for (String fieldName : fieldNames) {
			final Object value = VulpeReflectUtil.getInstance().getFieldValue(bean, fieldName);
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
			final Object value = VulpeReflectUtil.getInstance().getFieldValue(bean, fieldName);
			if (StringUtils.isNotBlank(value.toString())) {
				for (VulpeBaseEntity<?> realBean : beans) {
					final Object valueRealBean = VulpeReflectUtil.getInstance().getFieldValue(realBean,
							fieldName);
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

		for (final Iterator<VulpeBaseEntity<?>> iterator = beans.iterator(); iterator.hasNext();) {
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
	public Collection<DuplicatedBean> duplicatedItens(final Collection<VulpeBaseEntity<?>> beans,
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
	public String getCurrentActionKey() {
		final String base = getCurrentActionName();
		final String projectName = base.contains(VulpeConstants.AUDIT) ? VulpeConstants.ACTIVE
				: VulpeConfigHelper.getProjectName();
		return projectName.concat("/").concat(base);
	}

	public abstract String getCurrentActionName();

	public abstract String getCurrentMethod();

	/**
	 * 
	 * @param controller
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public VulpeBaseActionConfig getActionConfig(final VulpeBaseController controller) {
		if (VulpeCacheHelper.getInstance().contains(getCurrentActionKey())) {
			return VulpeCacheHelper.getInstance().get(getCurrentActionKey());
		}

		final List<VulpeBaseDetailConfig> details = new ArrayList<VulpeBaseDetailConfig>();
		final VulpeBaseActionConfig config = new VulpeBaseActionConfig(controller.getClass(),
				details);
		VulpeCacheHelper.getInstance().put(getCurrentActionKey(), config);

		int count = 0;
		for (DetailConfig detail : config.getDetailsConfig()) {
			if (!details.contains(detail)) {
				details.add(new VulpeBaseDetailConfig());
			}
			final VulpeBaseDetailConfig detailConfig = details.get(count);
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
	public VulpeBaseSimpleActionConfig getActionConfig(final VulpeBaseSimpleController controller) {
		if (VulpeCacheHelper.getInstance().contains(getCurrentActionKey())) {
			return VulpeCacheHelper.getInstance().get(getCurrentActionKey());
		}

		final VulpeBaseSimpleActionConfig config = new VulpeBaseSimpleActionConfig(controller
				.getClass());
		VulpeCacheHelper.getInstance().put(getCurrentActionKey(), config);

		return config;
	}
}