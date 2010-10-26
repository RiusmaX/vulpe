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
 * @author <a href="mailto:fabio.viana@activethread.com.br">F�bio Viana</a>
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
		// gets real exception
		final Throwable newException = getException(exception);
		if (newException instanceof VulpeAuthenticationException) {
			action.addActionMessageKey(newException.getMessage());
		} else if (newException instanceof VulpeAuthorizationException) {
			action.addActionMessageKey(newException.getMessage());
		} else if (newException instanceof VulpeSystemException) {
			final VulpeSystemException sException = (VulpeSystemException) newException;
			if (sException.getArgs() != null && sException.getArgs().length > 0) {
				action.addActionMessage(newException.getMessage(), (Object[]) sException.getArgs());
			} else {
				final String key = newException.getMessage();
				if (key.startsWith("vulpe.error")) {
					action.addActionError(key, (sException.getCause() == null
							|| StringUtils.isEmpty(sException.getCause().getMessage()) ? "unknown"
							: sException.getCause().getMessage()));
				} else {
					action.addActionMessage(key, (sException.getCause() == null
							|| StringUtils.isEmpty(sException.getCause().getMessage()) ? "unknown"
							: sException.getCause().getMessage()));
				}
			}
		} else if (newException instanceof VulpeApplicationException) {
			final VulpeApplicationException sException = (VulpeApplicationException) newException;
			action.addActionMessage(newException.getMessage(), (Object[]) sException.getArgs());
		} else {
			final String key = newException.getClass().getName().toLowerCase();
			String value = action.getText(key);
			if (StringUtils.isBlank(value) || value.equals(key)) {
				String msg = newException.getMessage();
				final MessageFormat msgFormat = buildMessageFormat(TextParseUtil
						.translateVariables(msg, invocation.getStack()), invocation
						.getInvocationContext().getLocale());
				msg = msgFormat.format(null);
				value = action.getText(msg);
				if (StringUtils.isBlank(value) || value.equals(msg)) {
					action.addActionMessage(VulpeConstants.GENERAL_ERROR, msg);
				} else {
					action.addActionMessage(value);
				}
			} else {
				action.addActionMessage(value);
			}
		}
		if (action.isAjax()) {
			return VulpeConstants.Controller.Forward.MESSAGES;
		} else {
			request.setAttribute(VulpeConstants.VULPE_SHOW_MESSAGES, true);
			return VulpeConstants.Controller.Forward.ERRORS;
		}
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
}