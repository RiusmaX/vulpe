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
import java.text.MessageFormat;
import java.util.Locale;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.controller.struts.VulpeStrutsController;
import org.vulpe.exception.VulpeApplicationException;
import org.vulpe.exception.VulpeAuthenticationException;
import org.vulpe.exception.VulpeAuthorizationException;
import org.vulpe.exception.VulpeSystemException;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.util.TextParseUtil;

/**
 * Interceptor class to control exceptions.
 *
 * @author <a href="mailto:fabio.viana@vulpe.org">Fábio Viana</a>
 */
@SuppressWarnings( { "serial", "unchecked" })
public class VulpeExceptionMappingInterceptor extends com.opensymphony.xwork2.interceptor.ExceptionMappingInterceptor {

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
			e.printStackTrace();
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
	protected String findResultFromException(final ActionInvocation invocation, final Throwable exception) {
		final VulpeStrutsController<?, ?> action = (VulpeStrutsController<?, ?>) invocation.getAction();
		final HttpServletRequest request = ServletActionContext.getRequest();
		request.setAttribute(VulpeConstants.IS_EXCEPTION, Boolean.TRUE);
		// gets real exception
		final Throwable newException = getException(exception);
		if (newException instanceof VulpeAuthenticationException) {
			action.addActionMessageKey(newException.getMessage());
		} else if (newException instanceof VulpeAuthorizationException) {
			action.addActionMessageKey(newException.getMessage());
		} else if (newException instanceof VulpeSystemException) {
			final VulpeSystemException vse = (VulpeSystemException) newException;
			if (vse.getArgs() != null && vse.getArgs().length > 0) {
				action.addActionMessage(newException.getMessage(), (Object[]) vse.getArgs());
			} else {
				String message = action.getText("vulpe.error.unknown");
				final String key = newException.getMessage();
				if (key.startsWith("vulpe.error")) {
					if (vse.getCause() != null && StringUtils.isNotEmpty(vse.getCause().getMessage())) {
						message = vse.getCause().getMessage();
						if (message.startsWith("vulpe.error")) {
							message = action.getText(message);
						}
					}
					action.addActionError(key, message);
				} else {
					if (vse.getCause() != null && StringUtils.isNotEmpty(vse.getCause().getMessage())) {
						message = vse.getCause().getMessage();
						if (message.startsWith("vulpe.error")) {
							message = action.getText(message);
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
					final MessageFormat messageFormat = buildMessageFormat(TextParseUtil.translateVariables(message,
							invocation.getStack()), invocation.getInvocationContext().getLocale());
					message = messageFormat.format(null);
					value = action.getText(message);
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
		if (!action.isAjax()) {
			request.setAttribute(VulpeConstants.VULPE_SHOW_MESSAGES, true);
		}
		return VulpeConstants.Controller.Forward.MESSAGES;
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
		final VulpeStrutsController<?, ?> action = (VulpeStrutsController<?, ?>) invocation.getAction();
		String message = exception.getMessage();
		if (exception instanceof EntityNotFoundException) {
			String token1 = "Unable to find ";
			String token2 = " with id ";
			if (message.contains(token1) && message.contains(token2)) {
				String entity = message.substring(token1.length(), message.indexOf(token2));
				String identifier = message.substring(message.indexOf(token2) + token2.length());
				message = action.getText("vulpe.exception.translate.EntityNotFoundException", action.getText(entity),
						identifier);
			}
		}
		action.addActionMessage(VulpeConstants.GENERAL_ERROR, message);
	}

	/**
	 *
	 * @param invocation
	 * @param exception
	 * @return
	 */
	protected String treatExceptionMessage(final ActionInvocation invocation, final Throwable exception) {
		final VulpeStrutsController<?, ?> action = (VulpeStrutsController<?, ?>) invocation.getAction();
		String message = "";
		final String key = exception.getClass().getName();
		final Boolean sessionDebug = action.ever.getSelf(VulpeConstants.Configuration.Ever.DEBUG, Boolean.FALSE);
		final Boolean globalDebug = action.ever.getSelf(VulpeConstants.Configuration.Global.DEBUG, Boolean.FALSE);
		if (sessionDebug || globalDebug) {
			if (key.endsWith(NullPointerException.class.getName())) {
				final StackTraceElement ste = exception.getStackTrace()[0];
				final String fileName = ste.getFileName().replace(".java", "");
				final String methodName = ste.getMethodName();
				final int lineNumber = ste.getLineNumber();
				message = action.getText(key, lineNumber, methodName, fileName);
			}
		} else {
			message = action.getText(key);
		}
		return message;
	}
}