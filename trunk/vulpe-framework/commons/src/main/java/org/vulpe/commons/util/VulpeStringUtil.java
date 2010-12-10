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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * Vulpe String Utility class.
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 */
public class VulpeStringUtil {

	private static VulpeHashMap<Character, String> accentMap = new VulpeHashMap<Character, String>();
	private static final VulpeHashMap<Character, String> utfChars = new VulpeHashMap<Character, String>();
	private static final VulpeHashMap<Character, String> specialChars = new VulpeHashMap<Character, String>();

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

		// (�)
		specialChars.put("�".charAt(0), "&aacute;");
		specialChars.put("�".charAt(0), "&eacute;");
		specialChars.put("�".charAt(0), "&iacute;");
		specialChars.put("�".charAt(0), "&oacute;");
		specialChars.put("�".charAt(0), "&uacute;");
		specialChars.put("�".charAt(0), "&Aacute;");
		specialChars.put("�".charAt(0), "&Eacute;");
		specialChars.put("�".charAt(0), "&Iacute;");
		specialChars.put("�".charAt(0), "&Oacute;");
		specialChars.put("�".charAt(0), "&Uacute;");
		// (~)
		specialChars.put("�".charAt(0), "&atilde;");
		specialChars.put("�".charAt(0), "&ntilde;");
		specialChars.put("�".charAt(0), "&otilde;");
		specialChars.put("�".charAt(0), "&Atilde;");
		specialChars.put("�".charAt(0), "&Ntilde;");
		specialChars.put("�".charAt(0), "&Otilde;");
		// (^)
		specialChars.put("�".charAt(0), "&acirc;");
		specialChars.put("�".charAt(0), "&ecirc;");
		specialChars.put("�".charAt(0), "&icirc;");
		specialChars.put("�".charAt(0), "&ocirc;");
		specialChars.put("�".charAt(0), "&ucirc;");
		specialChars.put("�".charAt(0), "&Acirc;");
		specialChars.put("�".charAt(0), "&Ecirc;");
		specialChars.put("�".charAt(0), "&Icirc;");
		specialChars.put("�".charAt(0), "&Ocirc;");
		specialChars.put("�".charAt(0), "&Ucirc;");
		// (� �)
		specialChars.put("�".charAt(0), "&ccedil;");
		specialChars.put("�".charAt(0), "&Ccedil;");

		// (�)
		utfChars.put("�".charAt(0), "=E1");
		utfChars.put("�".charAt(0), "=E9");
		utfChars.put("�".charAt(0), "=ED");
		utfChars.put("�".charAt(0), "=F3");
		utfChars.put("�".charAt(0), "=FA");
		utfChars.put("�".charAt(0), "=C1");
		utfChars.put("�".charAt(0), "=C9");
		utfChars.put("�".charAt(0), "=CD");
		utfChars.put("�".charAt(0), "=D3");
		utfChars.put("�".charAt(0), "=DA");
		// (~)
		utfChars.put("�".charAt(0), "=E3");
		utfChars.put("�".charAt(0), "=F1");
		utfChars.put("�".charAt(0), "=F5");
		utfChars.put("�".charAt(0), "=C3");
		utfChars.put("�".charAt(0), "=D1");
		utfChars.put("�".charAt(0), "=D5");
		// (^)
		utfChars.put("�".charAt(0), "=E2");
		utfChars.put("�".charAt(0), "=EA");
		utfChars.put("�".charAt(0), "=EE");
		utfChars.put("�".charAt(0), "=F4");
		utfChars.put("�".charAt(0), "=FB");
		utfChars.put("�".charAt(0), "=C2");
		utfChars.put("�".charAt(0), "=CA");
		utfChars.put("�".charAt(0), "=CE");
		utfChars.put("�".charAt(0), "=D4");
		utfChars.put("�".charAt(0), "=DB");
		// (� �)
		utfChars.put("�".charAt(0), "=E7");
		utfChars.put("�".charAt(0), "=C7");
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
			normalized.append(accentMap.containsKey(term.charAt(i)) ? accentMap.get(term.charAt(i)) : term.charAt(i));
		}
		return normalized.toString();
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static String encodeUTF(final String value) {
		final StringBuilder encoded = new StringBuilder();
		for (int i = 0; i < value.length(); i++) {
			encoded.append(utfChars.containsKey(value.charAt(i)) ? utfChars.get(value.charAt(i)) : value.charAt(i));
		}
		return encoded.toString();
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	public static String encodeHTMLSpecials(final String value) {
		final StringBuilder encoded = new StringBuilder();
		for (int i = 0; i < value.length(); i++) {
			encoded.append(specialChars.containsKey(value.charAt(i)) ? specialChars.get(value.charAt(i)) : value
					.charAt(i));
		}
		return encoded.toString();
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

	public static int count(final String value, final String token) {
		int count = 0;
		for (int i = 0; i < value.length(); i++) {
			if (value.charAt(i) == token.charAt(0)) {
				++count;
			}
		}
		return count;
	}

	public static int count(final String value, final String tokenBegin, final String tokenEnd) {
		String[] valueParts = value.split(tokenBegin);
		int count = 0;
		for (String string : valueParts) {
			if (string.contains(tokenEnd)) {
				break;
			}
			++count;
		}
		return count;
	}

	public static String separateWords(final String value) {
		final StringBuilder newValue = new StringBuilder();
		for (int i = 0; i < value.length(); i++) {
			if (i > 0 && StringUtils.isAllUpperCase(value.substring(i, i + 1))) {
				newValue.append(" ").append(value.charAt(i));
			} else {
				newValue.append(value.charAt(i));
			}
		}
		return newValue.toString();
	}
}
