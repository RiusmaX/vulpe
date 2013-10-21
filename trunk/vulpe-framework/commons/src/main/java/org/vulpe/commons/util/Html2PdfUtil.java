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
package org.vulpe.commons.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	protected static final Logger LOG = LoggerFactory.getLogger(Html2PdfUtil.class);

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
			LOG.error(e.getMessage());
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
