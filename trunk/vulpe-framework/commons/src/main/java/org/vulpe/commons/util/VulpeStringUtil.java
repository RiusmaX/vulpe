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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Vulpe String Utility class.
 *
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 */
public class VulpeStringUtil {

	private static VulpeHashMap<Character, String> ACCENT_MAP = new VulpeHashMap<Character, String>();
	private static final VulpeHashMap<Character, String> UTF_CHARS = new VulpeHashMap<Character, String>();
	private static final VulpeHashMap<Character, String> SPECIAL_CHARS = new VulpeHashMap<Character, String>();

	private static final Logger LOG = LoggerFactory.getLogger(VulpeStringUtil.class);

	static {
		ACCENT_MAP.put('á', "a");
		ACCENT_MAP.put('â', "a");
		ACCENT_MAP.put('ã', "a");
		ACCENT_MAP.put('à', "a");
		ACCENT_MAP.put('ä', "a");

		ACCENT_MAP.put('Á', "A");
		ACCENT_MAP.put('Â', "A");
		ACCENT_MAP.put('Ã', "A");
		ACCENT_MAP.put('À', "A");
		ACCENT_MAP.put('Ä', "A");

		ACCENT_MAP.put('é', "e");
		ACCENT_MAP.put('è', "e");
		ACCENT_MAP.put('ë', "e");
		ACCENT_MAP.put('ê', "e");

		ACCENT_MAP.put('É', "E");
		ACCENT_MAP.put('Ê', "E");
		ACCENT_MAP.put('È', "E");
		ACCENT_MAP.put('Ë', "E");

		ACCENT_MAP.put('í', "i");
		ACCENT_MAP.put('ì', "i");
		ACCENT_MAP.put('î', "i");
		ACCENT_MAP.put('ï', "i");

		ACCENT_MAP.put('Í', "I");
		ACCENT_MAP.put('Ì', "I");
		ACCENT_MAP.put('Î', "I");
		ACCENT_MAP.put('Ï', "I");

		ACCENT_MAP.put('ó', "o");
		ACCENT_MAP.put('ò', "o");
		ACCENT_MAP.put('õ', "o");
		ACCENT_MAP.put('ô', "o");
		ACCENT_MAP.put('ö', "o");

		ACCENT_MAP.put('Ó', "O");
		ACCENT_MAP.put('Ò', "O");
		ACCENT_MAP.put('Õ', "O");
		ACCENT_MAP.put('Ô', "O");
		ACCENT_MAP.put('Ö', "O");

		ACCENT_MAP.put('ú', "u");
		ACCENT_MAP.put('ù', "u");
		ACCENT_MAP.put('ü', "u");
		ACCENT_MAP.put('ü', "u");

		ACCENT_MAP.put('Ú', "U");
		ACCENT_MAP.put('Ù', "U");
		ACCENT_MAP.put('Û', "U");
		ACCENT_MAP.put('Ü', "U");

		// (')
		SPECIAL_CHARS.put('á', "&aacute;");
		SPECIAL_CHARS.put('é', "&eacute;");
		SPECIAL_CHARS.put('í', "&iacute;");
		SPECIAL_CHARS.put('ó', "&oacute;");
		SPECIAL_CHARS.put('ú', "&uacute;");
		SPECIAL_CHARS.put('Á', "&Aacute;");
		SPECIAL_CHARS.put('É', "&Eacute;");
		SPECIAL_CHARS.put('Í', "&Iacute;");
		SPECIAL_CHARS.put('Ó', "&Oacute;");
		SPECIAL_CHARS.put('Ú', "&Uacute;");
		// (~)
		SPECIAL_CHARS.put('ã', "&atilde;");
		SPECIAL_CHARS.put('ñ', "&ntilde;");
		SPECIAL_CHARS.put('õ', "&otilde;");
		SPECIAL_CHARS.put('Ã', "&Atilde;");
		SPECIAL_CHARS.put('Ñ', "&Ntilde;");
		SPECIAL_CHARS.put('Õ', "&Otilde;");
		// (^)
		SPECIAL_CHARS.put('â', "&acirc;");
		SPECIAL_CHARS.put('ê', "&ecirc;");
		SPECIAL_CHARS.put('î', "&icirc;");
		SPECIAL_CHARS.put('ô', "&ocirc;");
		SPECIAL_CHARS.put('û', "&ucirc;");
		SPECIAL_CHARS.put('Â', "&Acirc;");
		SPECIAL_CHARS.put('Ê', "&Ecirc;");
		SPECIAL_CHARS.put('Î', "&Icirc;");
		SPECIAL_CHARS.put('Ô', "&Ocirc;");
		SPECIAL_CHARS.put('Û', "&Ucirc;");
		// (ç Ç)
		SPECIAL_CHARS.put('ç', "&ccedil;");
		SPECIAL_CHARS.put('Ç', "&Ccedil;");
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
			normalized.append(ACCENT_MAP.containsKey(term.charAt(i)) ? ACCENT_MAP.get(term.charAt(i))
					: term.charAt(i));
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
			encoded.append(UTF_CHARS.containsKey(value.charAt(i)) ? UTF_CHARS.get(value.charAt(i))
					: value.charAt(i));
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
			encoded.append(SPECIAL_CHARS.containsKey(value.charAt(i)) ? SPECIAL_CHARS.get(value
					.charAt(i)) : value.charAt(i));
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
			LOG.error(e.getMessage());
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
			LOG.error(e.getMessage());
		}
		return blob;
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

	public static String getAttributeName(final String value) {
		String attributeName = value;
		if (StringUtils.isAllUpperCase(value)) {
			attributeName = value.toLowerCase();
		} else {
			attributeName = lowerCaseFirst(value);
		}
		return attributeName;
	}

	public static String encode(final String value, final String encode) {
		String encoded = value;
		try {
			byte[] bytes = value.getBytes(encode);
			encoded = new String(bytes, encode);
		} catch (UnsupportedEncodingException e) {
			LOG.error(e.getMessage());
		}
		return encoded;
	}

	public static String concat(final Object object, final String... properties) {
		final StringBuilder concat = new StringBuilder();
		for (final String property : properties) {
			final Object value = VulpeReflectUtil.getFieldValue(object, property);
			concat.append(String.valueOf(value));
		}
		return concat.toString();
	}

	public static String getValueFromLast(String value, String token) {
		return value.substring(value.lastIndexOf(token) + 1);
	}

	public static List<String> splitHierarchy(String value) {
		final List<String> parts = new ArrayList<String>();
		int firstPos = value.indexOf('[');
		int lastPos = value.lastIndexOf(']');
		String parent = "";
		if (firstPos > 0) {
			parent = value.substring(0, firstPos);
		}
		value = value.substring(firstPos + 1, lastPos);
		final List<Integer> positions = new ArrayList<Integer>();
		for (int i = 0; i < value.length(); i++) {
			if (value.charAt(i) == ',') {
				positions.add(i);
			}
		}
		if (positions.size() == 1) {
			int pos = positions.get(0);
			String cut = value.substring(0, pos);
			parts.add(parent + "." + cut);
			cut = value.substring(pos + 1);
			parts.add(parent + "." + cut);
		} else {
			int pos2 = 0;
			for (int i = 0; i < positions.size(); i++) {
				int pos = positions.get(i);
				String cut = value.substring(pos2, pos);
				int open = VulpeStringUtil.count(cut, "[");
				int close = VulpeStringUtil.count(cut, "]");
				if (cut.contains("[") && cut.contains("]") && open == close) {
					parts.add(parent + "." + cut);
				} else if (cut.contains("[") || cut.contains("]")) {
					if (i == (positions.size() - 1)) {
						cut = value.substring(pos2);
						parts.add(parent + "." + cut);
					}
					continue;
				} else {
					parts.add(parent + "." + cut);
				}
				pos2 = pos + 1;
				if (i == (positions.size() - 1)) {
					cut = value.substring(pos2);
					parts.add(parent + "." + cut);
				}
			}
		}
		return parts;
	}

	public static void main(String[] args) {
		String expressao = "x[teste1[teste1_1,teste1_2[teste1_2_1,teste1_2_2,teste1_2_3[teste1_2_3_1,teste1_2_3_2],teste1_2_4]],teste2,teste3[teste3_1]]";
		for (String string : splitHierarchy(expressao)) {
			System.out.println(string);
		}
	}

}