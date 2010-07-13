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
package org.vulpe.controller.commons;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.vulpe.commons.VulpeReflectUtil;
import org.vulpe.commons.VulpeConstants.Action;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.model.entity.VulpeBaseEntity;
import org.vulpe.view.tags.Functions;

/**
 * Vulpe Controller Config implementation.
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings( { "serial", "unchecked", "rawtypes" })
public class VulpeBaseControllerConfig<ENTITY extends VulpeBaseEntity<ID>, ID extends Serializable & Comparable>
		extends VulpeBaseSimpleControllerConfig implements Serializable {
	private final List<VulpeBaseDetailConfig> details;
	private final Class<ID> idClass;
	private final Class<ENTITY> entityClass;

	public VulpeBaseControllerConfig(final Class<?> controllerClass,
			final List<VulpeBaseDetailConfig> details) {
		setSimple(false);
		setController(VulpeReflectUtil.getInstance().getAnnotationInClass(Controller.class,
				controllerClass));
		setControllerName(getControllerUtil().getCurrentControllerName());
		this.entityClass = (Class<ENTITY>) VulpeReflectUtil.getInstance().getIndexClass(
				controllerClass, 0);
		this.idClass = (Class<ID>) VulpeReflectUtil.getInstance().getIndexClass(controllerClass, 1);
		this.details = details;
	}

	public List<VulpeBaseDetailConfig> getDetails() {
		if (!getControllerType().equals(ControllerType.TABULAR)
				&& getController().detailsConfig().length == 0) {
			this.details.clear();
		}
		return this.details;
	}

	public VulpeBaseDetailConfig getTabularConfig() {
		if (getControllerType().equals(ControllerType.TABULAR)
				&& (this.details == null || this.details.isEmpty())) {
			final int newDetails = getController().tabularNewDetails();
			final int startNewDetails = getController().tabularStartNewDetails();
			final String[] despiseFields = getController().tabularDespiseFields();
			String name = Action.ENTITIES;
			String propertyName = name;
			if (StringUtils.isNotBlank(getController().tabularName())) {
				name = getController().tabularName();
				propertyName = getController().tabularName();
			}
			if (StringUtils.isNotBlank(getController().tabularPropertyName())) {
				propertyName = getController().tabularPropertyName();
			}
			this.details.add(new VulpeBaseDetailConfig(name, propertyName, startNewDetails,
					newDetails, despiseFields));
		}
		return getDetail(Action.ENTITIES);
	}

	public Class<ENTITY> getEntityClass() {
		return this.entityClass;
	}

	public Class<ID> getIdClass() {
		return this.idClass;
	}

	public VulpeBaseDetailConfig getDetail(final String name) {
		for (VulpeBaseDetailConfig detail : details) {
			if (detail.getName().equals(name)) {
				return detail;
			}
		}
		return null;
	}

	public VulpeBaseDetailConfig getDetailConfig(final String detail) {
		VulpeBaseDetailConfig detailConfig = getDetail(detail);
		if (detailConfig != null) {
			return detailConfig;
		}

		final String name = Functions.clearChars(Functions.replaceSequence(detail, "[", "]", ""),
				".");
		detailConfig = getDetail(name);
		if (detailConfig != null) {
			return detailConfig;
		}

		String propertyName = detail;
		if (StringUtils.lastIndexOf(detail, '.') >= 0) {
			propertyName = detail.substring(StringUtils.lastIndexOf(detail, '.') + 1);
		}
		return getDetail(propertyName);
	}

}