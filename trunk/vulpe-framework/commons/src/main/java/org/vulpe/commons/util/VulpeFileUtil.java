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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.vulpe.commons.beans.DownloadInfo;
import org.vulpe.commons.helper.VulpeCacheHelper;
import org.vulpe.exception.VulpeSystemException;

/**
 * Utility class to manipulate files.
 *
 * @author <a href="mailto:fabio.viana@vulpe.org">Fábio Viana</a>
 */
public class VulpeFileUtil {

	protected VulpeFileUtil() {
		// default constructor
	}

	/**
	 * Returns resource properties by resource name
	 */
	public static Properties getResourceProperties(final String resourceName) {
		final VulpeCacheHelper cache = VulpeCacheHelper.getInstance();
		if (!cache.contains(resourceName)) {
			final InputStream iStream = Thread.currentThread()
					.getContextClassLoader().getResourceAsStream(resourceName);
			final Properties properties = new Properties();
			try {
				properties.load(iStream);
			} catch (IOException e) {
				throw new VulpeSystemException(e);
			}
			cache.put(resourceName, properties);
		}
		return cache.get(resourceName);
	}

	public static byte[][] openFiles(final File[] files) {
		try {
			byte[][] bytes = new byte[files.length][];
			for (int i = 0; i < files.length; i++) {
				bytes[i] = FileUtils.readFileToByteArray(files[i]);
			}
			return bytes;
		} catch (IOException e) {
			throw new VulpeSystemException(e);
		}
	}

	public static DownloadInfo getDownloadInfo(final Object value,
			final String contentType, final String contentDisposition) {
		DownloadInfo downloadInfo = null;
		if (value instanceof byte[]) {
			downloadInfo = new DownloadInfo((byte[]) value, contentType,
					contentDisposition);
		} else if (value instanceof Byte[]) {
			final Byte[] bytes = ((Byte[]) value);
			byte[] newBytes = new byte[bytes.length];
			for (int i = 0; i < bytes.length; i++) {
				newBytes[i] = bytes[i].byteValue();
			}
			downloadInfo = new DownloadInfo(newBytes, contentType,
					contentDisposition);
		} else if (value instanceof InputStream) {
			downloadInfo = new DownloadInfo((InputStream) value, contentType,
					contentDisposition);
		} else if (value instanceof DownloadInfo) {
			downloadInfo = (DownloadInfo) value;
		}
		return downloadInfo;
	}
}