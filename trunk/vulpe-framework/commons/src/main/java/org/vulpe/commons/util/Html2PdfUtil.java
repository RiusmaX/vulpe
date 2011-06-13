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
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;
import org.w3c.dom.Document;
import org.w3c.tidy.Tidy;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.pdf.BaseFont;

/**
 * HTML 2 PDF utility class.
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 */
public class Html2PdfUtil {

	protected final static Logger LOG = Logger.getLogger(Html2PdfUtil.class);

	public static void convert(String input, OutputStream out) throws DocumentException {
		convert(new ByteArrayInputStream(input.getBytes()), out);
	}

	public static void convert(InputStream input, OutputStream out) throws DocumentException {
		try {
			final Tidy tidy = new Tidy();
			final Document doc = tidy.parseDOM(input, null);
			final ITextRenderer renderer = new ITextRenderer();
			final File liberationSansRegular = new File(
					getRealPath("/reports/fonts/liberation_sans/LiberationSans-Regular.ttf"));
			final File liberationSansBold = new File(
					getRealPath("/reports/fonts/liberation_sans/LiberationSans-Bold.ttf"));
			final File liberationSansBoldItalic = new File(
					getRealPath("/reports/fonts/liberation_sans/LiberationSans-BoldItalic.ttf"));
			final File liberationSansItalic = new File(
					getRealPath("/reports/fonts/liberation_sans/LiberationSans-Italic.ttf"));
			renderer.getFontResolver().addFont(liberationSansRegular.getAbsolutePath(), BaseFont.IDENTITY_H,
					BaseFont.EMBEDDED);
			renderer.getFontResolver().addFont(liberationSansBold.getAbsolutePath(), BaseFont.IDENTITY_H,
					BaseFont.EMBEDDED);
			renderer.getFontResolver().addFont(liberationSansBoldItalic.getAbsolutePath(), BaseFont.IDENTITY_H,
					BaseFont.EMBEDDED);
			renderer.getFontResolver().addFont(liberationSansItalic.getAbsolutePath(), BaseFont.IDENTITY_H,
					BaseFont.EMBEDDED);
			// final BaseFont font = BaseFont.createFont(file.getAbsolutePath(),
			// BaseFont.IDENTITY_H,
			// BaseFont.NOT_EMBEDDED);
			// final String fontFamilyName = TrueTypeUtil.getFamilyName(font);
			renderer.setDocument(doc, null);
			renderer.layout();
			renderer.createPDF(out);
		} catch (Exception e) {
			LOG.error(e);
		}
	}

	

	private static String getRealPath(final String fileName) {
		String realPath = "";
		final URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
		if (url != null) {
			realPath = url.getPath();
		} else {
			realPath = Html2PdfUtil.class.getResource(fileName).getPath();
		}
		return realPath.replaceAll("%20", " ");
	}
}
