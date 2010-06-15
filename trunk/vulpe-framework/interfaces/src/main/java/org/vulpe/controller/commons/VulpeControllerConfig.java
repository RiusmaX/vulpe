package org.vulpe.controller.commons;

import org.vulpe.model.services.Services;

/**
 * Vulpe Action Config Interface.
 * 
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * @version 1.0
 * @since 1.0
 */
public interface VulpeControllerConfig {

	/**
	 * Retrieves type of controller.
	 * 
	 * @return String of Controller type
	 */
	String getType();

	Class<? extends Services> getServiceClass();

	int getPageSize();

	String getOwnerController();

	String getMethod();

	String getControllerBaseName();

	String getControllerName();

	String getControllerKey();

	String getSimpleControllerName();

	String getFormName();

	boolean isDetailsInTabs();

	String getReportFormat();

	String getReportDataSource();

	String getReportName();

	boolean isReportDownload();

	String getReportFile();

	String getParentName(final String detail);

	void setControllerBaseName(final String controllerBaseName);

	void setControllerName(final String actionName);

	void setSimpleControllerName(final String simpleControllerName);

	void setReportFile(final String reportFile);

	String getTitleKey();

	void setTitleKey(final String titleKey);

	boolean isSimple();

	void setSimple(final boolean simple);

	void setViewItemsPath(final String viewItemsPath);

	String getViewItemsPath();

	void setViewPath(final String viewPath);

	String getViewPath();

	void setSubReports(String[] subReports);

	String[] getSubReports();
	
	String getReportControllerName();
}
