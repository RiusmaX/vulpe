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

	ControllerType getControllerType();

	void setControllerType(final ControllerType controllerType);

	Class<? extends Services> getServiceClass();

	int getPageSize();

	String getOwnerController();

	String getMethod();

	String getControllerName();

	String getSimpleControllerName();

	String getFormName();

	String getSelectFormName();

	String getCRUDFormName();

	boolean isDetailsInTabs();

	String getReportFormat();

	String getReportDataSource();

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

	/**
	 * Controllers type
	 *
	 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
	 */
	public enum ControllerType {
		CRUD, CRUD_SELECT, TABULAR, SELECT, REPORT, BACKEND, FRONTEND, OTHER, ALL, NONE
	}
}
