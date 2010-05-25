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
package org.vulpe.controller;

import java.util.Date;
import java.util.Map;

import org.vulpe.model.services.Services;
import org.vulpe.security.authentication.UserAuthenticatedService;

public interface VulpeBaseSimpleController {

	Date getSystemDate();

	int getCurrentYear();

	int getCurrentMonth();

	int getCurrentDay();

	Object invokeServices(final String eventName, final String serviceName,
			final Class<?>[] argsType, final Object[] argsValues);

	Services getService();

	<T extends Services> T getService(final Class<T> serviceClass);

	Map<String, Object> getCachedClass();

	Map<String, Object> getCachedEnum();

	Map<String, String> getCachedEnumArray();

	boolean isAuthenticated();

	UserAuthenticatedService getUserAuthenticated();

	String getUserName();

	String frontend();

	String download();

	String upload();

	boolean isAjax();

	void setAjax(final boolean ajax);

	String getOperation();

	void setOperation(final String operation);

	boolean isUploaded();

	void setUploaded(final boolean uploaded);

	String getOnHideMessages();

	void setOnHideMessages(final String onHideMessages);

	boolean isBack();

	void setBack(final boolean back);

	boolean isExecuted();

	void setExecuted(final boolean executed);

	String getDownloadKey();

	void setDownloadKey(final String downloadKey);

	String getDownloadContentType();

	void setDownloadContentType(final String downloadContentType);

	String getDownloadContentDisposition();

	void setDownloadContentDisposition(final String downloadContentDisposition);

	String getPopupKey();

	void setPopupKey(final String popupKey);

	boolean isPopup();

	void setUrlBack(final String urlBack);

	void setLayerUrlBack(final String layerUrlBack);

	boolean isShowTitle();

	void setShowTitle(boolean showTitle);

}
