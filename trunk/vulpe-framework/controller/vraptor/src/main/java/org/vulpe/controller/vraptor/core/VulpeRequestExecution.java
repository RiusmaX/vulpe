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
