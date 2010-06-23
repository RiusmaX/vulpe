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