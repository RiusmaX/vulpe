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

import org.apache.commons.lang.StringUtils;
import org.vulpe.commons.VulpeReflectUtil;
import org.vulpe.commons.VulpeStringUtil;
import org.vulpe.commons.VulpeConstants.Action;
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
 * Vulpe Simple Controller Config implementation.
 * 
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("serial")
public class VulpeBaseSimpleControllerConfig implements VulpeControllerConfig, Serializable {

	private boolean simple = true;
	private Controller controller;
	private String controllerBaseName;
	private String controllerName;
	private String moduleName;
	private String simpleControllerName;
	private String viewPath;
	private String viewItemsPath;
	private String titleKey;
	private String reportFile;
	private String[] subReports;

	public VulpeBaseSimpleControllerConfig() {
		// default constructor
	}

	public VulpeBaseSimpleControllerConfig(final Class<?> controllerClass) {
		this.controller = VulpeReflectUtil.getInstance().getAnnotationInClass(Controller.class,
				controllerClass);
		final VulpeCacheHelper cache = VulpeCacheHelper.getInstance();
		final ControllerUtil controllerUtil = cache.get(ControllerUtil.class);
		this.controllerName = controllerUtil.getCurrentControllerName();
		this.viewPath = Layout.PROTECTED_JSP;
		this.viewItemsPath = Layout.PROTECTED_JSP;
		final String simple = controllerName.replace(Layout.MAIN, "");
		final String[] parts = simple.split("/");
		if (getControllerType().equals(ControllerType.BACKEND)
				|| getControllerType().equals(ControllerType.FRONTEND)) {
			this.controllerBaseName = this.controllerName;
			final String module = parts[0];
			final String name = parts[1];
			this.moduleName = module;
			this.simpleControllerName = name;
			this.viewPath += module.concat("/").concat(name).concat("/");
			final String method = controllerUtil.getCurrentMethod();
			if (!Action.FRONTEND.equals(method) && !Action.BACKEND.equals(method)) {
				this.viewPath += method;
			} else {
				this.viewPath += name;
			}
			this.viewPath += Layout.SUFFIX_JSP;
			if (getControllerType().equals(ControllerType.SELECT)) {
				this.viewItemsPath += this.viewItemsPath
						+ module.concat("/").concat(name).concat("/").concat(name).concat(
								Layout.SUFFIX_JSP_SELECT_ITEMS);
			}
		} else {
			final String module = parts[0];
			final String name = parts[1];
			this.moduleName = module;
			this.simpleControllerName = name;
			this.viewPath += module.concat("/").concat(name).concat("/").concat(name);
			if (getControllerType().equals(ControllerType.CRUD)) {
				this.controllerBaseName = this.controllerName.replace(Logic.CRUD, "");
				this.viewPath += Layout.SUFFIX_JSP_CRUD;
			}
			if (getControllerType().equals(ControllerType.TABULAR)) {
				this.controllerBaseName = this.controllerName.replace(Logic.TABULAR, "");
				this.viewPath += Layout.SUFFIX_JSP_TABULAR;
			}
			if (getControllerType().equals(ControllerType.SELECT)) {
				this.controllerBaseName = this.controllerName.replace(Logic.SELECT, "");
				this.viewPath += Layout.SUFFIX_JSP_SELECT;
				this.viewItemsPath += this.viewItemsPath
						+ module.concat("/").concat(name).concat("/").concat(name).concat(
								Layout.SUFFIX_JSP_SELECT_ITEMS);
			}
			if (getControllerType().equals(ControllerType.REPORT)) {
				this.controllerBaseName = this.controllerName.replace(Logic.REPORT, "");
				this.viewPath += Layout.SUFFIX_JSP_REPORT;
				this.viewItemsPath += this.viewItemsPath
						+ module.concat("/").concat(name).concat("/").concat(name).concat(
								Layout.SUFFIX_JSP_REPORT_ITEMS);
			}
		}
		this.titleKey = View.LABEL.concat(getProjectName()).concat(".").concat(
				controllerName.replace("/", "."));

		this.reportFile = this.controller.report().reportFile();
		if ("".equals(this.reportFile)) {
			this.reportFile = Report.PATH.concat(this.controllerBaseName).concat("/").concat(
					this.simpleControllerName).concat(Report.JASPER);
		}
		this.subReports = this.controller.report().subReports();
		if (this.subReports != null && this.subReports.length > 0) {
			int count = 0;
			for (String subReport : this.subReports) {
				this.subReports[count] = Report.PATH.concat(this.controllerBaseName).concat("/")
						.concat(subReport).concat(Report.JASPER);
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
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#getOwnerController()
	 */
	public String getOwnerController() {
		if (StringUtils.isNotEmpty(this.controller.ownerController())) {
			return this.controller.ownerController();
		} else {
			if (getControllerType().equals(ControllerType.CRUD)) {
				return getControllerBaseName().concat(Logic.SELECT);
			} else if (getControllerType().equals(ControllerType.SELECT)) {
				return getControllerBaseName().concat(Logic.CRUD);
			} else {
				return null;
			}
		}
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
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#getControllerBaseName
	 * ()
	 */
	public String getControllerBaseName() {
		return this.controllerBaseName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#getControllerName()
	 */
	public String getControllerName() {
		return this.controllerName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#getReportControllerName
	 * ()
	 */
	public String getReportControllerName() {
		return getControllerName().replace("/select", "/report");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#getControllerKey()
	 */
	public String getControllerKey() {
		return getProjectName().concat(".").concat(getControllerName().replace("/", "."));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#getSimpleControllerName
	 * ()
	 */
	public String getSimpleControllerName() {
		return this.simpleControllerName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getFormName()
	 */
	public String getFormName() {
		final StringBuilder formName = new StringBuilder();
		final String type = VulpeStringUtil.capitalize(getControllerType().name());
		if (getControllerType().equals(ControllerType.FRONTEND)
				|| getControllerType().equals(ControllerType.BACKEND)) {
			formName.append(VulpeStringUtil.lowerCaseFirst(getSimpleControllerName()).concat(type));
		} else {
			formName.append(getModuleName().concat(getSimpleControllerName().concat(type)));
		}
		return formName.append("Form").toString();
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
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#getReportDataSource()
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
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#isReportDownload()
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
	 * org.vulpe.controller.commons.VulpeControllerConfig#getParentName(java
	 * .lang .String)
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
	 * org.vulpe.controller.commons.VulpeControllerConfig#setControllerBaseName
	 * (java .lang.String)
	 */
	public void setControllerBaseName(final String actionBaseName) {
		this.controllerBaseName = actionBaseName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#setControllerName(
	 * java.lang .String)
	 */
	public void setControllerName(final String controllerName) {
		this.controllerName = controllerName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#setSimpleControllerName
	 * (java .lang.String)
	 */
	public void setSimpleControllerName(final String simpleControllerName) {
		this.simpleControllerName = simpleControllerName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#setReportFile(java
	 * .lang .String)
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
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#setSimple(boolean)
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
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#getViewItemsPath()
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
	 * org.vulpe.controller.commons.VulpeControllerConfig#setSubReports(java
	 * .lang .String[])
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

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleName() {
		return moduleName;
	}

}