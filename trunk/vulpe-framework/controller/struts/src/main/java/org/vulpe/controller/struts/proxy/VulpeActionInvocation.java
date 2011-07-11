package org.vulpe.controller.struts.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.ServletRedirectResult;
import org.apache.struts2.dispatcher.mapper.DefaultActionMapper;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.VulpeConstants.Controller;
import org.vulpe.commons.VulpeConstants.Configuration.Ever;
import org.vulpe.commons.util.VulpeHashMap;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.controller.AbstractVulpeBaseController;
import org.vulpe.controller.VulpeController;
import org.vulpe.controller.annotations.ExecuteAlways;
import org.vulpe.controller.annotations.ExecuteOnce;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionChainResult;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionEventListener;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ObjectFactory;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.UnknownHandlerManager;
import com.opensymphony.xwork2.XWorkException;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationException;
import com.opensymphony.xwork2.config.entities.ActionConfig;
import com.opensymphony.xwork2.config.entities.InterceptorMapping;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import com.opensymphony.xwork2.inject.Container;
import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.interceptor.PreResultListener;
import com.opensymphony.xwork2.util.ValueStack;
import com.opensymphony.xwork2.util.ValueStackFactory;
import com.opensymphony.xwork2.util.profiling.UtilTimerStack;

/**
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings( { "serial", "unchecked" })
public class VulpeActionInvocation implements ActionInvocation {

	private static final Logger LOG = Logger.getLogger(VulpeActionInvocation.class);

	private static final Class[] EMPTY_CLASS_ARRAY = new Class[0];

	protected Object action;
	protected ActionProxy proxy;
	protected List<PreResultListener> preResultListeners;
	protected Map<String, Object> extraContext;
	protected ActionContext invocationContext;
	protected Iterator<InterceptorMapping> interceptors;
	protected ValueStack stack;
	protected Result result;
	protected Result explicitResult;
	protected String resultCode;
	protected boolean executed = false;
	protected boolean pushAction = true;
	protected ObjectFactory objectFactory;
	protected ActionEventListener actionEventListener;
	protected ValueStackFactory valueStackFactory;
	protected Container container;
	@SuppressWarnings("unused")
	private Configuration configuration;
	protected UnknownHandlerManager unknownHandlerManager;

	private final VulpeHashMap<String, Method> methodsToExecuteAlwaysBefore = new VulpeHashMap<String, Method>();

	private final VulpeHashMap<String, Method> methodsToExecuteOnceBefore = new VulpeHashMap<String, Method>();

	private final VulpeHashMap<String, Method> methodsToExecuteAlwaysAfter = new VulpeHashMap<String, Method>();

	private final VulpeHashMap<String, Method> methodsToExecuteOnceAfter = new VulpeHashMap<String, Method>();

	public VulpeActionInvocation(final Map<String, Object> extraContext, final boolean pushAction) {
		VulpeActionInvocation.this.extraContext = extraContext;
		VulpeActionInvocation.this.pushAction = pushAction;
	}

	@Inject
	public void setUnknownHandlerManager(UnknownHandlerManager unknownHandlerManager) {
		this.unknownHandlerManager = unknownHandlerManager;
	}

	@Inject
	public void setValueStackFactory(ValueStackFactory fac) {
		this.valueStackFactory = fac;
	}

	@Inject
	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	@Inject
	public void setObjectFactory(ObjectFactory fac) {
		this.objectFactory = fac;
	}

	@Inject
	public void setContainer(Container cont) {
		this.container = cont;
	}

	@Inject(required = false)
	public void setActionEventListener(ActionEventListener listener) {
		this.actionEventListener = listener;
	}

	public Object getAction() {
		return action;
	}

	public boolean isExecuted() {
		return executed;
	}

	public ActionContext getInvocationContext() {
		return invocationContext;
	}

	public ActionProxy getProxy() {
		return proxy;
	}

	/**
	 * If the DefaultActionInvocation has been executed before and the Result is
	 * an instance of ActionChainResult, this method will walk down the chain of
	 * ActionChainResults until it finds a non-chain result, which will be
	 * returned. If the DefaultActionInvocation's result has not been executed
	 * before, the Result instance will be created and populated with the result
	 * params.
	 * 
	 * @return a Result instance
	 * @throws Exception
	 */
	public Result getResult() throws Exception {
		Result returnResult = result;

		// If we've chained to other Actions, we need to find the last result
		while (returnResult instanceof ActionChainResult) {
			ActionProxy aProxy = ((ActionChainResult) returnResult).getProxy();

			if (aProxy != null) {
				Result proxyResult = aProxy.getInvocation().getResult();

				if ((proxyResult != null) && (aProxy.getExecuteResult())) {
					returnResult = proxyResult;
				} else {
					break;
				}
			} else {
				break;
			}
		}

		return returnResult;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		if (isExecuted())
			throw new IllegalStateException("Result has already been executed.");

		this.resultCode = resultCode;
	}

	public ValueStack getStack() {
		return stack;
	}

	/**
	 * Register a com.opensymphony.xwork2.interceptor.PreResultListener to be
	 * notified after the Action is executed and before the Result is executed.
	 * The ActionInvocation implementation must guarantee that listeners will be
	 * called in the order in which they are registered. Listener registration
	 * and execution does not need to be thread-safe.
	 * 
	 * @param listener
	 */
	public void addPreResultListener(PreResultListener listener) {
		if (preResultListeners == null) {
			preResultListeners = new ArrayList<PreResultListener>(1);
		}

		preResultListeners.add(listener);
	}

	public Result createResult() throws Exception {
		if (getAction() instanceof VulpeController) {
			final VulpeController controller = (VulpeController) getAction();
			if (controller.getResultName().equals(Controller.Result.REDIRECT)
					&& StringUtils.isNotBlank(controller.getUrlRedirect())) {
				final ServletRedirectResult srr = new ServletRedirectResult("${urlRedirect}");
				srr.setPrependServletContext(true);
				srr.setActionMapper(new DefaultActionMapper());
				return srr;
			}
		}
		if (explicitResult != null) {
			Result ret = explicitResult;
			explicitResult = null;

			return ret;
		}
		ActionConfig config = proxy.getConfig();
		Map<String, ResultConfig> results = config.getResults();

		ResultConfig resultConfig = null;

		try {
			resultConfig = results.get(resultCode);
		} catch (NullPointerException e) {
			// swallow
		}

		if (resultConfig == null) {
			// If no result is found for the given resultCode, try to get a
			// wildcard '*' match.
			resultConfig = results.get("*");
		}

		if (resultConfig != null) {
			try {
				return objectFactory.buildResult(resultConfig, invocationContext.getContextMap());
			} catch (Exception e) {
				LOG.error("There was an exception while instantiating the result of type "
						+ resultConfig.getClassName(), e);
				throw new XWorkException(e, resultConfig);
			}
		} else if (resultCode != null && !Action.NONE.equals(resultCode)
				&& unknownHandlerManager.hasUnknownHandlers()) {
			return unknownHandlerManager.handleUnknownResult(invocationContext, proxy
					.getActionName(), proxy.getConfig(), resultCode);
		}
		return null;
	}

	/**
	 * @throws ConfigurationException
	 *             If no result can be found with the returned code
	 */
	public String invoke() throws Exception {
		String profileKey = "invoke: ";
		try {
			UtilTimerStack.push(profileKey);

			if (executed) {
				throw new IllegalStateException("Action has already executed");
			}

			if (interceptors.hasNext()) {
				final InterceptorMapping interceptor = (InterceptorMapping) interceptors.next();
				String interceptorMsg = "interceptor: " + interceptor.getName();
				UtilTimerStack.push(interceptorMsg);
				try {
					resultCode = interceptor.getInterceptor().intercept(VulpeActionInvocation.this);
					if (getAction() instanceof VulpeController
							&& !resultCode.equals(Controller.Result.MESSAGES)
							&& resultCode.equals(Controller.Result.ERRORS)) {
						resultCode = ((VulpeController) getAction()).getResultName();
					}
				} finally {
					UtilTimerStack.pop(interceptorMsg);
				}
			} else {
				resultCode = invokeActionOnly();
				if (getAction() instanceof VulpeController) {
					resultCode = ((VulpeController) getAction()).getResultName();
				}
			}

			// this is needed because the result will be executed, then control
			// will return to the Interceptor, which will
			// return above and flow through again
			if (!executed) {
				if (preResultListeners != null) {
					for (Object preResultListener : preResultListeners) {
						PreResultListener listener = (PreResultListener) preResultListener;

						String _profileKey = "preResultListener: ";
						try {
							UtilTimerStack.push(_profileKey);
							listener.beforeResult(this, resultCode);
						} finally {
							UtilTimerStack.pop(_profileKey);
						}
					}
				}

				// now execute the result, if we're supposed to
				if (proxy.getExecuteResult()) {
					executeResult();
				}

				executed = true;
			}

			return resultCode;
		} finally {
			UtilTimerStack.pop(profileKey);
		}
	}

	public String invokeActionOnly() throws Exception {
		return invokeAction(getAction(), proxy.getConfig());
	}

	protected void createAction(Map<String, Object> contextMap) {
		// load action
		String timerKey = "actionCreate: " + proxy.getActionName();
		try {
			UtilTimerStack.push(timerKey);
			action = objectFactory.buildAction(proxy.getActionName(), proxy.getNamespace(), proxy
					.getConfig(), contextMap);
		} catch (InstantiationException e) {
			throw new XWorkException("Unable to intantiate Action!", e, proxy.getConfig());
		} catch (IllegalAccessException e) {
			throw new XWorkException("Illegal access to constructor, is it public?", e, proxy
					.getConfig());
		} catch (Exception e) {
			String gripe = "";

			if (proxy == null) {
				gripe = "Whoa!  No ActionProxy instance found in current ActionInvocation.  This is bad ... very bad";
			} else if (proxy.getConfig() == null) {
				gripe = "Sheesh.  Where'd that ActionProxy get to?  I can't find it in the current ActionInvocation!?";
			} else if (proxy.getConfig().getClassName() == null) {
				gripe = "No Action defined for '" + proxy.getActionName() + "' in namespace '"
						+ proxy.getNamespace() + "'";
			} else {
				gripe = "Unable to instantiate Action, " + proxy.getConfig().getClassName()
						+ ",  defined for '" + proxy.getActionName() + "' in namespace '"
						+ proxy.getNamespace() + "'";
			}

			gripe += (((" -- " + e.getMessage()) != null) ? e.getMessage()
					: " [no message in exception]");
			throw new XWorkException(gripe, e, proxy.getConfig());
		} finally {
			UtilTimerStack.pop(timerKey);
		}

		if (actionEventListener != null) {
			action = actionEventListener.prepare(action, stack);
		}
	}

	protected Map<String, Object> createContextMap() {
		Map<String, Object> contextMap;

		if ((extraContext != null) && (extraContext.containsKey(ActionContext.VALUE_STACK))) {
			// In case the ValueStack was passed in
			stack = (ValueStack) extraContext.get(ActionContext.VALUE_STACK);

			if (stack == null) {
				throw new IllegalStateException("There was a null Stack set into the extra params.");
			}

			contextMap = stack.getContext();
		} else {
			// create the value stack
			// this also adds the ValueStack to its context
			stack = valueStackFactory.createValueStack();

			// create the action context
			contextMap = stack.getContext();
		}

		// put extraContext in
		if (extraContext != null) {
			contextMap.putAll(extraContext);
		}

		// put this DefaultActionInvocation into the context map
		contextMap.put(ActionContext.ACTION_INVOCATION, this);
		contextMap.put(ActionContext.CONTAINER, container);

		return contextMap;
	}

	/**
	 * Uses getResult to get the final Result and executes it
	 * 
	 * @throws ConfigurationException
	 *             If not result can be found with the returned code
	 */
	private void executeResult() throws Exception {
		result = createResult();

		String timerKey = "executeResult: " + getResultCode();
		try {
			UtilTimerStack.push(timerKey);
			if (result != null) {
				result.execute(this);
			} else if (resultCode != null && !Action.NONE.equals(resultCode)) {
				throw new ConfigurationException("No result defined for action "
						+ getAction().getClass().getName() + " and result " + getResultCode(),
						proxy.getConfig());
			} else {
				if (LOG.isDebugEnabled()) {
					LOG.debug("No result returned for action " + getAction().getClass().getName()
							+ " at " + proxy.getConfig().getLocation());
				}
			}
		} finally {
			UtilTimerStack.pop(timerKey);
		}
	}

	public void init(ActionProxy proxy) {
		this.proxy = proxy;
		Map<String, Object> contextMap = createContextMap();

		// Setting this so that other classes, like object factories, can use
		// the ActionProxy and other
		// contextual information to operate
		ActionContext actionContext = ActionContext.getContext();

		if (actionContext != null) {
			actionContext.setActionInvocation(this);
		}

		createAction(contextMap);

		if (pushAction) {
			stack.push(action);
			contextMap.put("action", action);
		}

		invocationContext = new ActionContext(contextMap);
		invocationContext.setName(proxy.getActionName());

		// get a new List so we don't get problems with the iterator if someone
		// changes the list
		List<InterceptorMapping> interceptorList = new ArrayList<InterceptorMapping>(proxy
				.getConfig().getInterceptors());
		interceptors = interceptorList.iterator();
	}

	protected String invokeAction(Object action, ActionConfig actionConfig) throws Exception {
		String methodName = proxy.getMethod();

		if (LOG.isDebugEnabled()) {
			LOG.debug("Executing action method = " + actionConfig.getMethodName());
		}

		String timerKey = "invokeAction: " + proxy.getActionName();
		try {
			UtilTimerStack.push(timerKey);

			boolean methodCalled = false;
			Object methodResult = null;
			Method method = null;
			try {
				method = getAction().getClass().getMethod(methodName, EMPTY_CLASS_ARRAY);
			} catch (NoSuchMethodException e) {
				// hmm -- OK, try doXxx instead
				try {
					String altMethodName = "do" + methodName.substring(0, 1).toUpperCase()
							+ methodName.substring(1);
					method = getAction().getClass().getMethod(altMethodName, EMPTY_CLASS_ARRAY);
				} catch (NoSuchMethodException e1) {
					// well, give the unknown handler a shot
					if (unknownHandlerManager.hasUnknownHandlers()) {
						try {
							methodResult = unknownHandlerManager.handleUnknownMethod(action,
									methodName);
							methodCalled = true;
						} catch (NoSuchMethodException e2) {
							// throw the original one
							throw e;
						}
					} else {
						throw e;
					}
				}
			}

			if (!methodCalled) {
				executeMethods(action);
				executeAlwaysBefore(action);
				final boolean same = sameController(action);
				if (same) {
					executeOnceBefore(action);
				}
				methodResult = method.invoke(action, new Object[0]);
				if (same) {
					executeOnceAfter(action);
				}
				executeAlwaysAfter(action);
			}

			if (methodResult instanceof Result) {
				this.explicitResult = (Result) methodResult;

				// Wire the result automatically
				container.inject(explicitResult);
				return null;
			} else {
				return (String) methodResult;
			}
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException("The " + methodName + "() is not defined in action "
					+ getAction().getClass() + "");
		} catch (InvocationTargetException e) {
			// We try to return the source exception.
			Throwable t = e.getTargetException();

			if (actionEventListener != null) {
				String result = actionEventListener.handleException(t, getStack());
				if (result != null) {
					return result;
				}
			}
			if (t instanceof Exception) {
				throw (Exception) t;
			} else {
				throw e;
			}
		} finally {
			UtilTimerStack.pop(timerKey);
		}
	}

	/**
	 * 
	 * @param action
	 */
	private void executeMethods(final Object action) {
		if (action instanceof VulpeController) {
			final AbstractVulpeBaseController baseController = (AbstractVulpeBaseController) action;
			baseController.vulpe.controller().currentMethodName(proxy.getConfig().getMethodName());
			final List<Method> methods = VulpeReflectUtil.getMethods(baseController.getClass());
			for (final Method method : methods) {
				final ExecuteAlways executeAlways = method.getAnnotation(ExecuteAlways.class);
				if (executeAlways != null) {
					if (executeAlways.before()) {
						methodsToExecuteAlwaysBefore.put(method.getName(), method);
					} else {
						methodsToExecuteAlwaysAfter.put(method.getName(), method);
					}
				}
				final ExecuteOnce executeOnce = method.getAnnotation(ExecuteOnce.class);
				if (executeOnce != null) {
					if (executeOnce.before()) {
						methodsToExecuteOnceBefore.put(method.getName(), method);
					} else {
						methodsToExecuteOnceAfter.put(method.getName(), method);
					}
				}
			}
		}
	}

	/**
	 * 
	 * @param action
	 */
	private void executeAlwaysBefore(final Object action) {
		if (action instanceof VulpeController) {
			final VulpeController controller = (VulpeController) action;
			for (final Method method : methodsToExecuteAlwaysBefore.values()) {
				try {
					method.invoke(controller, new Object[] {});
				} catch (Exception e) {
					LOG.error(e);
				}
			}
		}
	}

	/**
	 * 
	 * @param action
	 */
	private void executeAlwaysAfter(final Object action) {
		if (action instanceof VulpeController) {
			final VulpeController controller = (VulpeController) action;
			for (final Method method : methodsToExecuteAlwaysAfter.values()) {
				try {
					method.invoke(controller, new Object[] {});
				} catch (Exception e) {
					LOG.error(e);
				}
			}
		}
	}

	/**
	 * 
	 * @param action
	 */
	private void executeOnceBefore(final Object action) {
		if (action instanceof VulpeController) {
			final VulpeController controller = (VulpeController) action;
			for (final Method method : methodsToExecuteOnceBefore.values()) {
				try {
					method.invoke(controller, new Object[] {});
				} catch (Exception e) {
					LOG.error(e);
				}
			}
		}
	}

	/**
	 * 
	 * @param action
	 */
	private void executeOnceAfter(final Object action) {
		if (action instanceof VulpeController) {
			final VulpeController controller = (VulpeController) action;
			for (final Method method : methodsToExecuteOnceAfter.values()) {
				try {
					method.invoke(controller, new Object[] {});
				} catch (Exception e) {
					LOG.error(e);
				}
			}
		}
	}

	/**
	 * 
	 * @param action
	 * @return
	 */
	private boolean sameController(final Object action) {
		boolean same = false;
		if (action instanceof VulpeController) {
			final AbstractVulpeBaseController baseController = (AbstractVulpeBaseController) action;
			if (baseController.ever != null) {
				baseController.ever.put(Ever.CURRENT_CONTROLLER_NAME, baseController.vulpe
						.controller().currentName());
				final String currentControllerKey = baseController.ever
						.getSelf(Ever.CURRENT_CONTROLLER_KEY);
				final String controllerKey = baseController.vulpe.controller().currentKey();
				boolean autocomplete = false;
				if (baseController.getEntitySelect() != null
						&& StringUtils.isNotEmpty(baseController.getEntitySelect()
								.getAutocomplete())) {
					autocomplete = true;
				}
				if (StringUtils.isEmpty(currentControllerKey)) {
					baseController.ever.put(Ever.CURRENT_CONTROLLER_KEY, controllerKey);
					same = true;
				} else if (!currentControllerKey.equals(controllerKey)
						&& StringUtils.isEmpty(baseController.vulpe.controller().popupKey())
						&& !autocomplete) {
					baseController.ever.removeWeakRef();
					baseController.ever.put(Ever.CURRENT_CONTROLLER_KEY, controllerKey);
					same = true;
				}
			}
			updateParameters(baseController);
		}
		return same;
	}

	/**
	 * 
	 * @param controller
	 */
	private void updateParameters(final AbstractVulpeBaseController controller) {
		ServletActionContext.getRequest().getSession().setAttribute(VulpeConstants.Session.EVER,
				controller.ever);
		ServletActionContext.getRequest().setAttribute(VulpeConstants.Request.NOW, controller.now);
	}
}
