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
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;

public class VulpeZipUtil {

	public static byte[] zip(final Map<String, FileInputStream> streams, final String outputFile) {
		byte[] buffer = new byte[1024];

		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			final FileOutputStream fos = new FileOutputStream(outputFile);

			final ZipOutputStream out = new ZipOutputStream(fos);
			for (final String key : streams.keySet()) {
				final FileInputStream stream = streams.get(key);
				final ZipInputStream zin = new ZipInputStream(new ByteArrayInputStream(IOUtils
						.toByteArray(stream)));
				out.putNextEntry(new ZipEntry(key));
				int len = 0;
				while ((len = zin.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
				out.closeEntry();
				zin.close();
				stream.close();
			}

			out.close();

		} catch (IOException e) {
		}
		return baos.toByteArray();
	}

	public static byte[] zipBytes(final Map<String, Byte[]> streams) {
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			final ZipOutputStream out = new ZipOutputStream(baos);
			for (final String key : streams.keySet()) {
				final Byte[] value = streams.get(key);
				byte[] bytes = new byte[value.length];
				for (int i = 0; i < value.length; i++) {
					bytes[i] = value[i].byteValue();
				}
				out.putNextEntry(new ZipEntry(key));
				out.write(bytes);
				out.closeEntry();
			}

			out.close();

		} catch (IOException e) {
		}
		return baos.toByteArray();
	}

}
