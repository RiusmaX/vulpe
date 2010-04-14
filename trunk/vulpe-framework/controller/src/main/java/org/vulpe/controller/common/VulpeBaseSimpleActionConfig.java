package org.vulpe.controller.common;

import java.io.Serializable;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.vulpe.common.ReflectUtil;
import org.vulpe.common.Constants.View;
import org.vulpe.common.Constants.View.Layout;
import org.vulpe.common.Constants.View.Logic;
import org.vulpe.common.Constants.View.Report;
import org.vulpe.common.annotations.DetailConfig;
import org.vulpe.controller.annotations.Controller;
import org.vulpe.controller.annotations.Controller.ControllerType;
import org.vulpe.controller.util.ControllerUtil;
import org.vulpe.model.services.Services;

@SuppressWarnings("serial")
public class VulpeBaseSimpleActionConfig implements Serializable {
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

	public VulpeBaseSimpleActionConfig() {
		// default constructor
	}

	public VulpeBaseSimpleActionConfig(final Class<?> classAction) {
		this.controller = ReflectUtil.getInstance().getAnnotationInClass(
				Controller.class, classAction);
		this.actionName = ControllerUtil.getInstance().getCurrentActionName();

		this.actionBaseName = StringUtils.replace(this.actionName,
				Logic.FRONTEND, "");
		this.actionBaseName = StringUtils.replace(this.actionBaseName,
				Logic.CRUD, "");
		this.actionBaseName = StringUtils.replace(this.actionBaseName,
				Logic.SELECTION, "");
		this.actionBaseName = StringUtils.replace(this.actionBaseName,
				Logic.TABULAR, "");
		this.actionBaseName = StringUtils.replace(this.actionBaseName,
				Logic.REPORT, "");

		if (StringUtils.lastIndexOf(actionBaseName, '.') >= 0) {
			this.simpleActionName = actionBaseName.substring(StringUtils
					.lastIndexOf(actionBaseName, '.') + 1);
		} else {
			this.simpleActionName = actionBaseName;
		}

		this.viewPath = Layout.PROTECTED_JSP;
		this.viewItemsPath = Layout.PROTECTED_JSP;
		final String simple = actionName.replace(Layout.MAIN, "");
		final StringTokenizer parts = new StringTokenizer(simple, ".");
		if (getType().equals(ControllerType.FRONTEND)) {
			final String name = parts.nextToken();
			final String module = parts.nextToken();
			this.viewPath += module.concat("/").concat(name).concat("/")
					.concat(name).concat(Layout.JSP);
			if (getType().equals(ControllerType.SELECT)) {
				this.viewItemsPath += this.viewItemsPath
						+ module.concat("/").concat(name).concat("/").concat(
								name).concat(Layout.SUFIX_JSP_SELECT_ITEMS);
			}
		} else {
			final String module = parts.nextToken();
			final String name = parts.nextToken();
			// final String type = parts.nextToken();
			this.viewPath += module.concat("/").concat(name).concat("/")
					.concat(name);
			if (getType().equals(ControllerType.CRUD)) {
				this.viewPath += Layout.SUFIX_JSP_CRUD;
			}
			if (getType().equals(ControllerType.TABULAR)) {
				this.viewPath += Layout.SUFIX_JSP_TABULAR;
			}
			if (getType().equals(ControllerType.SELECT)) {
				this.viewPath += Layout.SUFIX_JSP_SELECT;
				this.viewItemsPath += this.viewItemsPath
						+ module.concat("/").concat(name).concat("/").concat(
								name).concat(Layout.SUFIX_JSP_SELECT_ITEMS);
			}
			if (getType().equals(ControllerType.REPORT)) {
				this.viewPath += Layout.SUFIX_JSP_REPORT;
				this.viewItemsPath += this.viewItemsPath
						+ module.concat("/").concat(name).concat("/").concat(
								name).concat(Layout.SUFIX_JSP_REPORT_ITEMS);
			}
		}
		this.titleKey = View.LABEL.concat(getProjectName()).concat(".").concat(
				actionName);

		this.reportFile = this.controller.report().reportFile();
		if (this.reportFile.equals("")) {
			this.reportFile = Report.PATH.concat(
					StringUtils.replace(this.actionBaseName, ".", "/")).concat(
					"/").concat(this.simpleActionName).concat(Report.JASPER);
		}
		this.subReports = this.controller.report().subReports();
		if (this.subReports != null && this.subReports.length > 0) {
			int count = 0;
			for (String subReport : this.subReports) {
				this.subReports[count] = Report.PATH.concat(
						StringUtils.replace(this.actionBaseName, ".", "/"))
						.concat("/").concat(subReport).concat(Report.JASPER);
				count++;
			}
		}
	}

	public ControllerType getType() {
		return this.controller.controllerType();
	}

	public Class<? extends Services> getServiceClass() {
		return this.controller.serviceClass();
	}

	public int getPageSize() {
		return this.controller.pageSize();
	}

	public String getOwnerAction() {
		if (StringUtils.isNotEmpty(this.controller.ownerAction())) {
			return this.controller.ownerAction();
		} else {
			if (getType().equals(ControllerType.CRUD)) {
				return getActionBaseName().concat(Logic.SELECTION);
			} else if (getType().equals(ControllerType.SELECT)) {
				return getActionBaseName().concat(Logic.CRUD);
			} else {
				return null;
			}
		}
	}

	public String getPrimitiveOwnerAction() {
		return StringUtils.replace(getOwnerAction(), ".", "/");
	}

	public String getMethod() {
		return ControllerUtil.getInstance().getCurrentMethod();
	}

	public String getActionBaseName() {
		return this.actionBaseName;
	}

	public String getActionName() {
		return this.actionName;
	}

	public String getPrimitiveActionName() {
		this.primitiveActionName = StringUtils.replace(getActionName(), ".",
				"/");
		return this.primitiveActionName;
	}

	public String getPrimitiveReportActionName() {
		this.primitiveReportActionName = StringUtils.replace(getActionName(),
				".", "/");
		this.primitiveReportActionName = StringUtils.replace(
				this.primitiveReportActionName, "/select", "/report");
		return this.primitiveReportActionName;
	}

	public String getActionKey() {
		return getProjectName().concat("/").concat(getActionName());
	}

	public String getSimpleActionName() {
		return this.simpleActionName;
	}

	public String getFormName() {
		final String formName = StringUtils.replace(getActionName(),
				Logic.FRONTEND, "");
		return StringUtils.replace(formName, ".", "");
	}

	public DetailConfig[] getDetailsConfig() {
		return this.controller.detailsConfig();
	}

	public boolean isDetailsInTabs() {
		return this.controller.detailsInTabs();
	}

	public String getReportFormat() {
		return this.controller.report().reportFormat();
	}

	public String getReportDataSource() {
		return this.controller.report().reportDataSource();
	}

	public String getReportName() {
		return this.controller.report().reportName();
	}

	public boolean isReportDownload() {
		return this.controller.report().reportDownload();
	}

	public String getReportFile() {
		return this.reportFile;
	}

	public String getParentName(final String detail) {
		final int pos = StringUtils.lastIndexOf(detail, ".");
		return pos == -1 ? detail : StringUtils.substring(detail, 0, pos);
	}

	private String getProjectName() {
		return ControllerUtil.getInstance().getCurrentProject();
	}

	public Controller getController() {
		return controller;
	}

	public void setController(final Controller controller) {
		this.controller = controller;
	}

	public void setActionBaseName(final String actionBaseName) {
		this.actionBaseName = actionBaseName;
	}

	public void setActionName(final String actionName) {
		this.actionName = actionName;
	}

	public void setPrimitiveActionName(final String primitiveActionName) {
		this.primitiveActionName = primitiveActionName;
	}

	public void setSimpleActionName(final String simpleActionName) {
		this.simpleActionName = simpleActionName;
	}

	public void setReportFile(final String reportFile) {
		this.reportFile = reportFile;
	}

	public String getTitleKey() {
		return titleKey;
	}

	public void setTitleKey(final String titleKey) {
		this.titleKey = titleKey;
	}

	public boolean isSimple() {
		return simple;
	}

	public void setSimple(final boolean simple) {
		this.simple = simple;
	}

	public void setViewItemsPath(final String viewItemsPath) {
		this.viewItemsPath = viewItemsPath;
	}

	public String getViewItemsPath() {
		return viewItemsPath;
	}

	public void setViewPath(final String viewPath) {
		this.viewPath = viewPath;
	}

	public String getViewPath() {
		return viewPath;
	}

	public void setPrimitiveReportActionName(String primitiveReportActionName) {
		this.primitiveReportActionName = primitiveReportActionName;
	}

	public void setSubReports(String[] subReports) {
		this.subReports = subReports;
	}

	public String[] getSubReports() {
		return subReports;
	}

}