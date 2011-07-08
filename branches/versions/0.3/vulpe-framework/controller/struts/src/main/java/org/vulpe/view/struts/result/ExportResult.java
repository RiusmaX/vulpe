/**
 * Vulpe Framework - Copyright (c) Active Thread
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vulpe.view.struts.result;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.StrutsResultSupport;
import org.vulpe.commons.VulpeConstants.Configuration.Ever;
import org.vulpe.commons.util.Html2PdfUtil;
import org.vulpe.commons.util.VulpeStringUtil;
import org.vulpe.controller.commons.EverParameter;

import com.opensymphony.xwork2.ActionInvocation;

/**
 * Export Result
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 */
@SuppressWarnings("serial")
public class ExportResult extends StrutsResultSupport {

	@Override
	protected void doExecute(String finalLocation, ActionInvocation invocation) throws Exception {
		final HttpServletRequest request = (HttpServletRequest) invocation.getInvocationContext()
				.get(HTTP_REQUEST);
		final HttpServletResponse response = (HttpServletResponse) invocation
				.getInvocationContext().get(HTTP_RESPONSE);
		try {
			response.setContentType("application/pdf");
			response.setHeader("Content-disposition", "inline;filename=export.pdf");
			final OutputStream output = response.getOutputStream();
			Html2PdfUtil.convert(VulpeStringUtil.encodeHTMLSpecials(EverParameter.getInstance(
					request.getSession()).<String> getSelf(Ever.CURRENT_HTML_CONTENT)), output);
			output.flush();
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
