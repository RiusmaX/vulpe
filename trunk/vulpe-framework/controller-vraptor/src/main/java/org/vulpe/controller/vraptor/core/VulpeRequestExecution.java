package org.vulpe.controller.vraptor.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vulpe.controller.vraptor.interceptor.VulpeControllerInterceptor;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.core.DefaultRequestExecution;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.core.RequestExecution;
import br.com.caelum.vraptor.extra.ForwardToDefaultViewInterceptor;
import br.com.caelum.vraptor.interceptor.DeserializingInterceptor;
import br.com.caelum.vraptor.interceptor.ExecuteMethodInterceptor;
import br.com.caelum.vraptor.interceptor.FlashInterceptor;
import br.com.caelum.vraptor.interceptor.InstantiateInterceptor;
import br.com.caelum.vraptor.interceptor.InterceptorListPriorToExecutionExtractor;
import br.com.caelum.vraptor.interceptor.OutjectResult;
import br.com.caelum.vraptor.interceptor.ParametersInstantiatorInterceptor;
import br.com.caelum.vraptor.interceptor.ResourceLookupInterceptor;
import br.com.caelum.vraptor.interceptor.download.DownloadInterceptor;
import br.com.caelum.vraptor.interceptor.multipart.MultipartInterceptor;
import br.com.caelum.vraptor.ioc.Component;

@Component
public class VulpeRequestExecution implements RequestExecution {

	private static final Logger LOG = LoggerFactory.getLogger(DefaultRequestExecution.class);

	private final InterceptorStack interceptorStack;
	private final InstantiateInterceptor instantiator;

	public VulpeRequestExecution(InterceptorStack interceptorStack,
			InstantiateInterceptor instantiator) {
		this.interceptorStack = interceptorStack;
		this.instantiator = instantiator;
	}

	public void execute() throws InterceptionException {
		LOG.debug("executing stack  DefaultRequestExecution");

		interceptorStack.add(MultipartInterceptor.class);
		interceptorStack.add(ResourceLookupInterceptor.class);
		interceptorStack.add(FlashInterceptor.class);
		interceptorStack.add(InterceptorListPriorToExecutionExtractor.class);
		interceptorStack.add(instantiator);
		interceptorStack.add(ParametersInstantiatorInterceptor.class);
		interceptorStack.add(DeserializingInterceptor.class);
		interceptorStack.add(ExecuteMethodInterceptor.class);
		interceptorStack.add(OutjectResult.class);
		interceptorStack.add(VulpeControllerInterceptor.class);
		interceptorStack.add(DownloadInterceptor.class);
		interceptorStack.add(ForwardToDefaultViewInterceptor.class);
		interceptorStack.next(null, null);
	}
}
