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
package org.vulpe.controller.struts.interceptor;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;
import org.apache.struts2.interceptor.FileUploadInterceptor;
import org.vulpe.commons.VulpeConstants;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.config.annotations.VulpeUpload;
import org.vulpe.controller.AbstractVulpeBaseController;
import org.vulpe.controller.VulpeController;
import org.vulpe.controller.commons.MultipleResourceBundle;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ValidationAware;

/**
 *
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 *
 */
@SuppressWarnings({ "unchecked", "serial", "rawtypes" })
public class VulpeUploadInterceptor extends FileUploadInterceptor {

	private static final Logger LOG = Logger.getLogger(VulpeUploadInterceptor.class);

	/*
	 * (non-Javadoc)
	 *
	 * @seeorg.apache.struts2.interceptor.FileUploadInterceptor#intercept(com.
	 * opensymphony.xwork2.ActionInvocation)
	 */
	@Override
	public String intercept(final ActionInvocation invocation) throws Exception {
		final VulpeUpload upload = VulpeConfigHelper.getApplicationConfiguration().upload();
		setMaximumSize(new Long(upload.maximumSize() * 1048576));
		if (!"*".equals(upload.allowedTypes())) {
			setAllowedTypes(upload.allowedTypes());
		}
		final ActionContext actionContext = invocation.getInvocationContext();
		final HttpServletRequest request = (HttpServletRequest) actionContext
				.get(ServletActionContext.HTTP_REQUEST);
		// sets the files in the session to parameters.
		List<Object[]> fileList = (List<Object[]>) actionContext.getSession().get(
				VulpeConstants.Upload.FILES);
		if (fileList != null) {
			for (Object[] object : fileList) {
				final String inputName = (String) object[0];
				if (!actionContext.getParameters().containsKey(inputName)) {
					actionContext.getParameters().put(inputName, object[1]);
					actionContext.getParameters().put(inputName.concat("ContentType"), object[2]);
					actionContext.getParameters().put(inputName.concat("FileName"), object[3]);
				}
			}
			fileList.clear();
		}

		if (invocation.getAction() instanceof VulpeController) {
			if (!"upload".equals(invocation.getProxy().getMethod())) {
				return super.intercept(invocation);
			}
		}
		if (!(request instanceof MultiPartRequestWrapper)) {
			if (LOG.isDebugEnabled()) {
				final ActionProxy proxy = invocation.getProxy();
				LOG.debug(getText("vulpe.message.bypass.request",
						new Object[] { proxy.getNamespace(), proxy.getActionName() }));
			}
			return invocation.invoke();
		}

		final Object action = invocation.getAction();
		ValidationAware validation = null;

		if (action instanceof ValidationAware) {
			validation = (ValidationAware) action;
		}

		final MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) request;
		if (multiWrapper.hasErrors()) {
			for (final Iterator errorIterator = multiWrapper.getErrors().iterator(); errorIterator
					.hasNext();) {
				final String error = (String) errorIterator.next();
				// the request was rejected because its size (9014884) exceeds
				// the configured maximum (2097152)
				if (validation != null) {
					validation.addActionError(error);
				}
				LOG.error(error);
			}
		}

		// Bind allowed Files
		Enumeration fpNames = multiWrapper.getFileParameterNames();
		while (fpNames != null && fpNames.hasMoreElements()) {
			// get the value of this input tag
			final String inputName = (String) fpNames.nextElement();

			// get the content type
			final String[] contentType = multiWrapper.getContentTypes(inputName);

			if (isNotEmpty(contentType)) {
				// get the name of the file from the input tag
				final String[] fileName = multiWrapper.getFileNames(inputName);
				if (isNotEmpty(fileName)) {
					// Get a File object for the uploaded File
					final File[] files = multiWrapper.getFiles(inputName);
					if (ArrayUtils.isNotEmpty(files)) {
						if (fileList == null) {
							fileList = new ArrayList();
						}
						byte[][] bytes = new byte[files.length][];
						for (int index = 0; index < files.length; index++) {
							if (acceptFile(files[index], contentType[index], inputName, validation,
									actionContext.getLocale())) {
								bytes[index] = FileUtils.readFileToByteArray(files[index]);
							}
						}
						fileList.add(new Object[] { inputName,
								(files.length == 1 ? bytes[0] : bytes), contentType, fileName });
					}
				} else {
					LOG.error(getText("vulpe.message.invalid.file", new Object[] { inputName }));
				}
			} else {
				LOG.error(getText("vulpe.message.invalid.content.type", new Object[] { inputName }));
			}
		}

		if (fileList != null && !fileList.isEmpty()) {
			actionContext.getSession().put(VulpeConstants.Upload.FILES, fileList);
		}

		// invoke action
		final String result = invocation.invoke();

		// cleanup
		fpNames = multiWrapper.getFileParameterNames();
		while (fpNames != null && fpNames.hasMoreElements()) {
			final String inputValue = (String) fpNames.nextElement();
			final File[] file = multiWrapper.getFiles(inputValue);
			for (int index = 0; index < file.length; index++) {
				final File currentFile = file[index];
				LOG.info(getText("vulpe.message.removing.file", new Object[] { inputValue,
						currentFile }));
				if ((currentFile != null) && currentFile.isFile()) {
					currentFile.delete();
				}
			}
		}

		return result;
	}

	/**
	 *
	 * @param objArray
	 * @return
	 */
	protected static boolean isNotEmpty(final Object[] objArray) {
		boolean result = false;
		for (int index = 0; index < objArray.length && !result; index++) {
			if (objArray[index] != null) {
				result = true;
			}
		}
		return result;
	}

	public String getText(final String key) {
		return MultipleResourceBundle.getInstance().getString(key);
	}

	public String getText(final String key, final Object... args) {
		return MessageFormat.format(getText(key), args);
	}

	/**
	 * Override for added functionality. Checks if the proposed file is
	 * acceptable based on contentType and size.
	 *
	 * @param file
	 *            - proposed upload file.
	 * @param contentType
	 *            - contentType of the file.
	 * @param inputName
	 *            - inputName of the file.
	 * @param validation
	 *            - Non-null ValidationAware if the action implements
	 *            ValidationAware, allowing for better logging.
	 * @param locale
	 * @return true if the proposed file is acceptable by contentType and size.
	 */
	protected boolean acceptFile(File file, String contentType, String inputName,
			ValidationAware validation, Locale locale) {
		boolean fileIsAcceptable = false;

		// If it's null the upload failed
		if (file == null) {
			// String errMsg = getText("vulpe.error.uploading", new Object[] {
			// inputName });
			final String message = getText("vulpe.error.uploading");
			if (validation != null) {
				// validation.addFieldError(inputName, message);
				validation.addActionError(message);
			}
			LOG.error(message);
		} else if (maximumSize != null && maximumSize.longValue() < file.length()) {
			final VulpeUpload upload = VulpeConfigHelper.getApplicationConfiguration().upload();
			// String errMsg = getText("vulpe.error.file.too.large", new
			// Object[] { inputName, file.getName(),
			// "" + file.length() });
			final String message = getText("vulpe.error.file.too.large", upload.maximumSize());
			if (validation != null) {
				// validation.addFieldError(inputName, message);
				validation.addActionError(message);
				final AbstractVulpeBaseController baseController = (AbstractVulpeBaseController) validation;
				baseController.now.put("fileUploadError", "too.large");
			}

			LOG.error(message);
		} else if ((!allowedTypesSet.isEmpty()) && (!containsItem(allowedTypesSet, contentType))) {
			// String errMsg = getText("vulpe.error.content.type.not.allowed",
			// new Object[] { inputName, file.getName(),
			// contentType });
			final String message = getText("vulpe.error.content.type.not.allowed", new Object[] {
					inputName, file.getName(), contentType });
			if (validation != null) {
				// validation.addFieldError(inputName, message);
				validation.addActionError(message);
			}

			LOG.error(message);
		} else {
			fileIsAcceptable = true;
		}

		return fileIsAcceptable;
	}

	/**
	 * @param itemCollection
	 *            - Collection of string items (all lowercase).
	 * @param key
	 *            - Key to search for.
	 * @return true if itemCollection contains the key, false otherwise.
	 */
	private static boolean containsItem(Collection itemCollection, String key) {
		return itemCollection.contains(key.toLowerCase());
	}

}