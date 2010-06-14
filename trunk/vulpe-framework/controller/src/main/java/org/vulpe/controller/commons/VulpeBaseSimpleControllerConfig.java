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
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.vulpe.commons.VulpeReflectUtil;
import org.vulpe.commons.VulpeConstants.View;
import org.vulpe.commons.VulpeConstants.View.Layout;
import org.vulpe.commons.VulpeConstants.View.Logic;
import org.vulpe.commons.VulpeConstants.View.Report;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.commons.cache.VulpeCacheHelper;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;
import org.vulpe.controller.util.ControllerUtil;
import org.vulpe.model.services.Services;

/**
 * Simple Vulpe Action Config implementation.
 * 
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("serial")
public class VulpeBaseSimpleControllerConfig implements VulpeControllerConfig, Serializable {

	private boolean simple = true;
	private Controller controller;
	private String actionBaseName;
	private String actionName;
	private String primitiveActionName;
	private String primitiveReportActionName;
	private String simpleActionName;
	private String viewPath;
	private String viewItemsPath;
	private String titleKey;
	private String reportFile;
	private String[] subReports;

	public VulpeBaseSimpleControllerConfig() {
		// default constructor
	}

	public VulpeBaseSimpleControllerConfig(final Class<?> classAction) {
		this.controller = VulpeReflectUtil.getInstance().getAnnotationInClass(Controller.class,
				classAction);
		final VulpeCacheHelper cache = VulpeCacheHelper.getInstance();
		final ControllerUtil controllerUtil = cache.get(ControllerUtil.class);
		this.actionName = controllerUtil.getCurrentActionName();

		this.actionBaseName = StringUtils.replace(this.actionName, Logic.CRUD, "");
		this.actionBaseName = StringUtils.replace(this.actionBaseName, Logic.SELECTION, "");
		this.actionBaseName = StringUtils.replace(this.actionBaseName, Logic.TABULAR, "");
		this.actionBaseName = StringUtils.replace(this.actionBaseName, Logic.REPORT, "");

		if (StringUtils.lastIndexOf(actionBaseName, '.') >= 0) {
			this.simpleActionName = actionBaseName.substring(StringUtils.lastIndexOf(
					actionBaseName, '.') + 1);
		} else {
			this.simpleActionName = actionBaseName;
		}

		this.viewPath = Layout.PROTECTED_JSP;
		this.viewItemsPath = Layout.PROTECTED_JSP;
		final String simple = actionName.replace(Layout.MAIN, "");
		final StringTokenizer parts = new StringTokenizer(simple, ".");
		if (getControllerType().equals(ControllerType.BACKEND)
				|| getControllerType().equals(ControllerType.FRONTEND)) {
			final String module = parts.nextToken();
			final String name = parts.nextToken();
			this.viewPath += module.concat("/").concat(name).concat("/").concat(name).concat(
					Layout.JSP);
			if (getControllerType().equals(ControllerType.SELECT)) {
				this.viewItemsPath += this.viewItemsPath
						+ module.concat("/").concat(name).concat("/").concat(name).concat(
								Layout.SUFFIX_JSP_SELECT_ITEMS);
			}
		} else {
			final String module = parts.nextToken();
			final String name = parts.nextToken();
			this.viewPath += module.concat("/").concat(name).concat("/").concat(name);
			if (getControllerType().equals(ControllerType.CRUD)) {
				this.viewPath += Layout.SUFFIX_JSP_CRUD;
			}
			if (getControllerType().equals(ControllerType.TABULAR)) {
				this.viewPath += Layout.SUFFIX_JSP_TABULAR;
			}
			if (getControllerType().equals(ControllerType.SELECT)) {
				this.viewPath += Layout.SUFFIX_JSP_SELECT;
				this.viewItemsPath += this.viewItemsPath
						+ module.concat("/").concat(name).concat("/").concat(name).concat(
								Layout.SUFFIX_JSP_SELECT_ITEMS);
			}
			if (getControllerType().equals(ControllerType.REPORT)) {
				this.viewPath += Layout.SUFFIX_JSP_REPORT;
				this.viewItemsPath += this.viewItemsPath
						+ module.concat("/").concat(name).concat("/").concat(name).concat(
								Layout.SUFFIX_JSP_REPORT_ITEMS);
			}
		}
		this.titleKey = View.LABEL.concat(getProjectName()).concat(".").concat(actionName);

		this.reportFile = this.controller.report().reportFile();
		if (this.reportFile.equals("")) {
			this.reportFile = Report.PATH
					.concat(StringUtils.replace(this.actionBaseName, ".", "/")).concat("/").concat(
							this.simpleActionName).concat(Report.JASPER);
		}
		this.subReports = this.controller.report().subReports();
		if (this.subReports != null && this.subReports.length > 0) {
			int count = 0;
			for (String subReport : this.subReports) {
				this.subReports[count] = Report.PATH.concat(
						StringUtils.replace(this.actionBaseName, ".", "/")).concat("/").concat(
						subReport).concat(Report.JASPER);
				count++;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getType()
	 */
	public String getType() {
		return this.controller.controllerType().name();
	}

	/**
	 * 
	 * @return
	 */
	public ControllerType getControllerType() {
		return this.controller.controllerType();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getServiceClass()
	 */
	public Class<? extends Services> getServiceClass() {
		return this.controller.serviceClass();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getPageSize()
	 */
	public int getPageSize() {
		return this.controller.pageSize();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getOwnerAction()
	 */
	public String getOwnerAction() {
		if (StringUtils.isNotEmpty(this.controller.ownerAction())) {
			return this.controller.ownerAction();
		} else {
			if (getControllerType().equals(ControllerType.CRUD)) {
				return getActionBaseName().concat(Logic.SELECTION);
			} else if (getControllerType().equals(ControllerType.SELECT)) {
				return getActionBaseName().concat(Logic.CRUD);
			} else {
				return null;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#getPrimitiveOwnerAction()
	 */
	public String getPrimitiveOwnerAction() {
		return StringUtils.replace(getOwnerAction(), ".", "/");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getMethod()
	 */
	public String getMethod() {
		final VulpeCacheHelper cache = VulpeCacheHelper.getInstance();
		final ControllerUtil controllerUtil = cache.get(ControllerUtil.class);
		return controllerUtil.getCurrentMethod();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getActionBaseName()
	 */
	public String getActionBaseName() {
		return this.actionBaseName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getActionName()
	 */
	public String getActionName() {
		return this.actionName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#getPrimitiveActionName()
	 */
	public String getPrimitiveActionName() {
		this.primitiveActionName = StringUtils.replace(getActionName(), ".", "/");
		return this.primitiveActionName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#getPrimitiveReportActionName
	 * ()
	 */
	public String getPrimitiveReportActionName() {
		this.primitiveReportActionName = StringUtils.replace(getActionName(), ".", "/");
		this.primitiveReportActionName = StringUtils.replace(this.primitiveReportActionName,
				"/select", "/report");
		return this.primitiveReportActionName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getActionKey()
	 */
	public String getActionKey() {
		return getProjectName().concat("/").concat(getActionName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getSimpleActionName()
	 */
	public String getSimpleActionName() {
		return this.simpleActionName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getFormName()
	 */
	public String getFormName() {
		String formName = StringUtils.replace(getActionName(), Logic.BACKEND, "");
		formName = StringUtils.replace(getActionName(), Logic.FRONTEND, "");
		return StringUtils.replace(formName, ".", "");
	}

	/**
	 * Retrieves Configuration of Details.
	 * 
	 * @return Array of DetailConfig
	 */
	public DetailConfig[] getDetailsConfig() {
		return this.controller.detailsConfig();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#isDetailsInTabs()
	 */
	public boolean isDetailsInTabs() {
		return this.controller.detailsInTabs();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getReportFormat()
	 */
	public String getReportFormat() {
		return this.controller.report().reportFormat();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getReportDataSource()
	 */
	public String getReportDataSource() {
		return this.controller.report().reportDataSource();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getReportName()
	 */
	public String getReportName() {
		return this.controller.report().reportName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#isReportDownload()
	 */
	public boolean isReportDownload() {
		return this.controller.report().reportDownload();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getReportFile()
	 */
	public String getReportFile() {
		return this.reportFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#getParentName(java.lang
	 * .String)
	 */
	public String getParentName(final String detail) {
		final int position = StringUtils.lastIndexOf(detail, ".");
		return position == -1 ? detail : StringUtils.substring(detail, 0, position);
	}

	/**
	 * Retrieves Name of Project
	 * 
	 * @return Name of Project
	 */
	protected String getProjectName() {
		return VulpeConfigHelper.getProjectName();
	}

	/**
	 * Retrieves Controller Annotation.
	 * 
	 * @return Controller Annotation
	 */
	public Controller getController() {
		return controller;
	}

	/**
	 * Puts Controller Annotation.
	 * 
	 * @param controller
	 */
	public void setController(final Controller controller) {
		this.controller = controller;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#setActionBaseName(java
	 * .lang.String)
	 */
	public void setActionBaseName(final String actionBaseName) {
		this.actionBaseName = actionBaseName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#setActionName(java.lang
	 * .String)
	 */
	public void setActionName(final String actionName) {
		this.actionName = actionName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#setPrimitiveActionName
	 * (java.lang.String)
	 */
	public void setPrimitiveActionName(final String primitiveActionName) {
		this.primitiveActionName = primitiveActionName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#setSimpleActionName(java
	 * .lang.String)
	 */
	public void setSimpleActionName(final String simpleActionName) {
		this.simpleActionName = simpleActionName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#setReportFile(java.lang
	 * .String)
	 */
	public void setReportFile(final String reportFile) {
		this.reportFile = reportFile;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getTitleKey()
	 */
	public String getTitleKey() {
		return titleKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#setTitleKey(java.lang.
	 * String)
	 */
	public void setTitleKey(final String titleKey) {
		this.titleKey = titleKey;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#isSimple()
	 */
	public boolean isSimple() {
		return simple;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#setSimple(boolean)
	 */
	public void setSimple(final boolean simple) {
		this.simple = simple;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#setViewItemsPath(java.
	 * lang.String)
	 */
	public void setViewItemsPath(final String viewItemsPath) {
		this.viewItemsPath = viewItemsPath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getViewItemsPath()
	 */
	public String getViewItemsPath() {
		return viewItemsPath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#setViewPath(java.lang.
	 * String)
	 */
	public void setViewPath(final String viewPath) {
		this.viewPath = viewPath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getViewPath()
	 */
	public String getViewPath() {
		return viewPath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#setPrimitiveReportActionName
	 * (java.lang.String)
	 */
	public void setPrimitiveReportActionName(String primitiveReportActionName) {
		this.primitiveReportActionName = primitiveReportActionName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#setSubReports(java.lang
	 * .String[])
	 */
	public void setSubReports(String[] subReports) {
		this.subReports = subReports;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getSubReports()
	 */
	public String[] getSubReports() {
		return subReports;
	}

}