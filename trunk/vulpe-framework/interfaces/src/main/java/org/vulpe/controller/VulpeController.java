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
