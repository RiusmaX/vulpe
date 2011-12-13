/**
 * Vulpe Framework - Quick and Smart ;)
 * Copyright (C) 2011 Active Thread
 * 
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos da Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; tanto a versão 2 da
 * Licença como (a seu critério) qualquer versão mais nova.
 * 
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * 
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para a Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.
 */
/**
 * Vulpe Framework - Quick and Smart ;)
 * Copyright (C) 2011 Active Thread
 * 
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.vulpe.controller.vraptor.interceptor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.vulpe.commons.util.VulpeReflectUtil;
import org.vulpe.commons.util.VulpeStringUtil;
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
			final List<Field> fields = VulpeReflectUtil.getFields(controller.getClass());
			for (final Field field : fields) {
				try {
					result.include(field.getName(), VulpeReflectUtil.getFieldValue(controller,
							field.getName()));
				} catch (Exception e) {
					LOG.error(e);
				}
			}
			final List<Method> methods = VulpeReflectUtil.getMethods(controller.getClass());
			for (final Method method2 : methods) {
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
