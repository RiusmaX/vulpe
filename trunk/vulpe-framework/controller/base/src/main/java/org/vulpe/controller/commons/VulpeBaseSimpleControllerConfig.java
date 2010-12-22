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
import org.vulpe.commons.VulpeConstants.View;
import org.vulpe.commons.VulpeConstants.View.Layout;
import org.vulpe.commons.VulpeConstants.View.Logic;
import org.vulpe.commons.VulpeConstants.View.Report;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.commons.helper.VulpeCacheHelper;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.commons.util.VulpeStringUtil;
import org.vulpe.controller.VulpeSimpleController.Operation;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.util.ControllerUtil;
import org.vulpe.model.services.VulpeService;

/**
 * Vulpe Simple Controller Config implementation.
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("serial")
public class VulpeBaseSimpleControllerConfig implements VulpeControllerConfig, Serializable {

	private boolean simple = true;
	private Controller controller;
	private String controllerName;
	private String moduleName;
	private String simpleControllerName;
	private String viewMainPath;
	private String viewSelectPath;
	private String viewSelectItemsPath;
	private String viewPath;
	private String viewItemsPath;
	private String reportFile;
	private String[] subReports;
	private ControllerType controllerType;

	public VulpeBaseSimpleControllerConfig() {
		// default constructor
	}

	public VulpeBaseSimpleControllerConfig(final Class<?> controllerClass) {
		this.controller = VulpeReflectUtil.getAnnotationInClass(Controller.class, controllerClass);
		this.controllerName = getControllerUtil().getCurrentControllerName();
	}

	protected ControllerUtil getControllerUtil() {
		final VulpeCacheHelper cache = VulpeCacheHelper.getInstance();
		return cache.<ControllerUtil> get(ControllerUtil.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#getControllerType()
	 */
	public ControllerType getControllerType() {
		if (this.controllerType == null) {
			this.controllerType = this.controller.type();
		}
		return this.controllerType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#setControllerType(
	 * org.vulpe.controller.commons.VulpeControllerConfig.ControllerType)
	 */
	public void setControllerType(final ControllerType controllerType) {
		this.controllerType = controllerType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getServiceClass()
	 */
	public Class<? extends VulpeService> getServiceClass() {
		return this.controller.serviceClass();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getPageSize()
	 */
	public int getPageSize() {
		final int pageSize = this.controller.select().pageSize();
		final int globalPageSize = VulpeConfigHelper.getProjectConfiguration().view().globalPageSize();
		return pageSize > 0 ? pageSize : globalPageSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#getTabularPageSize()
	 */
	public int getTabularPageSize() {
		return this.controller.tabular().pageSize();
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
		}
		return this.controllerName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getMethod()
	 */
	public String getMethod() {
		return getControllerUtil().getCurrentMethod();
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
		return getControllerName().replace(Logic.SELECT, Logic.REPORT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#getSimpleControllerName
	 * ()
	 */
	public String getSimpleControllerName() {
		this.simpleControllerName = StringUtils.isNotEmpty(getController().viewBaseName()) ? getController()
				.viewBaseName() : "";
		if (StringUtils.isBlank(this.simpleControllerName) && controllerName.contains("/")) {
			final String[] parts = controllerName.split("/");
			this.simpleControllerName = parts[1];
		}
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
		if (getControllerType().equals(ControllerType.FRONTEND) || getControllerType().equals(ControllerType.BACKEND)) {
			formName.append(VulpeStringUtil.lowerCaseFirst(getSimpleControllerName()).concat(type));
		} else if (controllerName.equals(View.AUTHENTICATOR)) {
			formName.append(controllerName);
		} else {
			formName.append(getModuleName().concat(getSimpleControllerName().concat(type)));
		}
		return formName.append(Layout.SUFFIX_FORM).toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getMainFormName()
	 */
	public String getMainFormName() {
		final StringBuilder formName = new StringBuilder();
		final String type = VulpeStringUtil.capitalize(ControllerType.MAIN.name());
		formName.append(getModuleName().concat(getSimpleControllerName().concat(type)));
		return formName.append(Layout.SUFFIX_FORM).toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#getSelectFormName()
	 */
	public String getSelectFormName() {
		final StringBuilder formName = new StringBuilder();
		final String type = VulpeStringUtil.capitalize(ControllerType.SELECT.name());
		formName.append(getModuleName().concat(getSimpleControllerName().concat(type)));
		return formName.append(Layout.SUFFIX_FORM).toString();
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
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#isShowInTabs()
	 */
	public boolean isShowInTabs() {
		return this.controller.showInTabs();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getReportFormat()
	 */
	public String getReportFormat() {
		return this.controller.report().format().getValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#getReportDataSourceName
	 * ()
	 */
	public String getReportDataSourceName() {
		return this.controller.report().dataSourceName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getReportName()
	 */
	public String getReportName() {
		return this.controller.report().name();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#isReportDownload()
	 */
	public boolean isReportDownload() {
		return this.controller.report().forceDownload();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getReportFile()
	 */
	public String getReportFile() {
		if (StringUtils.isBlank(this.reportFile)) {
			this.reportFile = this.controller.report().file();
			if ("".equals(this.reportFile)) {
				this.reportFile = Report.PATH.concat(this.controllerName).concat("/").concat(this.simpleControllerName)
						.concat(Report.JASPER);
			}
		}
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
	 * org.vulpe.controller.commons.VulpeControllerConfig#setControllerName(
	 * java.lang .String)
	 */
	public void setControllerName(final String controllerName) {
		this.controllerName = controllerName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getTitleKey()
	 */
	public String getTitleKey() {
		final StringBuilder titleKey = new StringBuilder();
		titleKey.append(View.LABEL);
		final String controllerKey = getControllerUtil().getCurrentControllerKey();
		if (StringUtils.isNotBlank(getController().viewBaseName())) {
			titleKey.append(controllerKey.substring(0, controllerKey.lastIndexOf(".") + 1)).append(
					getController().viewBaseName());
		} else {
			titleKey.append(controllerKey);
		}

		if (!getControllerType().equals(ControllerType.BACKEND) && !getControllerType().equals(ControllerType.FRONTEND)) {
			titleKey.append(".").append(getControllerType().name().toLowerCase());
		}
		return titleKey.toString();
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
	 * org.vulpe.controller.commons.VulpeControllerConfig#getViewItemsPath()
	 */
	public String getViewItemsPath() {
		return viewItemsPath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getViewPath()
	 */
	public String getViewPath() {
		this.viewPath = Layout.PROTECTED_JSP;
		this.viewItemsPath = Layout.PROTECTED_JSP;
		final String viewBaseName = StringUtils.isNotEmpty(getController().viewBaseName()) ? getController()
				.viewBaseName() : getSimpleControllerName();
		if (getControllerType().equals(ControllerType.BACKEND) || getControllerType().equals(ControllerType.FRONTEND)) {
			this.viewPath += getModuleName().concat("/").concat(viewBaseName).concat("/");
			final String method = getControllerUtil().getCurrentMethod();
			if (!Operation.FRONTEND.getValue().equals(method) && !Operation.BACKEND.getValue().equals(method)) {
				this.viewPath += method;
			} else {
				this.viewPath += viewBaseName;
			}
			this.viewPath += Layout.SUFFIX_JSP;
			if (getControllerType().equals(ControllerType.SELECT)) {
				this.viewItemsPath += this.viewItemsPath
						+ getModuleName().concat("/").concat(viewBaseName).concat("/").concat(viewBaseName).concat(
								Layout.SUFFIX_JSP_SELECT_ITEMS);
			}
		} else {
			this.viewPath += getModuleName().concat("/").concat(viewBaseName).concat("/").concat(viewBaseName);
			if (getControllerType().equals(ControllerType.TWICE)) {
				this.viewMainPath = this.viewPath + Layout.SUFFIX_JSP_MAIN;
				this.viewSelectPath = this.viewPath + Layout.SUFFIX_JSP_SELECT;
				this.viewSelectItemsPath = this.viewItemsPath
						+ getModuleName().concat("/").concat(viewBaseName).concat("/").concat(viewBaseName).concat(
								Layout.SUFFIX_JSP_SELECT_ITEMS);
			}
			if (getControllerType().equals(ControllerType.MAIN)) {
				this.viewPath += Layout.SUFFIX_JSP_MAIN;
			}
			if (getControllerType().equals(ControllerType.TABULAR)) {
				if (getController().tabular().showFilter()) {
					this.viewSelectPath = this.viewPath + Layout.SUFFIX_JSP_SELECT;
				}
				this.viewPath += Layout.SUFFIX_JSP_TABULAR;
			}
			if (getControllerType().equals(ControllerType.SELECT)) {
				this.viewPath += Layout.SUFFIX_JSP_SELECT;
				this.viewItemsPath += getModuleName().concat("/").concat(viewBaseName).concat("/").concat(viewBaseName)
						.concat(Layout.SUFFIX_JSP_SELECT_ITEMS);
			}
			if (getControllerType().equals(ControllerType.REPORT)) {
				this.viewPath += Layout.SUFFIX_JSP_REPORT;
				this.viewItemsPath += getModuleName().concat("/").concat(viewBaseName).concat("/").concat(viewBaseName)
						.concat(Layout.SUFFIX_JSP_REPORT_ITEMS);
			}
		}
		return viewPath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getSubReports()
	 */
	public String[] getSubReports() {
		if (this.subReports == null) {
			this.subReports = this.controller.report().subReports();
			if (this.subReports != null && this.subReports.length > 0) {
				int count = 0;
				for (String subReport : this.subReports) {
					this.subReports[count] = Report.PATH.concat(this.controllerName).concat("/").concat(subReport)
							.concat(Report.JASPER);
					count++;
				}
			}
		}
		return subReports;
	}

	public String getModuleName() {
		if (StringUtils.isBlank(this.moduleName)) {
			final String[] parts = controllerName.split("/");
			this.moduleName = parts[0];
		}
		return this.moduleName;
	}

	public String getMasterTitleKey() {
		return getTitleKey().concat(View.MASTER);
	}

	public void setViewSelectItemsPath(final String viewSelectItemsPath) {
		this.viewSelectItemsPath = viewSelectItemsPath;
	}

	public String getViewSelectItemsPath() {
		return viewSelectItemsPath;
	}

	public void setViewMainPath(final String viewMainPath) {
		this.viewMainPath = viewMainPath;
	}

	public String getViewMainPath() {
		return viewMainPath;
	}

	public void setViewSelectPath(final String viewSelectPath) {
		this.viewSelectPath = viewSelectPath;
	}

	public String getViewSelectPath() {
		return viewSelectPath;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#isTabularShowFilter()
	 */
	@Override
	public boolean isTabularShowFilter() {
		return getController().tabular().showFilter();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getViewBaseName()
	 */
	public String getViewBaseName() {
		return getController().viewBaseName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#requireOneOfFilters()
	 */
	public String[] requireOneOfFilters() {
		return getController().select().requireOneOfFilters();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#isRequireOneFilter()
	 */
	public boolean isRequireOneFilter() {
		return getController().select().requireOneFilter();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#isOnlyUpdateDetails()
	 */
	public boolean isOnlyUpdateDetails() {
		return getController().onlyUpdateDetails();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#isNewOnPost()
	 */
	public boolean isNewOnPost() {
		return getController().newOnPost();
	}
}