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

	void manageButtons(final Operation operation);

	void json();

	void select();

	void tabular();

	void tabularFilter();

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

	void read();

	public enum Operation {

		NONE("none"), ADD_DETAIL("addDetail"), CREATE("create"), CREATE_POST("createPost"), CLONE(
				"clone"), DELETE("delete"), DELETE_DETAIL("deleteDetail"), DELETE_FILE("deleteFile"), UPDATE(
				"update"), UPDATE_POST("updatePost"), PERSIST("persist"), TABULAR("tabular"), TABULAR_POST(
				"tabularPost"), TWICE("twice"), PREPARE("prepare"), SELECT("select"), REPORT_EMPTY(
				"report"), REPORT_SUCCESS("report"), VIEW("view"), READ("read"), READ_DELETED(
				"read"), FIND("find"), PAGING("paging"), BACKEND("backend"), FRONTEND("frontend"), DEFINE(
				"define"), DOWNLOAD("download"), UPLOAD("upload");
		private String value;

		private Operation(final String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}

	}

}
