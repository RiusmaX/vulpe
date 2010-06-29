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

import org.vulpe.controller.commons.VulpeControllerConfig;
import org.vulpe.model.services.Services;
import org.vulpe.security.context.VulpeSecurityContext;

public interface VulpeBaseSimpleController {

	/**
	 * Method returns current date time of system.
	 * 
	 * @since 1.0
	 * @return VulpeDate.
	 */
	Date getSystemDate();

	/**
	 * Method returns current year from date of system.
	 * 
	 * @since 1.0
	 * @return Year.
	 */
	int getCurrentYear();

	/**
	 * Method returns current month from date of system.
	 * 
	 * @since 1.0
	 * @return Month.
	 */
	int getCurrentMonth();

	/**
	 * Method returns current day from date of system.
	 * 
	 * @since 1.0
	 * @return Day.
	 */
	int getCurrentDay();

	/**
	 * Method to invoke services.
	 * 
	 * @param eventName
	 *            Name of event
	 * @param serviceName
	 *            Name of service
	 * @param argsType
	 *            Types of arguments
	 * @param argsValues
	 *            Arguments values
	 * 
	 * @since 1.0
	 * @return Object
	 */
	Object invokeServices(final String eventName, final String serviceName,
			final Class<?>[] argsType, final Object[] argsValues);

	/**
	 * Return current action configuration.
	 * 
	 * @since 1.0
	 * @return ActionConfig object for current action.
	 */
	VulpeControllerConfig getControllerConfig();

	/**
	 * Method find specific service returns POJO or EJB implementation.
	 * 
	 * @param serviceClass
	 * @return Service Implementation.
	 * @since 1.0
	 * @see Services
	 */
	<T extends Services> T getService(final Class<T> serviceClass);

	/**
	 * Retrieves current action service configured.
	 * 
	 * @since 1.0
	 * @return Service Implementation.
	 * @see Services
	 */
	Services getService();

	/**
	 * Retrieves map of cached class
	 * 
	 * @return Map with cached classes
	 */
	Map<String, Object> getCachedClass();

	/**
	 * Retrieves map of cached enumeration
	 * 
	 * @return Map with cached enumerations
	 */
	Map<String, Object> getCachedEnum();

	/**
	 * Retrieves map of cached enumeration in array to checkbox list
	 * 
	 * @return Map with cached enumerations array
	 */
	Map<String, String> getCachedEnumArray();

	/**
	 * Retrieves current security context.
	 * 
	 * @return VulpeSecurityContext Interface
	 * @since 1.0
	 */
	VulpeSecurityContext getSecurityContext();


	/**
	 * Retrieves user authenticated.
	 * 
	 * @return
	 */
	String getUserAuthenticated();

	/**
	 * Method to prepare back-end show.
	 * 
	 * @since 1.0
	 * @return Navigation
	 */
	String backend();

	/**
	 * Method to prepare front-end show.
	 * 
	 * @since 1.0
	 * @return Navigation
	 */
	String frontend();

	/**
	 * Method to download file.
	 * 
	 * @since 1.0
	 * @return Navigation.
	 */
	String download();

	/**
	 * Method to upload file.
	 * 
	 * @since 1.0
	 * @return Navigation.
	 */
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

	/**
	 * Retrieves a Spring Bean by name.
	 * 
	 * @param <T>
	 *            Class type to return
	 * @param beanName
	 *            Name of Component/Service/Repository
	 * @return Bean converted to Class type
	 * @since 1.0
	 */
	<T> T getBean(final String beanName);

	/**
	 * Retrieves a Spring Bean by class.
	 * 
	 * @param <T>
	 *            Class type to return
	 * @param clazz
	 *            Component/Service/Repository class
	 * @return Bean converted to Class type
	 * @since 1.0
	 */
	<T> T getBean(final Class<T> clazz);

	/**
	 * Retrieves a Session Attribute.
	 * 
	 * @param <T>
	 *            Class type to return
	 * @param attributeName
	 *            Name of Session Attribute
	 * @return Attribute converted to Class type
	 * @since 1.0
	 */
	<T> T getSessionAttribute(final String attributeName);

	/**
	 * Retrieves a Request Attribute.
	 * 
	 * @param <T>
	 *            Class type to return
	 * @param attributeName
	 *            Name of Request Attribute
	 * @return Attribute converted to Class type
	 * @since 1.0
	 */
	<T> T getRequestAttribute(final String attributeName);

}
