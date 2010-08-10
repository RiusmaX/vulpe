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

/**
 * Controller Interface.
 *
 * @author <a href="mailto:felipe.matos@activethread.com.br">Felipe Matos</a>
 * @version 1.0
 * @since 1.0
 */

public interface VulpeController extends VulpeSimpleController {

	/**
	 * Method to control add detail.
	 *
	 * @since 1.0
	 * @return Navigation.
	 */
	String addDetail();

	/**
	 * Method to create new record.
	 *
	 * @since 1.0
	 * @return Navigation.
	 */
	String create();

	/**
	 * Method to confirm create.
	 *
	 * @since 1.0
	 * @return Navigation.
	 */
	String createPost();

	/**
	 * Method to delete record.
	 *
	 * @since 1.0
	 * @return Navigation.
	 */
	String delete();

	/**
	 * Method to delete detail items.
	 *
	 * @since 1.0
	 * @return Navigation.
	 */
	String deleteDetail();

	/**
	 * Method to prepare show.
	 *
	 * @since 1.0
	 * @return Navigation
	 */
	String prepare();

	/**
	 * Method to confirm logic tabulate.
	 *
	 * @since 1.0
	 * @return Navigation.
	 */
	String tabularPost();

	/**
	 * Method to update.
	 *
	 * @since 1.0
	 * @return Navigation.
	 */
	String update();

	/**
	 * Method to confirm update.
	 *
	 * @since 1.0
	 * @return Navigation.
	 */
	String updatePost();

	/**
	 * Checks if entity is valid.
	 *
	 * @return
	 */
	boolean validateEntity();

	String autocomplete();

	void buttonControl(final String button, final boolean show);

	void hideButton(final String button);

	void hideButtons(final String... buttons);

	String json();

	void deleteHide(final String detail);

	void deleteDetailShow(final String detail);

	boolean isDeleteDetailShow(final String detail);

	void addDetailHide(final String detail);

	void addDetailShow(final String detail);

	boolean isAddDetailShow(final String detail);

	boolean isAddDetailShow();
}
