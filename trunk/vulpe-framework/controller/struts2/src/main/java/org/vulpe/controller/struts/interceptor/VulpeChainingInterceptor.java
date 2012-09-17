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
package org.vulpe.controller.struts.interceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.vulpe.controller.VulpeController;
import org.vulpe.controller.VulpeController.Operation;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.ChainingInterceptor;
import com.opensymphony.xwork2.ognl.OgnlUtil;
import com.opensymphony.xwork2.util.CompoundRoot;
import com.opensymphony.xwork2.util.ValueStack;

@SuppressWarnings( { "serial", "unchecked", "rawtypes" })
public class VulpeChainingInterceptor extends ChainingInterceptor {

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.opensymphony.xwork2.interceptor.ChainingInterceptor#intercept(com
	 * .opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	public String intercept(final ActionInvocation invocation) throws Exception {
		if (invocation.getAction() != null && invocation.getAction() instanceof VulpeController) {
			if (Operation.READ.getValue().equals(invocation.getProxy().getMethod())) {
				if (invocation.getAction() instanceof ValidationAware) {
					final ValueStack stack = invocation.getStack();
					final CompoundRoot root = stack.getRoot();
					if (root.size() > 1) {
						final List list = new ArrayList(root);
						list.remove(0);
						Collections.reverse(list);
						final Map ctxMap = invocation.getInvocationContext().getContextMap();
						final Iterator iterator = list.iterator();
						while (iterator.hasNext()) {
							final Object obj = iterator.next();
							if (obj instanceof ValidationAware) {
								new OgnlUtil().copy(obj, invocation.getAction(), ctxMap, null,
										Arrays.asList(new String[] { "actionErrors",
												"actionMessages", "fieldErrors" }));
							}
						}
					}
				}
				return invocation.invoke();
			}
		}
		return super.intercept(invocation);
	}
}