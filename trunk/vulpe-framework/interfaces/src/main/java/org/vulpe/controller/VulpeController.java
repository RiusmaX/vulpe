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

import org.vulpe.controller.commons.VulpeControllerConfig.ControllerType;
import org.vulpe.model.services.VulpeService;
import org.vulpe.security.context.VulpeSecurityContext;

/**
 * Controller Interface.
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 */

public interface VulpeController extends Serializable {

	/**
	 * Method to control add detail.
	 * 
	 * @since 1.0
	 */
	void addDetail();

	/**
	 * Method to control paging.
	 * 
	 */
	void paging();

	/**
	 * Method to clear screen.
	 * 
	 * @since 1.0
	 */
	void clear();

	/**
	 * Method to create new record.
	 * 
	 * @since 1.0
	 */
	void create();

	/**
	 * Method to confirm create.
	 * 
	 * @since 1.0
	 */
	void createPost();

	/**
	 * Method to clone record.
	 * 
	 * @since 1.0
	 */
	void cloneIt();

	/**
	 * Method to delete record.
	 * 
	 * @since 1.0
	 */
	void delete();

	/**
	 * Method to delete detail items.
	 * 
	 * @since 1.0
	 */
	void deleteDetail();

	/**
	 * Method to delete file uploaded.
	 * 
	 * @since 1.0
	 */
	void deleteFile();

	/**
	 * Method to prepare to show.
	 * 
	 * @since 1.0
	 * @return Navigation
	 */
	void prepare();

	/**
	 * Method to confirm logic tabulate.
	 * 
	 * @since 1.0
	 */
	void tabularPost();

	/**
	 * Method to update.
	 * 
	 * @since 1.0
	 */
	void update();

	/**
	 * Method to confirm update.
	 * 
	 * @since 1.0
	 */
	void updatePost();

	/**
	 * Checks if entity is valid.
	 * 
	 * @return
	 */
	boolean validateEntity();

	void autocomplete();

	/**
	 * Method to config button.
	 * 
	 * @param button
	 *            Button
	 * @param values
	 *            Array values (1st=render 2nd=show 3th=disabled) (true|false)
	 * @since 1.0
	 */
	void configButton(final String button, final boolean... values);

	/**
	 * Method to config button.
	 * 
	 * @param button
	 *            Button
	 * @param config
	 *            Config
	 * @param value
	 *            Value (true|false)
	 * @since 1.0
	 */
	void configButton(final String button, final String config, final boolean value);

	void manageButtons(final Operation operation);

	/**
	 * Method to render buttons.
	 * 
	 * @param buttons
	 *            Buttons.
	 * @since 1.0
	 */
	void renderButtons(final String... buttons);

	void renderButtons(final ControllerType controllerType, final String... buttons);

	/**
	 * Method to not render buttons.
	 * 
	 * @param buttons
	 *            Buttons.
	 * @since 1.0
	 */
	void notRenderButtons(final String... buttons);

	/**
	 * Method to show buttons.
	 * 
	 * @param buttons
	 *            Buttons.
	 * @since 1.0
	 */
	void showButtons(final String... buttons);

	/**
	 * Method to enable buttons.
	 * 
	 * @param buttons
	 *            Buttons.
	 * @since 1.0
	 */
	void enableButtons(final String... buttons);

	void showButtons(final ControllerType controllerType, final String... buttons);

	/**
	 * Method to hide buttons.
	 * 
	 * @param buttons
	 *            Buttons.
	 * @since 1.0
	 */
	void hideButtons(final String... buttons);

	/**
	 * Method to disable buttons.
	 * 
	 * @param buttons
	 *            Buttons.
	 * @since 1.0
	 */
	void disableButtons(final String... buttons);

	void json();

	void renderDetailButton(final String detail, final String button);

	void notRenderDetailButton(final String detail, final String button);

	void showDetailButton(final String detail, final String button);

	void hideDetailButton(final String detail, final String button);

	void enableDetailButton(final String detail, final String button);

	void disableDetailButton(final String detail, final String button);

	void select();

	void tabular();

	void tabularFilter();

	boolean isUploaded();

	void setUploaded(final boolean uploaded);

	String getDownloadKey();

	void setDownloadKey(final String downloadKey);

	String getDownloadContentType();

	void setDownloadContentType(final String downloadContentType);

	String getDownloadContentDisposition();

	void setDownloadContentDisposition(final String downloadContentDisposition);

	/**
	 * Method to download file.
	 * 
	 * @since 1.0
	 */
	void download();

	/**
	 * Method to upload file.
	 * 
	 * @since 1.0
	 */
	void upload();

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
	void backend();

	/**
	 * Method to prepare front-end show.
	 * 
	 * @since 1.0
	 * @return Navigation
	 */
	void frontend();

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
	 * Define Result Result to render normal or AJAX request
	 */
	void controlResultForward();

	/**
	 * Method retrieve forward.
	 * 
	 * @since 1.0
	 * @return Result Result.
	 */
	String getResultForward();

	void setResultForward(final String resultForward);

	/**
	 * Method retrieve result.
	 * 
	 * @since 1.0
	 * @return Result Name.
	 */
	String getResultName();

	void setResultName(final String resultName);

	/**
	 * Mount return to view by simple page name or full path.
	 * 
	 * @param page
	 */
	void returnToPage(final String page);

	void read();

	/**
	 * Redirect to URL.
	 * 
	 * @param url
	 */
	void redirectTo(final String url);

	/**
	 * Redirect to URL using AJAX.
	 * 
	 * @param url
	 * @param ajax
	 */
	void redirectTo(final String url, final boolean ajax);

	void setUrlRedirect(String urlRedirect);

	String getUrlRedirect();

	public enum Operation {

		ADD_DETAIL("addDetail"), CREATE("create"), CREATE_POST("createPost"), CLONE("clone"), DELETE(
				"delete"), DELETE_DETAIL("deleteDetail"), DELETE_FILE("deleteFile"), UPDATE(
				"update"), UPDATE_POST("updatePost"), PERSIST("persist"), TABULAR("tabular"), TABULAR_POST(
				"tabularPost"), TWICE("twice"), PREPARE("prepare"), SELECT("select"), REPORT_EMPTY(
				"report"), REPORT_SUCCESS("report"), VIEW("view"), READ("read"), READ_DELETED("read"), FIND("find"), PAGING(
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
