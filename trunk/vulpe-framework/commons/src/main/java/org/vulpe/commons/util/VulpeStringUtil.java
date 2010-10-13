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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.log4j.Logger;

/**
 * Vulpe String Utility class.
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * @version 1.0
 * @since 1.0
 */
public class VulpeStringUtil {

	private static VulpeHashMap<Character, String> accentMap = new VulpeHashMap<Character, String>();

	private static final Logger LOG = Logger.getLogger(VulpeStringUtil.class);

	static {
		accentMap.put("�".charAt(0), "a");
		accentMap.put("�".charAt(0), "a");
		accentMap.put("�".charAt(0), "a");
		accentMap.put("�".charAt(0), "a");
		accentMap.put("�".charAt(0), "a");

		accentMap.put("�".charAt(0), "A");
		accentMap.put("�".charAt(0), "A");
		accentMap.put("�".charAt(0), "A");
		accentMap.put("�".charAt(0), "A");
		accentMap.put("�".charAt(0), "A");

		accentMap.put("�".charAt(0), "e");
		accentMap.put("�".charAt(0), "e");
		accentMap.put("�".charAt(0), "e");
		accentMap.put("�".charAt(0), "e");

		accentMap.put("�".charAt(0), "E");
		accentMap.put("�".charAt(0), "E");
		accentMap.put("�".charAt(0), "E");
		accentMap.put("�".charAt(0), "E");

		accentMap.put("�".charAt(0), "i");
		accentMap.put("�".charAt(0), "i");
		accentMap.put("�".charAt(0), "i");
		accentMap.put("�".charAt(0), "i");

		accentMap.put("�".charAt(0), "I");
		accentMap.put("�".charAt(0), "I");
		accentMap.put("�".charAt(0), "I");
		accentMap.put("�".charAt(0), "I");

		accentMap.put("�".charAt(0), "o");
		accentMap.put("�".charAt(0), "o");
		accentMap.put("�".charAt(0), "o");
		accentMap.put("�".charAt(0), "o");
		accentMap.put("�".charAt(0), "o");

		accentMap.put("�".charAt(0), "O");
		accentMap.put("�".charAt(0), "O");
		accentMap.put("�".charAt(0), "O");
		accentMap.put("�".charAt(0), "O");
		accentMap.put("�".charAt(0), "O");

		accentMap.put("�".charAt(0), "u");
		accentMap.put("�".charAt(0), "u");
		accentMap.put("�".charAt(0), "u");
		accentMap.put("�".charAt(0), "u");

		accentMap.put("�".charAt(0), "U");
		accentMap.put("�".charAt(0), "U");
		accentMap.put("�".charAt(0), "U");
		accentMap.put("�".charAt(0), "U");
	}

	/**
	 * String accent normalize
	 *
	 * @param term
	 * @return
	 */
	public static String normalize(final String term) {
		final StringBuilder normalized = new StringBuilder();
		for (int i = 0; i < term.length(); i++) {
			normalized.append(accentMap.containsKey(term.charAt(i)) ? accentMap.get(term.charAt(i))
					: term.charAt(i));
		}
		return normalized.toString();
	}

	/**
	 *
	 * @param value
	 * @return
	 */
	public static String capitalize(final String value) {
		final String[] parts = value.toLowerCase().split("\\s");
		final StringBuilder cap = new StringBuilder();
		if (parts.length > 1) {
			int count = 0;
			for (String part : parts) {
				cap.append(upperCaseFirst(part));
				if (count < parts.length) {
					cap.append(" ");
				}
				++count;
			}
		} else {
			cap.append(upperCaseFirst(value.toLowerCase()));
		}
		return cap.toString();
	}

	/**
	 * Puts first char in upper case.
	 *
	 * @param value
	 * @return
	 */
	public static String upperCaseFirst(final String value) {
		return value.substring(0, 1).toUpperCase() + value.substring(1);
	}

	/**
	 * Puts first char in lower case.
	 *
	 * @param value
	 * @return
	 */
	public static String lowerCaseFirst(final String value) {
		return value.substring(0, 1).toLowerCase() + value.substring(1);
	}

	/**
	 * Convert SQL Blob to String.
	 *
	 * @param blob
	 * @return
	 */
	public static String blobToString(final Blob blob) {
		String blobString = null;
		try {
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			final byte[] buffer = new byte[1024];
			final InputStream inputStream = blob.getBinaryStream();
			int count = 0;
			while ((count = inputStream.read(buffer)) >= 0) {
				baos.write(buffer, 0, count);
			}
			inputStream.close();
			final byte[] bytes = baos.toByteArray();
			blobString = new String(bytes);
		} catch (Exception e) {
			LOG.error(e);
		}
		return blobString;
	}

	/**
	 * Convert SQL Blob to String.
	 *
	 * @param blob
	 * @return
	 */
	public static Blob stringToBlob(final String string) {
		Blob blob = null;
		try {
			blob = new SerialBlob(string.getBytes());
		} catch (Exception e) {
			LOG.error(e);
		}
		return blob;
	}

	public static boolean isInteger(final String input) {
		try {
			Integer.parseInt(input);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
