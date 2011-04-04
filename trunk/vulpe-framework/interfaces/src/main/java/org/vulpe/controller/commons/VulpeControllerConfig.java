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

	String getMainFormName();

	boolean isShowInTabs();

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

	void setViewMainPath(final String viewMainPath);

	String getViewMainPath();

	void setViewSelectPath(final String viewSelectPath);

	String getViewSelectPath();

	String getViewBaseName();
	
	boolean isOnlyUpdateDetails();
	
	boolean isNewOnPost();

	/**
	 * Controllers type
	 *
	 * @author <a href="mailto:fabio.viana@vulpe.org">Fábio Viana</a>
	 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
	 */
	public enum ControllerType {
		MAIN, TWICE, TABULAR, SELECT, REPORT, BACKEND, FRONTEND, OTHER, ALL, NONE
	}
}
