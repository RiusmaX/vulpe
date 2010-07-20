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
package org.vulpe.controller.vraptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.vulpe.commons.beans.DownloadInfo;
import org.vulpe.commons.file.FileUtil;
import org.vulpe.controller.AbstractVulpeBaseSimpleController;
import org.vulpe.controller.commons.VulpeControllerConfig;
import org.vulpe.controller.util.ControllerUtil;
import org.vulpe.exception.VulpeSystemException;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.core.RequestInfo;

/**
 * Action base for VRaptor
 * 
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings( { "serial" })
public abstract class AbstractVulpeVRaptorSimpleController extends AbstractVulpeBaseSimpleController {

	protected static final Logger LOG = Logger.getLogger(AbstractVulpeVRaptorSimpleController.class);

	@Autowired
	protected RequestInfo requestInfo;
	@Autowired
	protected Result result;
	@Autowired
	protected Validator validator;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.vulpe.controller.VulpeSimpleController#getActionConfig()
	 */
	public VulpeControllerConfig getControllerConfig() {
		return getControllerUtil().getControllerConfig(this);
	}

	public ControllerUtil getControllerUtil() {
		return ControllerUtil.getInstance(getRequest());
	}

	/**
	 * Extension point to read report.
	 * 
	 * @since 1.0
	 */
	protected DownloadInfo doReadReportLoad() {
		try {
			// List<VulpeEntity<?>> list = (List<VulpeEntity<?>>)
			// PropertyUtils.getProperty(
			// this, getActionConfig().getReportDataSource());
			// return StringUtils.isNotBlank(getActionConfig().getReportName())
			// ? StrutsReportUtil
			// .getInstance()
			// .getDownloadInfo(list, getActionConfig().getReportFile(),
			// getActionConfig().getSubReports(),
			// getActionConfig().getReportFormat(),
			// getActionConfig().getReportName(),
			// getActionConfig().isReportDownload())
			// : StrutsReportUtil.getInstance().getDownloadInfo(list,
			// getActionConfig().getReportFile(),
			// getActionConfig().getSubReports(),
			// getActionConfig().getReportFormat());
			return null;
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
	}

	/**
	 * Extension point to prepare download.
	 * 
	 * @since 1.0
	 */
	protected DownloadInfo prepareDownloadInfo() {
		try {
			Object value = null;
			// if (getFormParams() != null &&
			// getFormParams().containsKey(getDownloadKey())) {
			// final Object[] array = (Object[])
			// getFormParams().get(getDownloadKey());
			// value = array[1];
			// }
			if (value == null) {
				// value = ognlUtil.getValue(getDownloadKey(),
				// ActionContext.getContext()
				// .getContextMap(), this);
			}
			final DownloadInfo downloadInfo = FileUtil.getInstance().getDownloadInfo(value,
					getDownloadContentType(), getDownloadContentDisposition());
			if (downloadInfo != null) {
				downloadInfo.setKey(getDownloadKey());
			}
			return downloadInfo;
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
	}

	/**
	 * Retrieves current HTTP Session.
	 * 
	 * @return Http Session
	 */
	public HttpSession getSession() {
		return requestInfo.getRequest().getSession();
	}

	/**
	 * Retrieves current HTTP Request.
	 * 
	 * @return Http Servlet Request
	 */
	public HttpServletRequest getRequest() {
		return requestInfo.getRequest();
	}

	/**
	 * Retrieves current HTTP Response.
	 * 
	 * @return Http Servlet Reponse
	 */
	public HttpServletResponse getResponse() {
		return requestInfo.getResponse();
	}

	public void addActionMessage(final String message) {
		result.include("notice", message);
	}

	public void addActionError(final String message) {
		result.include("notice", message);
	}

	public void addActionError(final String key, final Object... args) {
		result.include("notice", getText(key));
	}

}