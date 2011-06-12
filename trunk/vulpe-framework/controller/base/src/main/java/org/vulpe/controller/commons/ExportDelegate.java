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
package org.vulpe.controller.commons;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.vulpe.commons.util.Html2PdfUtil;
import org.vulpe.commons.util.VulpeStringUtil;
import org.vulpe.controller.ContentResponseWrapper;

/**
 * Export Delegate
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 */
public class ExportDelegate {

	protected static final Logger LOG = Logger.getLogger(ExportDelegate.class);

	/**
	 * 
	 * @param request
	 * @param response
	 * @param wrapper
	 * @throws ServletException
	 * @throws IOException
	 */
	public static void export(HttpServletRequest request, HttpServletResponse response,
			ContentResponseWrapper wrapper) throws ServletException, IOException {
		response.setContentType("application/pdf");
		response.setHeader("Content-disposition", "inline;filename=export.pdf");
		final OutputStream writer = response.getOutputStream();
		try {
			Html2PdfUtil.convert(VulpeStringUtil.encodeHTMLSpecials(wrapper.toString()), writer);
		} catch (DocumentException e) {
			LOG.error(e);
		}
		writer.flush();
		writer.close();
	}
}
