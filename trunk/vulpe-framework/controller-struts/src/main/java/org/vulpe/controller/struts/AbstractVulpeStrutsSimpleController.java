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
package org.vulpe.controller.struts;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ognl.OgnlException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.beans.DownloadInfo;
import org.vulpe.commons.util.VulpeFileUtil;
import org.vulpe.controller.AbstractVulpeBaseSimpleController;
import org.vulpe.controller.annotations.ResetSession;
import org.vulpe.controller.struts.util.StrutsReportUtil;
import org.vulpe.exception.VulpeSystemException;
import org.vulpe.exception.VulpeValidationException;
import org.vulpe.model.entity.VulpeEntity;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.LocaleProvider;
import com.opensymphony.xwork2.Validateable;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.ValidationAwareSupport;
import com.opensymphony.xwork2.util.OgnlUtil;

/**
 * Action base for Struts2
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings({ "unchecked", "serial" })
public abstract class AbstractVulpeStrutsSimpleController extends AbstractVulpeBaseSimpleController
		implements Action, Validateable, ValidationAware, LocaleProvider, Serializable {

	protected static final Logger LOG = Logger.getLogger(AbstractVulpeStrutsSimpleController.class);
	/**
	 *
	 */
	private final OgnlUtil ognlUtil = new OgnlUtil();

	/**
	 * Extension point to read report.
	 *
	 * @since 1.0
	 */
	protected DownloadInfo doReadReportLoad() {
		try {
			List<VulpeEntity<?>> list = (List<VulpeEntity<?>>) PropertyUtils.getProperty(
					this, getControllerConfig().getReportDataSource());
			return StringUtils.isNotBlank(getControllerConfig().getReportName()) ? StrutsReportUtil
					.getInstance().getDownloadInfo(list, getControllerConfig().getReportFile(),
							getControllerConfig().getSubReports(),
							getControllerConfig().getReportFormat(),
							getControllerConfig().getReportName(),
							getControllerConfig().isReportDownload()) : StrutsReportUtil
					.getInstance().getDownloadInfo(list, getControllerConfig().getReportFile(),
							getControllerConfig().getSubReports(),
							getControllerConfig().getReportFormat());
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
	}

	@SkipValidation
	@ResetSession(before = true)
	@Override
	public String backend() {
		return super.backend();
	}

	@SkipValidation
	@ResetSession(before = true)
	@Override
	public String frontend() {
		return super.frontend();
	}

	@SkipValidation
	@Override
	public String upload() {
		return super.upload();
	}

	@SkipValidation
	@Override
	public String download() {
		return super.download();
	}

	/**
	 * Extension point to prepare download.
	 *
	 * @since 1.0
	 */
	@SuppressWarnings("static-access")
	protected DownloadInfo prepareDownloadInfo() {
		try {
			Object value = null;
			if (getFormParams() != null && getFormParams().containsKey(getDownloadKey())) {
				final Object[] array = (Object[]) getFormParams().get(getDownloadKey());
				value = array[1];
			}
			if (value == null) {
				value = ognlUtil.getValue(getDownloadKey(), ActionContext.getContext()
						.getContextMap(), this);
			}
			final DownloadInfo downloadInfo = VulpeFileUtil.getInstance().getDownloadInfo(value,
					getDownloadContentType(), getDownloadContentDisposition());
			if (downloadInfo != null) {
				downloadInfo.setKey(getDownloadKey());
			}
			return downloadInfo;
		} catch (OgnlException e) {
			throw new VulpeSystemException(e);
		}
	}

	@Override
	public void validate() {
		if (isBack() && !isExecuted()) {
			final Collection messages = getActionMessages();
			clearErrorsAndMessages();
			for (Object object : messages) {
				addActionMessage(object.toString());
			}
		}
		if (hasActionErrors() || hasFieldErrors()) {
			throw new VulpeValidationException();
		}
	}

	/**
	 * Retrieves form parameters
	 *
	 * @return Map with form parameters
	 */
	public Map getFormParams() {
		final String keyForm = getControllerUtil().getCurrentControllerKey().concat(
				VulpeConstants.PARAMS_SESSION_KEY);
		Map formParams = (Map) ServletActionContext.getRequest().getSession().getAttribute(keyForm);
		if (formParams == null) {
			formParams = new HashMap();
			ServletActionContext.getRequest().getSession().setAttribute(keyForm, formParams);
		}
		return formParams;
	}

	/**
	 * Retrieves current HTTP Session.
	 *
	 * @return Http Session
	 */
	public HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	/**
	 * Retrieves current HTTP Request.
	 *
	 * @return Http Servlet Request
	 */
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * Retrieves current HTTP Response.
	 *
	 * @return Http Servlet Reponse
	 */
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	private final ValidationAwareSupport validationAware = new ValidationAwareSupport();

	public void setActionErrors(Collection errorMessages) {
		validationAware.setActionErrors(errorMessages);
	}

	public Collection getActionErrors() {
		return validationAware.getActionErrors();
	}

	public void setActionMessages(Collection messages) {
		validationAware.setActionMessages(messages);
	}

	public Collection getActionMessages() {
		return validationAware.getActionMessages();
	}

	/**
	 * @deprecated Use {@link #getActionErrors()}.
	 */
	public Collection getErrorMessages() {
		return getActionErrors();
	}

	/**
	 * @deprecated Use {@link #getFieldErrors()}.
	 */
	public Map getErrors() {
		return getFieldErrors();
	}

	public void setFieldErrors(Map errorMap) {
		validationAware.setFieldErrors(errorMap);
	}

	public Map getFieldErrors() {
		return validationAware.getFieldErrors();
	}

	public Locale getLocale() {
		return ActionContext.getContext().getLocale();
	}

	public void addActionError(String anErrorMessage) {
		validationAware.addActionError(anErrorMessage);
	}

	public void addActionMessage(String aMessage) {
		validationAware.addActionMessage(aMessage);
	}

	public void addFieldError(String fieldName, String errorMessage) {
		validationAware.addFieldError(fieldName, errorMessage);
	}

	public String input() throws Exception {
		return INPUT;
	}

	public String doDefault() throws Exception {
		return SUCCESS;
	}

	/**
	 * A default implementation that does nothing an returns "success".
	 * <p/>
	 * Subclasses should override this method to provide their business logic.
	 * <p/>
	 * See also {@link com.opensymphony.xwork2.Action#execute()}.
	 *
	 * @return returns {@link #SUCCESS}
	 * @throws Exception
	 *             can be thrown by subclasses.
	 */
	public String execute() throws Exception {
		return SUCCESS;
	}

	public boolean hasActionErrors() {
		return validationAware.hasActionErrors();
	}

	public boolean hasActionMessages() {
		return validationAware.hasActionMessages();
	}

	public boolean hasErrors() {
		return validationAware.hasErrors();
	}

	public boolean hasFieldErrors() {
		return validationAware.hasFieldErrors();
	}

	/**
	 * Clears all errors and messages. Useful for Continuations and other
	 * situations where you might want to clear parts of the state on the same
	 * action.
	 */
	public void clearErrorsAndMessages() {
		validationAware.clearErrorsAndMessages();
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/**
	 * <!-- START SNIPPET: pause-method --> Stops the action invocation
	 * immediately (by throwing a PauseException) and causes the action
	 * invocation to return the specified result, such as {@link #SUCCESS},
	 * {@link #INPUT}, etc.
	 * <p/>
	 *
	 * The next time this action is invoked (and using the same continuation
	 * ID), the method will resume immediately after where this method was
	 * called, with the entire call stack in the execute method restored.
	 * <p/>
	 *
	 * Note: this method can <b>only</b> be called within the {@link #execute()}
	 * method. <!-- END SNIPPET: pause-method -->
	 *
	 * @param result
	 *            the result to return - the same type of return value in the
	 *            {@link #execute()} method.
	 */
	public void pause(String result) {
		// pause
	}

	public void addActionError(final String key, final Object... args) {
		// action error
	}
}