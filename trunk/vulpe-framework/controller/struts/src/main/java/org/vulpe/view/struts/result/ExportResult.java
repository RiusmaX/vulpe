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
					request.getSession()).<String> getAuto(Ever.CURRENT_HTML_CONTENT)), output);
			output.flush();
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
