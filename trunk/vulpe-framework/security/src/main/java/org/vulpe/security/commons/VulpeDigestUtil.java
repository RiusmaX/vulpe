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
package org.vulpe.security.commons;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.log4j.Logger;

/**
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
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