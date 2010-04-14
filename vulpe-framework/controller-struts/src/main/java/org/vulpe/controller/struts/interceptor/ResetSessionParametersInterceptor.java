package org.vulpe.controller.struts.interceptor;

import org.vulpe.common.Constants;
import org.vulpe.controller.annotations.ResetSession;
import org.vulpe.controller.util.ControllerUtil;
import org.vulpe.exception.VulpeSystemException;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

@SuppressWarnings("serial")
public class ResetSessionParametersInterceptor extends MethodFilterInterceptor {

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.opensymphony.xwork2.interceptor.MethodFilterInterceptor#doIntercept
	 * (com.opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	protected String doIntercept(final ActionInvocation invocation)
			throws Exception {
		final Object action = invocation.getAction();
		final String key = ControllerUtil.getInstance().getCurrentActionName()
				.concat(Constants.PARAMS_SESSION_KEY);
		if (ActionContext.getContext().getSession().containsKey(key)
				&& isMethodReset(action)) {
			ActionContext.getContext().getSession().remove(key);
		}
		return invocation.invoke();
	}

	/**
	 *
	 * @param action
	 * @return
	 */
	protected boolean isMethodReset(final Object action) {
		try {
			return action instanceof ValidationAware
					&& (((ValidationAware) action).hasActionErrors() || ((ValidationAware) action)
							.hasFieldErrors()) ? false : action.getClass()
					.getMethod(ControllerUtil.getInstance().getCurrentMethod())
					.isAnnotationPresent(ResetSession.class);
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
	}
}