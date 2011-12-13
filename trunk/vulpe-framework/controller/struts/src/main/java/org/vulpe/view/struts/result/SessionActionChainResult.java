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
package org.vulpe.view.struts.result;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.controller.struts.VulpeStrutsController;

import com.opensymphony.xwork2.ActionChainResult;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;

@SuppressWarnings( { "serial", "unchecked" })
public class SessionActionChainResult extends ActionChainResult {
	private boolean saveParams;
	private boolean sendParams;
	private boolean clearParams;
	private String noClearParamNames;
	private String name;

	public String getNoClearParamNames() {
		return noClearParamNames;
	}

	public void setNoClearParamNames(final String noClearParamNames) {
		this.noClearParamNames = noClearParamNames;
	}

	public boolean isSaveParams() {
		return saveParams;
	}

	public boolean isClearParams() {
		return clearParams;
	}

	public void setClearParams(final boolean clearParams) {
		this.clearParams = clearParams;
	}

	public void setSaveParams(final boolean saveParams) {
		this.saveParams = saveParams;
	}

	public boolean isSendParams() {
		return sendParams;
	}

	public void setSendParams(final boolean sendParams) {
		this.sendParams = sendParams;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getName(final ActionInvocation invocation, final boolean owner) {
		if (StringUtils.isEmpty(name)) {
			name = ActionContext.getContext().getName();
			if (invocation.getAction() instanceof VulpeStrutsController) {
				final VulpeStrutsController<?, ?> action = (VulpeStrutsController<?, ?>) invocation
						.getAction();
				name = owner ? action.vulpe.controller().config().getOwnerController()
						: action.vulpe.controller().config().getControllerName();
			}
		}
		return name;
	}

	@Override
	public void execute(final ActionInvocation invocation) throws Exception {
		final Map requestParams = invocation.getInvocationContext().getParameters();
		final Map sessionParams = invocation.getInvocationContext().getSession();
		String name;
		if (isClearParams()) {
			ActionContext.getContext().put(VulpeConstants.CLEAR_PARAMS, Boolean.TRUE);
		}

		Map params = null;
		if (isClearParams()) {
			if (StringUtils.isNotEmpty(getNoClearParamNames())) {
				params = new HashMap();
				final String paramNames[] = getNoClearParamNames().split(",");
				for (int i = 0; i < paramNames.length; i++) {
					if (paramNames[i].contains("=")) {
						final String paramName[] = paramNames[i].split("=");
						final Object value = requestParams.get(paramName[0]);
						params.put(paramName[0], (value == null ? new String[] { paramName[1] }
								: value));
					} else {
						params.put(paramNames[i], requestParams.get(paramNames[i]));
					}
				}
			}
			requestParams.clear();
		}

		if (isSaveParams()) {
			name = getName(invocation, false);
			sessionParams.put(name, requestParams);
		}

		if (isSendParams()) {
			name = getName(invocation, true);
			final Map paramsInSession = (Map) sessionParams.get(name);
			if (paramsInSession != null) {
				requestParams.putAll(paramsInSession);
				sessionParams.remove(name);
			}
		}

		if (params != null) {
			requestParams.putAll(params);
		}

		super.execute(invocation);
	}
}