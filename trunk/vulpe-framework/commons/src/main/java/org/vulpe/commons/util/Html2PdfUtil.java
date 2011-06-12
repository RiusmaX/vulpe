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
package org.vulpe.commons.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.dom4j.DocumentException;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

/**
 * HTML 2 PDF utility class.
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 */
public class Html2PdfUtil {

	public static void convert(String input, OutputStream out) throws DocumentException {
		convert(new ByteArrayInputStream(input.getBytes()), out);
	}

	public static void convert(InputStream input, OutputStream out) throws DocumentException {
		final Tidy tidy = new Tidy();
		final Document doc = tidy.parseDOM(input, null);
		final ITextRenderer renderer = new ITextRenderer();
		renderer.setDocument(doc, null);
		renderer.layout();
		try {
			renderer.createPDF(out);
		} catch (com.lowagie.text.DocumentException e) {
			e.printStackTrace();
		}
	}
}
