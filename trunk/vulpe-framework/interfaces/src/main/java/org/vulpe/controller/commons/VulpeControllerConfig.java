package org.vulpe.controller.commons;

import org.vulpe.model.services.VulpeService;

/**
 * Vulpe Controller Config Interface.
 *
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 */
public interface VulpeControllerConfig {

	ControllerType getControllerType();

	void setControllerType(final ControllerType controllerType);

	Class<? extends VulpeService> getServiceClass();

	int getPageSize();

	boolean isRequireOneFilter();

	String[] requireOneOfFilters();

	int getTabularPageSize();

	boolean isTabularShowFilter();

	String getOwnerController();

	String getMethod();

	String getControllerName();

	String getSimpleControllerName();

	String getFormName();

	String getSelectFormName();

	String getCRUDFormName();

	boolean isDetailsInTabs();

	String getReportFormat();

	String getReportDataSourceName();

	String getReportName();

	boolean isReportDownload();

	String getReportFile();

	String getParentName(final String detail);

	void setControllerName(final String actionName);

	String getTitleKey();

	boolean isSimple();

	void setSimple(final boolean simple);

	String getViewItemsPath();

	String getViewPath();

	String[] getSubReports();

	String getReportControllerName();

	String getModuleName();

	String getMasterTitleKey();

	void setViewSelectItemsPath(final String viewSelectItemsPath);

	String getViewSelectItemsPath();

	void setViewCRUDPath(final String viewCRUDPath);

	String getViewCRUDPath();

	void setViewSelectPath(final String viewSelectPath);

	String getViewSelectPath();

	String getViewBaseName();

	/**
	 * Controllers type
	 *
	 * @author <a href="mailto:fabio.viana@vulpe.org">F�bio Viana</a>
	 */
	public enum ControllerType {
		CRUD, TWICE, TABULAR, SELECT, REPORT, BACKEND, FRONTEND, OTHER, ALL, NONE
	}
}
