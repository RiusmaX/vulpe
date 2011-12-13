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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

/**
 *
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 *
 */
public final class VulpeDigestUtil {

	private static final Logger LOG = Logger.getLogger(VulpeDigestUtil.class);

	private VulpeDigestUtil() {
	}

	private static final String HEX_DIGITS = "0123456789abcdef";

	/**
	 * Do digest on byte array with specified algorithm.
	 *
	 * @param input
	 * @param algorithm
	 * @return byte[]
	 * @throws NoSuchAlgorithmException
	 */
	public static byte[] digest(byte[] input, final String algorithm)
			throws NoSuchAlgorithmException {
		MessageDigest digest = MessageDigest.getInstance(algorithm);
		digest.reset();
		return digest.digest(input);
	}

	/**
	 * Convert o byte array to hex.
	 *
	 * @param input
	 * @return
	 */
	public static String byteArrayToHexString(final byte[] bytes) {
		final StringBuffer buffer = new StringBuffer();

		for (int i = 0; i < bytes.length; i++) {
			final int index = ((int) bytes[i]) & 0xFF;
			buffer.append(HEX_DIGITS.charAt(index / 16));
			buffer.append(HEX_DIGITS.charAt(index % 16));
		}

		return buffer.toString();
	}

	/**
	 * Convert hex String to byte array.
	 *
	 * @param hexa
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static byte[] hexStringToByteArray(final String hexa) throws IllegalArgumentException {
		if (hexa.length() % 2 != 0) {
			throw new IllegalArgumentException("Invalid hex string");
		}

		final byte[] bytes = new byte[hexa.length() / 2];

		for (int i = 0; i < hexa.length(); i += 2) {
			bytes[i / 2] = (byte) ((HEX_DIGITS.indexOf(hexa.charAt(i)) << 4) | (HEX_DIGITS
					.indexOf(hexa.charAt(i + 1))));
		}
		return bytes;
	}

	public static String encrypt(final String value, final String algorithm) {
		try {
			byte[] bytes = digest(value.getBytes(), algorithm);
			return byteArrayToHexString(bytes);
		} catch (NoSuchAlgorithmException e) {
			LOG.error(e);
		}
		return value;
	}
}