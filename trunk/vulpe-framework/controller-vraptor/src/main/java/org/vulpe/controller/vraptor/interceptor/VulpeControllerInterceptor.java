package org.vulpe.controller.vraptor.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.vulpe.controller.vraptor.VulpeVRaptorController;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.caelum.vraptor.resource.ResourceMethod;

@Component
@RequestScoped
public class VulpeControllerInterceptor implements Interceptor {

	private static final Logger LOG = Logger.getLogger(VulpeControllerInterceptor.class);
	private final Result result;
	private final HttpServletRequest request;

	public VulpeControllerInterceptor(Result result, HttpServletRequest request) {
		this.result = result;
		this.request = request;
	}

	@Override
	public boolean accepts(ResourceMethod method) {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void intercept(InterceptorStack stack, ResourceMethod method, Object resourceInstance)
			throws InterceptionException {
		LOG.debug("Intercepting " + request.getRequestURI());
		if (resourceInstance != null && resourceInstance instanceof VulpeVRaptorController) {
			VulpeVRaptorController controller = (VulpeVRaptorController) resourceInstance;
			result.include("controllerConfig", controller.getControllerConfig());
		}
		stack.next(method, resourceInstance);
	}

}
