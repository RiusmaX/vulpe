package org.vulpe.controller.vraptor.interceptor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.commons.util.VulpeStringUtil;
import org.vulpe.controller.vraptor.VulpeVRaptorSimpleController;

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

	@Override
	public void intercept(InterceptorStack stack, ResourceMethod method, Object resourceInstance)
			throws InterceptionException {
		LOG.debug("Intercepting " + request.getRequestURI());
		if (resourceInstance != null
				&& resourceInstance instanceof VulpeVRaptorSimpleController) {
			VulpeVRaptorSimpleController controller = (VulpeVRaptorSimpleController) resourceInstance;
			List<Field> fields = VulpeReflectUtil.getFields(controller.getClass());
			for (Field field : fields) {
				try {
					result.include(field.getName(), PropertyUtils.getProperty(controller, field
							.getName()));
				} catch (Exception e) {
					LOG.error(e);
				}
			}
			List<Method> methods = VulpeReflectUtil.getMethods(controller.getClass());
			for (Method method2 : methods) {
				String methodName = method2.getName();
				if (methodName.contains("get")
						&& (method2.getParameterTypes() == null || method2.getParameterTypes().length == 0)) {
					try {
						methodName = VulpeStringUtil.lowerCaseFirst(methodName.substring(3));
						result.include(methodName, method2.invoke(controller, new Object[] {}));
					} catch (Exception e) {
						LOG.error(e);
					}
				}
			}
		}
		stack.next(method, resourceInstance);
	}

}
