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
		accentMap.put("á".charAt(0), "a");
		accentMap.put("â".charAt(0), "a");
		accentMap.put("ã".charAt(0), "a");
		accentMap.put("à".charAt(0), "a");
		accentMap.put("ä".charAt(0), "a");

		accentMap.put("Á".charAt(0), "A");
		accentMap.put("Â".charAt(0), "A");
		accentMap.put("Ã".charAt(0), "A");
		accentMap.put("À".charAt(0), "A");
		accentMap.put("Ä".charAt(0), "A");

		accentMap.put("é".charAt(0), "e");
		accentMap.put("è".charAt(0), "e");
		accentMap.put("ë".charAt(0), "e");
		accentMap.put("ê".charAt(0), "e");

		accentMap.put("É".charAt(0), "E");
		accentMap.put("Ê".charAt(0), "E");
		accentMap.put("È".charAt(0), "E");
		accentMap.put("Ë".charAt(0), "E");

		accentMap.put("í".charAt(0), "i");
		accentMap.put("ì".charAt(0), "i");
		accentMap.put("î".charAt(0), "i");
		accentMap.put("ï".charAt(0), "i");

		accentMap.put("Í".charAt(0), "I");
		accentMap.put("Ì".charAt(0), "I");
		accentMap.put("Î".charAt(0), "I");
		accentMap.put("Ï".charAt(0), "I");

		accentMap.put("ó".charAt(0), "o");
		accentMap.put("ò".charAt(0), "o");
		accentMap.put("õ".charAt(0), "o");
		accentMap.put("ô".charAt(0), "o");
		accentMap.put("ö".charAt(0), "o");

		accentMap.put("Ó".charAt(0), "O");
		accentMap.put("Ò".charAt(0), "O");
		accentMap.put("Õ".charAt(0), "O");
		accentMap.put("Ô".charAt(0), "O");
		accentMap.put("Ö".charAt(0), "O");

		accentMap.put("ú".charAt(0), "u");
		accentMap.put("ù".charAt(0), "u");
		accentMap.put("ü".charAt(0), "u");
		accentMap.put("ü".charAt(0), "u");

		accentMap.put("Ú".charAt(0), "U");
		accentMap.put("Ù".charAt(0), "U");
		accentMap.put("Û".charAt(0), "U");
		accentMap.put("Ü".charAt(0), "U");

		// (')
		specialChars.put("á".charAt(0), "&aacute;");
		specialChars.put("é".charAt(0), "&eacute;");
		specialChars.put("í".charAt(0), "&iacute;");
		specialChars.put("ó".charAt(0), "&oacute;");
		specialChars.put("ú".charAt(0), "&uacute;");
		specialChars.put("Á".charAt(0), "&Aacute;");
		specialChars.put("É".charAt(0), "&Eacute;");
		specialChars.put("Í".charAt(0), "&Iacute;");
		specialChars.put("Ó".charAt(0), "&Oacute;");
		specialChars.put("Ú".charAt(0), "&Uacute;");
		// (~)
		specialChars.put("ã".charAt(0), "&atilde;");
		specialChars.put("ñ".charAt(0), "&ntilde;");
		specialChars.put("õ".charAt(0), "&otilde;");
		specialChars.put("Ã".charAt(0), "&Atilde;");
		specialChars.put("Ñ".charAt(0), "&Ntilde;");
		specialChars.put("Õ".charAt(0), "&Otilde;");
		// (^)
		specialChars.put("â".charAt(0), "&acirc;");
		specialChars.put("ê".charAt(0), "&ecirc;");
		specialChars.put("î".charAt(0), "&icirc;");
		specialChars.put("ô".charAt(0), "&ocirc;");
		specialChars.put("û".charAt(0), "&ucirc;");
		specialChars.put("Â".charAt(0), "&Acirc;");
		specialChars.put("Ê".charAt(0), "&Ecirc;");
		specialChars.put("Î".charAt(0), "&Icirc;");
		specialChars.put("Ô".charAt(0), "&Ocirc;");
		specialChars.put("Û".charAt(0), "&Ucirc;");
		// (ç Ç)
		specialChars.put("ç".charAt(0), "&ccedil;");
		specialChars.put("Ç".charAt(0), "&Ccedil;");
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
	public static String encodeUTF(final String value) {
		final StringBuilder encoded = new StringBuilder();
		for (int i = 0; i < value.length(); i++) {
			encoded.append(utfChars.containsKey(value.charAt(i)) ? utfChars.get(value.charAt(i))
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
			encoded.append(specialChars.containsKey(value.charAt(i)) ? specialChars.get(value
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
			LOG.error(e);
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
		int firstPos = value.indexOf("[");
		int lastPos = value.lastIndexOf("]");
		String parent = "";
		if (firstPos > 0) {
			parent = value.substring(0, firstPos);
		}
		value = value.substring(firstPos + 1, lastPos);
		final List<Integer> positions = new ArrayList<Integer>();
		for (int i = 0; i < value.length(); i++) {
			if (value.charAt(i) == ",".charAt(0)) {
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

}