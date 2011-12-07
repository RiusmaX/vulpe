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
