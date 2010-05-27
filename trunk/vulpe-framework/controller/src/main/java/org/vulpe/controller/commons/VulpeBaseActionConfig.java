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
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.vulpe.commons.VulpeReflectUtil;
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

@SuppressWarnings( { "serial", "unchecked" })
public class VulpeBaseActionConfig<ENTITY extends VulpeBaseEntity<ID>, ID extends Serializable & Comparable>
		extends VulpeBaseSimpleActionConfig implements Serializable {
	private final List<VulpeBaseDetailConfig> details;
	private final Class<ID> idClass;
	private final Class<ENTITY> entityClass;

	public VulpeBaseActionConfig(final Class<?> classAction,
			final List<VulpeBaseDetailConfig> details) {
		setSimple(false);
		setController(VulpeReflectUtil.getInstance().getAnnotationInClass(Controller.class,
				classAction));
		this.entityClass = (Class<ENTITY>) VulpeReflectUtil.getInstance().getIndexClass(
				classAction, 0);
		this.idClass = (Class<ID>) VulpeReflectUtil.getInstance().getIndexClass(classAction, 1);
		this.details = details;
		final VulpeCacheHelper cache = VulpeCacheHelper.getInstance();
		final ControllerUtil controllerUtil = cache.get(ControllerUtil.class);
		setActionName(controllerUtil.getCurrentActionName());

		setActionBaseName(StringUtils.replace(getActionName(), Logic.CRUD, ""));
		setActionBaseName(StringUtils.replace(getActionBaseName(), Logic.SELECTION, ""));
		setActionBaseName(StringUtils.replace(getActionBaseName(), Logic.TABULAR, ""));
		setActionBaseName(StringUtils.replace(getActionBaseName(), Logic.REPORT, ""));

		if (StringUtils.lastIndexOf(getActionBaseName(), '.') >= 0) {
			setSimpleActionName(getActionBaseName().substring(
					StringUtils.lastIndexOf(getActionBaseName(), '.') + 1));
		} else {
			setSimpleActionName(getActionBaseName());
		}
		setViewPath(Layout.PROTECTED_JSP);
		setViewItemsPath(Layout.PROTECTED_JSP);
		final String simple = getActionName().replace(".main", "");
		final StringTokenizer parts = new StringTokenizer(simple, ".");
		if (getControllerType().equals(ControllerType.BACKEND)
				|| getControllerType().equals(ControllerType.FRONTEND)) {
			final String module = parts.nextToken();
			final String name = parts.nextToken();
			setViewPath(getViewPath().concat(
					module.concat("/").concat(name).concat("/").concat(name).concat(Layout.JSP)));
			if (getControllerType().equals(ControllerType.SELECT)) {
				setViewItemsPath(getViewItemsPath().concat(
						module.concat("/").concat(name).concat("/").concat(name).concat(
								Layout.SUFFIX_JSP_SELECT_ITEMS)));
			}
		} else {
			final String module = parts.nextToken();
			final String name = parts.nextToken();
			// final String type = parts.nextToken();
			setViewPath(getViewPath().concat(
					module.concat("/").concat(name).concat("/").concat(name)));
			if (getControllerType().equals(ControllerType.CRUD)) {
				setViewPath(getViewPath().concat(Layout.SUFFIX_JSP_CRUD));
			}
			if (getControllerType().equals(ControllerType.TABULAR)) {
				setViewPath(getViewPath().concat(Layout.SUFFIX_JSP_TABULAR));
			}
			if (getControllerType().equals(ControllerType.SELECT)) {
				setViewPath(getViewPath().concat(Layout.SUFFIX_JSP_SELECT));
				setViewItemsPath(getViewItemsPath().concat(
						module.concat("/").concat(name).concat("/").concat(name).concat(
								Layout.SUFFIX_JSP_SELECT_ITEMS)));
			}
			if (getControllerType().equals(ControllerType.REPORT)) {
				setViewPath(getViewPath().concat(Layout.SUFFIX_JSP_REPORT));
				setViewItemsPath(getViewItemsPath().concat(
						module.concat("/").concat(name).concat("/").concat(name).concat(
								Layout.SUFFIX_JSP_REPORT_ITEMS)));
			}
		}

		setTitleKey(View.LABEL.concat(getProjectName()).concat(".").concat(getActionName()));

		setReportFile(getController().report().reportFile());
		if (getReportFile().equals("")) {
			setReportFile(Report.PATH.concat(StringUtils.replace(getActionBaseName(), ".", "/"))
					.concat("/").concat(getSimpleActionName()).concat(Report.JASPER));
		}
		setSubReports(getController().report().subReports());
		if (getSubReports() != null && getSubReports().length > 0) {
			int count = 0;
			for (String subReport : getSubReports()) {
				getSubReports()[count] = Report.PATH.concat(
						StringUtils.replace(getActionBaseName(), ".", "/")).concat("/").concat(
						subReport).concat(Report.JASPER);
				count++;
			}
		}

		if (getController().controllerType().equals(ControllerType.TABULAR)) {
			final int detailNews = getController().tabularDetailNews();
			final String[] despiseFields = getController().tabularDespiseFields();
			String name = "entities";
			String propertyName = name;
			if (!getController().tabularName().equals("")) {
				name = getController().tabularName();
				propertyName = getController().tabularName();
			}
			if (!getController().tabularPropertyName().equals("")) {
				propertyName = getController().tabularPropertyName();
			}
			this.details.add(new VulpeBaseDetailConfig(name, propertyName, detailNews,
					despiseFields));
		}
	}

	public List<VulpeBaseDetailConfig> getDetails() {
		return this.details;
	}

	public VulpeBaseDetailConfig getTabularConfig() {
		return getDetail("entities");
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