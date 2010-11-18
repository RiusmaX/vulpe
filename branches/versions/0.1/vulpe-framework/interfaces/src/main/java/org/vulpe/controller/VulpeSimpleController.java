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

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.vulpe.controller.commons.VulpeControllerConfig;
import org.vulpe.model.services.VulpeService;
import org.vulpe.security.context.VulpeSecurityContext;

/**
 * Simple Controller Interface.
 *
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 */
public interface VulpeSimpleController extends Serializable {

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
	 * @see VulpeService
	 */
	<T extends VulpeService> T getService(final Class<T> serviceClass);

	/**
	 * Retrieves current action service configured.
	 *
	 * @since 1.0
	 * @return Service Implementation.
	 * @see VulpeService
	 */
	VulpeService getService();

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

	boolean isAjax();

	void setAjax(final boolean ajax);

	Operation getOperation();

	void setOperation(final Operation operation);

	String getOnHideMessages();

	void setOnHideMessages(final String onHideMessages);

	boolean isBack();

	void setBack(final boolean back);

	boolean isExecuted();

	void setExecuted(final boolean executed);

	String getPopupKey();

	void setPopupKey(final String popupKey);

	boolean isPopup();

	void setUrlBack(final String urlBack);

	void setLayerUrlBack(final String layerUrlBack);

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
	 * Sets attribute on session scope.
	 *
	 * @param attributeName
	 * @param attributeValue
	 */
	void setSessionAttribute(final String attributeName, final Object attributeValue);

	/**
	 * Retrieves a attribute on request scope.
	 *
	 * @param <T>
	 *            Class type to return
	 * @param attributeName
	 *            Name of Request Attribute
	 * @return Attribute converted to Class type
	 * @since 1.0
	 */
	<T> T getRequestAttribute(final String attributeName);

	/**
	 * Sets attribute on request scope.
	 *
	 * @param attributeName
	 * @param attributeValue
	 */
	void setRequestAttribute(final String attributeName, final Object attributeValue);

	/**
	 * Retrieves key of select form object (entitySelect) attribute on session
	 * scope.
	 *
	 * @return
	 */
	String getSelectFormKey();

	/**
	 * Retrieves key of select table object (entities) attribute on session
	 * scope.
	 *
	 * @return
	 */
	String getSelectTableKey();

	/**
	 * Retrieves key of select paging object (paging) attribute on session
	 * scope.
	 *
	 * @return
	 */
	String getSelectPagingKey();

	/**
	 * Retrieves current HTTP Request.
	 *
	 * @return Http Servlet Request
	 */
	HttpServletRequest getRequest();

	/**
	 * Retrieves current HTTP Response.
	 *
	 * @return Http Servlet Reponse
	 */
	HttpServletResponse getResponse();

	/**
	 * Retrieves current HTTP Session.
	 *
	 * @return Http Session
	 */
	HttpSession getSession();

	/**
	 * Define Result Forward to render normal or AJAX request
	 */
	void controlResultForward();

	/**
	 * Method retrieve forward.
	 *
	 * @since 1.0
	 * @return Result Forward.
	 */
	String getResultForward();

	void setResultForward(final String resultForward);

	/**
	 * Mount return to view by simple page name or full path.
	 *
	 * @param page
	 */
	void returnToPage(final String page);

	/**
	 * Redirect to URL.
	 *
	 * @param url
	 * @return
	 */
	String redirectTo(final String url);

	/**
	 * Redirect to URL using AJAX.
	 *
	 * @param url
	 * @param ajax
	 * @return
	 */
	String redirectTo(final String url, final boolean ajax);

	public enum Operation {

		ADD_DETAIL("addDetail"), CREATE("create"), CREATE_POST("createPost"), DELETE("delete"), DELETE_DETAIL(
				"deleteDetail"), UPDATE("update"), UPDATE_POST("updatePost"), PERSIST("persist"), TABULAR(
				"tabular"), TABULAR_POST("tabularPost"), TWICE("twice"), PREPARE("prepare"), SELECT(
				"select"), REPORT("report"), VIEW("view"), READ("read"), FIND("find"), PAGING(
				"paging"), BACKEND("backend"), FRONTEND("frontend"), DEFINE("define"), DOWNLOAD(
				"download"), UPLOAD("upload");
		private String value;

		private Operation(final String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	}

}
