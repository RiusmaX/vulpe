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
package org.vulpe.commons.beans;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

@SuppressWarnings("serial")
public class DownloadInfo implements Serializable {
	private String key;
	private String contentType;
	private String contentLength;
	private String contentDisposition = "inline";
	private InputStream inputStream;
	private String name;

	public DownloadInfo() {
		// default constructor
	}

	public DownloadInfo(final byte data[], final String contentType) {
		this(data, contentType, null);
	}

	public DownloadInfo(final InputStream inputStream, final String contentType) {
		this(inputStream, contentType, null);
	}

	public DownloadInfo(final byte data[], final String contentType,
			final String contentDisposition) {
		final byte[] newData = data.clone();
		this.inputStream = new ByteArrayInputStream(newData);
		this.contentType = contentType;
		this.contentLength = String.valueOf(newData.length);
		if (StringUtils.isNotEmpty(contentDisposition)) {
			this.contentDisposition = contentDisposition;
		}
	}

	public DownloadInfo(final InputStream inputStream, final String contentType,
			final String contentDisposition) {
		try {
			final byte data[] = IOUtils.toByteArray(inputStream);
			this.inputStream = new ByteArrayInputStream(data);
			this.contentLength = String.valueOf(data.length);
		} catch (IOException e) {
			this.inputStream = inputStream;
		}
		this.contentType = contentType;
		if (StringUtils.isNotEmpty(contentDisposition)) {
			this.contentDisposition = contentDisposition;
		}
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(final InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(final String contentType) {
		this.contentType = contentType;
	}

	public String getContentLength() {
		return contentLength;
	}

	public void setContentLength(final String contentLength) {
		this.contentLength = contentLength;
	}

	public String getContentDisposition() {
		return contentDisposition;
	}

	public void setContentDisposition(final String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}

	public String getKey() {
		return key;
	}

	public void setKey(final String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}
}