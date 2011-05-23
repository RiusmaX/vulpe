package org.vulpe.controller.struts.proxy;

import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.DefaultActionProxyFactory;

/**
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 */
public class VulpeActionProxyFactory extends DefaultActionProxyFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.opensymphony.xwork2.DefaultActionProxyFactory#createActionProxy(java
	 * .lang.String, java.lang.String, java.lang.String, java.util.Map, boolean,
	 * boolean)
	 */
	public ActionProxy createActionProxy(String namespace, String actionName, String methodName,
			Map<String, Object> extraContext, boolean executeResult, boolean cleanupContext) {
		final ActionInvocation inv = new VulpeActionInvocation(extraContext, true);
		container.inject(inv);
		return createActionProxy(inv, namespace, actionName, methodName, executeResult,
				cleanupContext);
	}
}
