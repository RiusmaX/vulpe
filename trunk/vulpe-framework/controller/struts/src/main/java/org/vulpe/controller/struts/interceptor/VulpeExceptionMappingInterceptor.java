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
package org.vulpe.controller.struts.interceptor;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;

import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.VulpeConstants.Context;
import org.vulpe.commons.VulpeConstants.Controller.Result;
import org.vulpe.commons.helper.VulpeCacheHelper;
import org.vulpe.controller.struts.VulpeStrutsController;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.exception.VulpeAuthenticationException;
import org.vulpe.exception.VulpeAuthorizationException;
import org.vulpe.exception.VulpeSystemException;
import org.vulpe.exception.VulpeValidationException;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.TextParseUtil;

/**
 * Interceptor class to control exceptions.
 * 
 * @author <a href="mailto:fabio.viana@vulpe.org">Fábio Viana</a>
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 */
@SuppressWarnings( { "serial", "unchecked" })
public class VulpeExceptionMappingInterceptor extends
		com.opensymphony.xwork2.interceptor.ExceptionMappingInterceptor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.opensymphony.xwork2.interceptor.ExceptionMappingInterceptor#intercept
	 * (com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	public String intercept(final ActionInvocation invocation) throws Exception {
		String result = null;
		try {
			result = super.intercept(invocation);
		} catch (Exception e) {
			// if exception no mapped in struts, then do general handling
			if (invocation.getAction() instanceof VulpeStrutsController) {
				result = findResultFromException(invocation, e);
			} else {
				throw e;
			}
		}
		return result;
	}

	/**
	 * Method responsible for handling exception.
	 * 
	 * @param invocation
	 * @param exception
	 * @return
	 */
	protected String findResultFromException(final ActionInvocation invocation,
			final Throwable exception) {
		final VulpeStrutsController<?, ?> action = (VulpeStrutsController<?, ?>) invocation
				.getAction();
		final HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute(VulpeConstants.IS_EXCEPTION, Boolean.TRUE);
		action.vulpe.controller().resultName(Result.MESSAGES);
		// gets real exception
		final Throwable newException = getException(exception);
		if (newException instanceof VulpeAuthenticationException) {
			action.addActionMessageKey(newException.getMessage());
		} else if (newException instanceof VulpeAuthorizationException) {
			action.addActionMessageKey(newException.getMessage());
		} else if (newException instanceof VulpeSystemException) {
			final VulpeSystemException vse = (VulpeSystemException) newException;
			if (vse.getArgs() != null && vse.getArgs().length > 0) {
				action.addActionMessage(vse.getMessage(), (Object[]) vse.getArgs());
			} else {
				String message = action.vulpe.controller().text("vulpe.error.unknown");
				final String key = vse.getMessage();
				if (key.startsWith("vulpe.error")) {
					if (vse.getCause() != null
							&& StringUtils.isNotEmpty(vse.getCause().getMessage())) {
						if (!key.equals(vse.getCause().getMessage())) {
							message = vse.getCause().getMessage();
						}
						if (message.startsWith("vulpe.error")) {
							message = action.vulpe.controller().text(message);
						}
					}
					action.addActionError(key, message);
				} else {
					if (vse.getCause() != null
							&& StringUtils.isNotEmpty(vse.getCause().getMessage())) {
						message = vse.getCause().getMessage();
						if (message.startsWith("vulpe.error")) {
							message = action.vulpe.controller().text(message);
						}
					}
					action.addActionMessage(key, message);
				}
			}
		} else if (newException instanceof VulpeApplicationException) {
			final VulpeApplicationException vae = (VulpeApplicationException) newException;
			action.addActionMessage(newException.getMessage(), (Object[]) vae.getArgs());
		} else {
			String value = treatExceptionMessage(invocation, newException);
			if (StringUtils.isBlank(value)) {
				String message = newException.getMessage();
				if (StringUtils.isNotEmpty(message)) {
					final MessageFormat messageFormat = buildMessageFormat(TextParseUtil
							.translateVariables(message, invocation.getStack()), invocation
							.getInvocationContext().getLocale());
					message = messageFormat.format(null);
					value = action.vulpe.controller().text(message);
					if (StringUtils.isBlank(value) || value.equals(message)) {
						translateException(invocation, newException);
					} else {
						action.addActionMessage(value);
					}
				} else {
					action.addActionMessage(VulpeConstants.GENERAL_ERROR, value);
				}
			} else {
				action.addActionMessage(value);
			}
		}
		if (!action.vulpe.controller().ajax()) {
			request.setAttribute(VulpeConstants.VULPE_SHOW_MESSAGES, true);
		}
		if (isDebug(action)) {
			exception.printStackTrace();
		}
		return action.vulpe.controller().resultName();
	}

	/**
	 * Checks if debug is enabled.
	 * 
	 * @param action
	 * @return
	 */
	protected boolean isDebug(final VulpeStrutsController<?, ?> action) {
		final Boolean sessionDebug = action.ever.getSelf(VulpeConstants.Configuration.Ever.DEBUG,
				Boolean.FALSE);
		final Map<String, Object> global = (Map<String, Object>) getServletContext().getAttribute(
				Context.GLOBAL);
		Boolean globalDebug = (Boolean) global
				.get(VulpeConstants.Configuration.Global.PROJECT_DEBUG);
		if (globalDebug == null) {
			globalDebug = Boolean.FALSE;
		}
		return sessionDebug || globalDebug;
	}

	/**
	 * 
	 * @param exception
	 * @return
	 */
	protected Throwable getException(final Throwable exception) {
		final Throwable newException = exception;
		return newException.getCause() instanceof InvocationTargetException ? getException(((InvocationTargetException) newException
				.getCause()).getTargetException())
				: newException;
	}

	/**
	 * 
	 * @param exception
	 * @return
	 */
	protected Throwable getCause(final Throwable exception) {
		return exception.getCause() != null ? getCause(exception.getCause()) : exception;
	}

	/**
	 * 
	 * @param pattern
	 * @param locale
	 * @return
	 */
	protected MessageFormat buildMessageFormat(final String pattern, final Locale locale) {
		return new MessageFormat(pattern, locale);
	}

	/**
	 * 
	 * @param invocation
	 * @param exception
	 */
	protected void translateException(final ActionInvocation invocation, final Throwable exception) {
		final VulpeStrutsController<?, ?> action = (VulpeStrutsController<?, ?>) invocation
				.getAction();
		String message = exception.getMessage();
		if (exception instanceof EntityNotFoundException) {
			final String token1 = "Unable to find ";
			final String token2 = " with id ";
			if (message.contains(token1) && message.contains(token2)) {
				String entity = message.substring(token1.length(), message.indexOf(token2));
				String identifier = message.substring(message.indexOf(token2) + token2.length());
				message = action.vulpe.controller().text(
						"vulpe.exception.translate.EntityNotFoundException",
						action.vulpe.controller().text(entity), identifier);
			}
		} else if (exception instanceof ServletException) {
			message = getCause(exception).getMessage();
		}
		action.addActionMessage(VulpeConstants.GENERAL_ERROR, message);
	}

	/**
	 * 
	 * @param invocation
	 * @param exception
	 * @return
	 */
	protected String treatExceptionMessage(final ActionInvocation invocation,
			final Throwable exception) {
		final VulpeStrutsController<?, ?> action = (VulpeStrutsController<?, ?>) invocation
				.getAction();
		final Throwable cause = getCause(exception);
		String message = cause.getMessage();
		String key = exception.getClass().getName();
		if (cause instanceof JspException) {
			key = cause.getClass().getName();
		} else if (cause instanceof SQLException) {
			key = cause.getClass().getName();
		}
		message = action.vulpe.controller().text(key);
		final String contactAdministratorMessageKey = "vulpe.error.contact.system.administrator";
		message += action.vulpe.controller().text(contactAdministratorMessageKey);
		if (isDebug(action)) {
			final String errorOccurrenceDebugKey = "vulpe.error.occurrence.debug";
			message += action.vulpe.controller().text(errorOccurrenceDebugKey);
			if (exception instanceof NullPointerException) {
				final StackTraceElement ste = exception.getStackTrace()[0];
				final String fileName = ste.getFileName().replace(".java", "");
				final String methodName = ste.getMethodName();
				final int lineNumber = ste.getLineNumber();
				message += action.vulpe.controller().text(key + ".debug", fileName, methodName,
						lineNumber);
			} else {
				message += "<i>"
						+ (exception instanceof VulpeValidationException ? "entity" : cause
								.getMessage()) + "</i>";
			}
		}
		return message;
	}

	/**
	 * 
	 * @return
	 */
	public ServletContext getServletContext() {
		return VulpeCacheHelper.getInstance().get(VulpeConstants.SERVLET_CONTEXT);
	}
}