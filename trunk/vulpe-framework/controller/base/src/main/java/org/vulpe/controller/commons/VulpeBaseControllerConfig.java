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
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.VulpeConstants.View;
import org.vulpe.commons.VulpeConstants.View.Layout;
import org.vulpe.commons.VulpeConstants.View.Logic;
import org.vulpe.commons.VulpeConstants.View.Report;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.commons.util.VulpeStringUtil;
import org.vulpe.controller.AbstractVulpeBaseController;
import org.vulpe.controller.VulpeController;
import org.vulpe.controller.VulpeController.Operation;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.model.entity.VulpeEntity;
import org.vulpe.model.services.VulpeService;
import org.vulpe.view.tags.Functions;

/**
 * Vulpe Controller Config implementation.
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings( { "serial", "unchecked" })
public class VulpeBaseControllerConfig<ENTITY extends VulpeEntity<ID>, ID extends Serializable & Comparable>
		implements VulpeControllerConfig, Serializable {

	private boolean simple = true;
	private VulpeController controller;
	private Controller controllerAnnotation;
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

	private final List<VulpeBaseDetailConfig> details;
	private final Class<ID> idClass;
	private final Class<ENTITY> entityClass;

	/**
	 * 
	 * @param controllerClass
	 * @param details
	 */
	public VulpeBaseControllerConfig(final VulpeController controller,
			final List<VulpeBaseDetailConfig> details) {
		setSimple(false);
		this.controller = controller;
		setControllerAnnotation(VulpeReflectUtil.getAnnotationInClass(Controller.class, controller
				.getClass()));
		this.entityClass = (Class<ENTITY>) VulpeReflectUtil.getIndexClass(controller.getClass(), 0);
		this.idClass = (Class<ID>) VulpeReflectUtil.getIndexClass(controller.getClass(), 1);
		this.details = details;
	}

	/**
	 * 
	 * @return
	 */
	public List<VulpeBaseDetailConfig> getDetails() {
		if (!getControllerType().equals(ControllerType.TABULAR)
				&& getControllerAnnotation().detailsConfig().length == 0) {
			this.details.clear();
		}
		return this.details;
	}

	/**
	 * 
	 * @return
	 */
	public VulpeBaseDetailConfig getTabularConfig() {
		if (getControllerType().equals(ControllerType.TABULAR)
				&& (this.details == null || this.details.isEmpty())) {
			final boolean addNewDetailsOnTop = getControllerAnnotation().tabular()
					.addNewRecordsOnTop();
			final boolean showFilter = getControllerAnnotation().tabular().showFilter();
			final int newDetails = getControllerAnnotation().tabular().newRecords();
			final int startNewDetails = getControllerAnnotation().tabular().startNewRecords();
			final String[] despiseFields = getControllerAnnotation().tabular().despiseFields();
			String name = VulpeConstants.Controller.ENTITIES;
			String propertyName = name;
			if (StringUtils.isNotBlank(getControllerAnnotation().tabular().name())) {
				name = getControllerAnnotation().tabular().name();
			}
			if (StringUtils.isNotBlank(getControllerAnnotation().tabular().propertyName())) {
				propertyName = getControllerAnnotation().tabular().propertyName();
			}
			this.details.add(new VulpeBaseDetailConfig(name, propertyName, startNewDetails,
					newDetails, addNewDetailsOnTop, showFilter, despiseFields));
		}
		return getDetail(VulpeConstants.Controller.ENTITIES);
	}

	/**
	 * 
	 * @return
	 */
	public Class<ENTITY> getEntityClass() {
		return this.entityClass;
	}

	/**
	 * 
	 * @return
	 */
	public Class<ID> getIdClass() {
		return this.idClass;
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public VulpeBaseDetailConfig getDetail(final String name) {
		for (final VulpeBaseDetailConfig detail : details) {
			if (detail.getName().equals(name)) {
				return detail;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param detail
	 * @return
	 */
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#getControllerType()
	 */
	public ControllerType getControllerType() {
		if (this.controllerType == null) {
			this.controllerType = this.controllerAnnotation.type();
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
		return this.controllerAnnotation.serviceClass();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getPageSize()
	 */
	public int getPageSize() {
		final int pageSize = this.controllerAnnotation.select().pageSize();
		final int globalPageSize = VulpeConfigHelper.getProjectConfiguration().view().paging()
				.pageSize();
		return pageSize > 0 ? pageSize : globalPageSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#getTabularPageSize()
	 */
	public int getTabularPageSize() {
		return this.controllerAnnotation.tabular().pageSize();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#getOwnerController()
	 */
	public String getOwnerController() {
		if (StringUtils.isNotEmpty(this.controllerAnnotation.ownerController())) {
			return this.controllerAnnotation.ownerController();
		}
		return getControllerName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#getControllerName()
	 */
	public String getControllerName() {
		if (StringUtils.isBlank(this.controllerName)) {
			this.controllerName = ((AbstractVulpeBaseController) this.controller).vulpe
					.controller().currentName();
		}
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
		this.simpleControllerName = StringUtils
				.isNotEmpty(getControllerAnnotation().viewBaseName()) ? getControllerAnnotation()
				.viewBaseName() : "";
		if (StringUtils.isBlank(this.simpleControllerName) && getControllerName().contains("/")) {
			final String[] parts = getControllerName().split("/");
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
		if (getControllerType().equals(ControllerType.FRONTEND)
				|| getControllerType().equals(ControllerType.BACKEND)) {
			formName.append(VulpeStringUtil.lowerCaseFirst(getSimpleControllerName()).concat(type));
		} else if (getControllerName().equals(View.AUTHENTICATOR)) {
			formName.append(getControllerName());
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
		return this.controllerAnnotation.detailsConfig();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#isShowInTabs()
	 */
	public boolean isShowInTabs() {
		return this.controllerAnnotation.showInTabs();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getReportFormat()
	 */
	public String getReportFormat() {
		return this.controllerAnnotation.report().format().getValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#getReportDataSourceName
	 * ()
	 */
	public String getReportDataSourceName() {
		return this.controllerAnnotation.report().dataSourceName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getReportName()
	 */
	public String getReportName() {
		return StringUtils.isBlank(this.controllerAnnotation.report().name()) ? this.simpleControllerName
				: this.controllerAnnotation.report().name();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#isReportDownload()
	 */
	public boolean isReportDownload() {
		return this.controllerAnnotation.report().forceDownload();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getReportFile()
	 */
	public String getReportFile() {
		if (StringUtils.isBlank(this.reportFile)) {
			this.reportFile = this.controllerAnnotation.report().file();
			if (StringUtils.isBlank(this.reportFile)) {
				this.reportFile = Report.PATH.concat(getControllerName()).concat("/").concat(
						this.simpleControllerName).concat(Report.JASPER);
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
	public Controller getControllerAnnotation() {
		return controllerAnnotation;
	}

	/**
	 * Puts Controller Annotation.
	 * 
	 * @param controller
	 */
	public void setControllerAnnotation(final Controller controllerAnnotation) {
		this.controllerAnnotation = controllerAnnotation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getTitleKey()
	 */
	public String getTitleKey() {
		final StringBuilder titleKey = new StringBuilder();
		titleKey.append(View.LABEL);
		final String controllerKey = ((AbstractVulpeBaseController) getController()).vulpe
				.controller().currentKey();
		if (StringUtils.isNotBlank(getControllerAnnotation().viewBaseName())) {
			titleKey.append(controllerKey.substring(0, controllerKey.lastIndexOf(".") + 1)).append(
					getControllerAnnotation().viewBaseName());
		} else {
			titleKey.append(controllerKey);
		}

		if (!getControllerType().equals(ControllerType.BACKEND)
				&& !getControllerType().equals(ControllerType.FRONTEND)) {
			titleKey.append(".").append(getControllerType().name().toLowerCase());
		}
		return titleKey.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#getReportTitleKey()
	 */
	public String getReportTitleKey() {
		final StringBuilder reportTitleKey = new StringBuilder();
		reportTitleKey.append(View.LABEL);
		final String controllerKey = ((AbstractVulpeBaseController) getController()).vulpe
				.controller().currentKey();
		if (StringUtils.isNotBlank(getControllerAnnotation().viewBaseName())) {
			reportTitleKey.append(controllerKey.substring(0, controllerKey.lastIndexOf(".") + 1))
					.append(getControllerAnnotation().viewBaseName());
		} else {
			reportTitleKey.append(controllerKey);
		}
		reportTitleKey.append(".report");
		return reportTitleKey.toString();
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
		getViewPath();
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
		final String viewBaseName = StringUtils
				.isNotEmpty(getControllerAnnotation().viewBaseName()) ? getControllerAnnotation()
				.viewBaseName() : getSimpleControllerName();
		if (getControllerType().equals(ControllerType.BACKEND)
				|| getControllerType().equals(ControllerType.FRONTEND)) {
			this.viewPath += getModuleName().concat("/").concat(viewBaseName).concat("/");
			final String method = ((AbstractVulpeBaseController) getController()).vulpe
					.controller().currentMethodName();
			if (!Operation.FRONTEND.getValue().equals(method)
					&& !Operation.BACKEND.getValue().equals(method)) {
				this.viewPath += method;
			} else {
				this.viewPath += viewBaseName;
			}
			this.viewPath += Layout.SUFFIX_JSP;
			if (getControllerType().equals(ControllerType.SELECT)) {
				this.viewItemsPath += this.viewItemsPath
						+ getModuleName().concat("/").concat(viewBaseName).concat("/").concat(
								viewBaseName).concat(Layout.SUFFIX_JSP_SELECT_ITEMS);
			}
		} else {
			this.viewPath += getModuleName().concat("/").concat(viewBaseName).concat("/").concat(
					viewBaseName);
			if (getControllerType().equals(ControllerType.TWICE)) {
				this.viewMainPath = this.viewPath + Layout.SUFFIX_JSP_MAIN;
				this.viewSelectPath = this.viewPath + Layout.SUFFIX_JSP_SELECT;
				this.viewSelectItemsPath = this.viewItemsPath
						+ getModuleName().concat("/").concat(viewBaseName).concat("/").concat(
								viewBaseName).concat(Layout.SUFFIX_JSP_SELECT_ITEMS);
			} else if (getControllerType().equals(ControllerType.MAIN)) {
				this.viewPath += Layout.SUFFIX_JSP_MAIN;
			} else if (getControllerType().equals(ControllerType.TABULAR)) {
				if (getControllerAnnotation().tabular().showFilter()) {
					this.viewSelectPath = this.viewPath + Layout.SUFFIX_JSP_SELECT;
				}
				this.viewPath += Layout.SUFFIX_JSP_TABULAR;
			} else if (getControllerType().equals(ControllerType.SELECT)) {
				this.viewPath += Layout.SUFFIX_JSP_SELECT;
				this.viewItemsPath += getModuleName().concat("/").concat(viewBaseName).concat("/")
						.concat(viewBaseName).concat(Layout.SUFFIX_JSP_SELECT_ITEMS);
			} else if (getControllerType().equals(ControllerType.REPORT)) {
				this.viewPath += Layout.SUFFIX_JSP_REPORT;
				this.viewItemsPath += getModuleName().concat("/").concat(viewBaseName).concat("/")
						.concat(viewBaseName).concat(Layout.SUFFIX_JSP_REPORT_ITEMS);
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
			this.subReports = this.controllerAnnotation.report().subReports();
			if (this.subReports != null && this.subReports.length > 0) {
				int count = 0;
				for (String subReport : this.subReports) {
					this.subReports[count] = Report.PATH.concat(getControllerName()).concat("/")
							.concat(subReport).concat(Report.JASPER);
					++count;
				}
			}
		}
		return subReports;
	}

	public String getModuleName() {
		if (StringUtils.isBlank(this.moduleName)) {
			final String[] parts = getControllerName().split("/");
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
		getViewPath();
		return viewSelectItemsPath;
	}

	public void setViewMainPath(final String viewMainPath) {
		this.viewMainPath = viewMainPath;
	}

	public String getViewMainPath() {
		getViewPath();
		return viewMainPath;
	}

	public void setViewSelectPath(final String viewSelectPath) {
		this.viewSelectPath = viewSelectPath;
	}

	public String getViewSelectPath() {
		getViewPath();
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
		return getControllerAnnotation().tabular().showFilter();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#getViewBaseName()
	 */
	public String getViewBaseName() {
		return getControllerAnnotation().viewBaseName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#requireOneOfFilters()
	 */
	public String[] requireOneOfFilters() {
		return getControllerAnnotation().select().requireOneOfFilters();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#isRequireOneFilter()
	 */
	public boolean isRequireOneFilter() {
		return getControllerAnnotation().select().requireOneFilter();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.vulpe.controller.commons.VulpeControllerConfig#isOnlyUpdateDetails()
	 */
	public boolean isOnlyUpdateDetails() {
		return getControllerAnnotation().onlyUpdateDetails();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.commons.VulpeControllerConfig#isNewOnPost()
	 */
	public boolean isNewOnPost() {
		return getControllerAnnotation().newOnPost();
	}

	public void setController(VulpeController controller) {
		this.controller = controller;
	}

	public VulpeController getController() {
		return controller;
	}
}