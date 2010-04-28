package org.vulpe.controller.struts.interceptor;

import java.util.Iterator;
import java.util.Map;

import org.vulpe.common.Constants;
import org.vulpe.common.ValidationUtil;
import org.vulpe.controller.annotations.ResetSession;
import org.vulpe.controller.struts.util.StrutsControllerUtil;
import org.vulpe.exception.VulpeSystemException;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.OgnlContextState;
import com.opensymphony.xwork2.util.ValueStack;

@SuppressWarnings("serial")
public class SessionParametersInterceptor extends
		com.opensymphony.xwork2.interceptor.ParametersInterceptor {

	@Override
	@SuppressWarnings("unchecked")
	protected void setParameters(final Object action, final ValueStack stack, final Map parameters) {
		super.setParameters(action, stack, parameters);

		final String key = StrutsControllerUtil.getInstance().getCurrentActionName()
				.concat(Constants.PARAMS_SESSION_KEY);
		if (isMethodReset(action)) {
			ActionContext.getContext().getSession().remove(key);
		} else {
			final Map params = (Map) ActionContext.getContext().getSession().get(key);
			if (params != null) {
				final boolean createNullObjects = OgnlContextState
						.isCreatingNullObjects(stack.getContext());
				try {
					for (final Iterator iterator = params.keySet().iterator(); iterator
							.hasNext();) {
						final String name = (String) iterator.next();
						final Object[] value = (Object[]) params.get(name);
						OgnlContextState.setCreatingNullObjects(stack
								.getContext(), false);
						if (ValidationUtil.getInstance().isEmpty(
								stack.findValue(name))) {
							OgnlContextState.setCreatingNullObjects(stack
									.getContext(), true);
							stack.setValue(name, value[1]);
						}
						if (Boolean.TRUE.equals(value[0])) {
							params.remove(name);
						}
					}
				} finally {
					OgnlContextState.setCreatingNullObjects(stack
							.getContext(), createNullObjects);
				}
			}
		}
	}

	protected boolean isMethodReset(final Object action) {
		try {
			final ResetSession resetSession = action.getClass().getMethod(
					StrutsControllerUtil.getInstance().getCurrentMethod())
					.getAnnotation(ResetSession.class);
			return resetSession != null && resetSession.before();
		} catch (Exception e) {
			throw new VulpeSystemException(e);
		}
	}
}