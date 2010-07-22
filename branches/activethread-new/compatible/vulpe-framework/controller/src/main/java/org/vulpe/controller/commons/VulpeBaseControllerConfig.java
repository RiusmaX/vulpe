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
import org.vulpe.commons.VulpeConstants.View;
import org.vulpe.commons.VulpeConstants.View.Layout;
import org.vulpe.commons.VulpeConstants.View.Logic;
import org.vulpe.commons.VulpeConstants.View.Report;
import org.vulpe.commons.cache.VulpeCacheHelper;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;
import org.vulpe.controller.util.ControllerUtil;
import org.vulpe.model.entity.VulpeBaseEntity;
import org.vulpe.view.tags.Functions;

/**
 * Vulpe Controller Config implementation.
 * 
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings( { "serial", "unchecked" })
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
		this.entityClass = (Class<ENTITY>) VulpeReflectUtil.getInstance().getIndexClass(
				controllerClass, 0);
		this.idClass = (Class<ID>) VulpeReflectUtil.getInstance().getIndexClass(controllerClass, 1);
		this.details = details;
		final VulpeCacheHelper cache = VulpeCacheHelper.getInstance();
		final ControllerUtil controllerUtil = cache.get(ControllerUtil.class);
		setControllerName(controllerUtil.getCurrentControllerName());

		setViewPath(Layout.PROTECTED_JSP);
		setViewItemsPath(Layout.PROTECTED_JSP);
		final String simple = getControllerName().replace(Layout.MAIN, "");
		final String[] parts = simple.split("/");
		if (getControllerType().equals(ControllerType.BACKEND)
				|| getControllerType().equals(ControllerType.FRONTEND)) {
			setControllerBaseName(getControllerName());
			final String module = parts[0];
			final String name = parts[1];
			setModuleName(module);
			setSimpleControllerName(name);
			setViewPath(getViewPath().concat(module.concat("/").concat(name).concat("/")));
			final String method = controllerUtil.getCurrentMethod();
			if (!Action.FRONTEND.equals(method) && !Action.BACKEND.equals(method)) {
				setViewPath(getViewPath().concat(method));
			} else {
				setViewPath(getViewPath().concat(name));
			}
			setViewPath(getViewPath().concat(Layout.SUFFIX_JSP));
			if (getControllerType().equals(ControllerType.SELECT)) {
				setViewItemsPath(getViewItemsPath().concat(
						module.concat("/").concat(name).concat("/").concat(name).concat(
								Layout.SUFFIX_JSP_SELECT_ITEMS)));
			}
		} else {
			final String module = parts[0];
			final String name = parts[1];
			setModuleName(module);
			setSimpleControllerName(name);
			setViewPath(getViewPath().concat(
					module.concat("/").concat(name).concat("/").concat(name)));
			if (getControllerType().equals(ControllerType.CRUD)) {
				setControllerBaseName(getControllerName().replace(Logic.CRUD, ""));
				setViewPath(getViewPath().concat(Layout.SUFFIX_JSP_CRUD));
			}
			if (getControllerType().equals(ControllerType.TABULAR)) {
				setControllerBaseName(getControllerName().replace(Logic.TABULAR, ""));
				setViewPath(getViewPath().concat(Layout.SUFFIX_JSP_TABULAR));
			}
			if (getControllerType().equals(ControllerType.SELECT)) {
				setControllerBaseName(getControllerName().replace(Logic.SELECT, ""));
				setViewPath(getViewPath().concat(Layout.SUFFIX_JSP_SELECT));
				setViewItemsPath(getViewItemsPath().concat(
						module.concat("/").concat(name).concat("/").concat(name).concat(
								Layout.SUFFIX_JSP_SELECT_ITEMS)));
			}
			if (getControllerType().equals(ControllerType.REPORT)) {
				setControllerBaseName(getControllerName().replace(Logic.REPORT, ""));
				setViewPath(getViewPath().concat(Layout.SUFFIX_JSP_REPORT));
				setViewItemsPath(getViewItemsPath().concat(
						module.concat("/").concat(name).concat("/").concat(name).concat(
								Layout.SUFFIX_JSP_REPORT_ITEMS)));
			}
		}
		setTitleKey(View.LABEL.concat(controllerUtil.getCurrentControllerKey()));
		setMasterTitleKey(View.LABEL.concat(controllerUtil.getCurrentControllerKey()).concat(
				View.MASTER));

		setReportFile(getController().report().reportFile());
		if ("".equals(getReportFile())) {
			setReportFile(Report.PATH.concat(getControllerBaseName()).concat("/").concat(
					getSimpleControllerName()).concat(Report.JASPER));
		}
		setSubReports(getController().report().subReports());
		if (getSubReports() != null && getSubReports().length > 0) {
			int count = 0;
			for (String subReport : getSubReports()) {
				getSubReports()[count] = Report.PATH.concat(getControllerBaseName()).concat("/")
						.concat(subReport).concat(Report.JASPER);
				count++;
			}
		}

		if (getController().controllerType().equals(ControllerType.TABULAR)) {
			final int newDetails = getController().tabularNewDetails();
			final int startNewDetails = getController().tabularStartNewDetails();
			final String[] despiseFields = getController().tabularDespiseFields();
			String name = Action.ENTITIES;
			String propertyName = name;
			if (!getController().tabularName().equals("")) {
				name = getController().tabularName();
				propertyName = getController().tabularName();
			}
			if (!getController().tabularPropertyName().equals("")) {
				propertyName = getController().tabularPropertyName();
			}
			this.details.add(new VulpeBaseDetailConfig(name, propertyName, startNewDetails,
					newDetails, despiseFields));
		}
	}

	public List<VulpeBaseDetailConfig> getDetails() {
		return this.details;
	}

	public VulpeBaseDetailConfig getTabularConfig() {
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