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
package org.vulpe.commons.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.vulpe.commons.file.FileUtil;
import org.vulpe.commons.beans.DownloadInfo;
import org.vulpe.commons.cache.VulpeCacheHelper;
import org.vulpe.exception.VulpeSystemException;

/**
 * Utility class to manipulate files.
 *
 * @author <a href="mailto:fabio.viana@activethread.com.br">F�bio Viana</a>
 */
public class FileUtil {

	/**
	 * Returns FileUtil instance
	 */
	public static FileUtil getInstance() {
		final VulpeCacheHelper cache = VulpeCacheHelper.getInstance();
		if (!cache.contains(FileUtil.class)) {
			cache.put(FileUtil.class, new FileUtil());
		}
		return cache.get(FileUtil.class);
	}

	protected FileUtil() {
		// default constructor
	}

	/**
	 * Returns resource properties by resource name
	 */
	public Properties getResourceProperties(final String resourceName) {
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

	public byte[][] openFiles(final File[] files) {
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

	public DownloadInfo getDownloadInfo(final Object value,
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