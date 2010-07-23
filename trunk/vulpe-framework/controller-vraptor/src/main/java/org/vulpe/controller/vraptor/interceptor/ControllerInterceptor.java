package org.vulpe.controller.vraptor.interceptor;

import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.ioc.SessionScoped;
import br.com.caelum.vraptor.resource.ResourceMethod;

@Intercepts
@SessionScoped
public class ControllerInterceptor implements Interceptor {

	private final Result result;
	private final HttpServletRequest request;

	public ControllerInterceptor(Result result, HttpServletRequest request) {
		this.result = result;
		this.request = request;
	}

	@Override
	public boolean accepts(ResourceMethod method) {
		return true;
	}

	@Override
	public void intercept(InterceptorStack stack, ResourceMethod method, Object resourceInstance)
			throws InterceptionException {
		System.out.println("Intercepting " + request.getRequestURI());
		stack.next(method, resourceInstance);
	}

}
