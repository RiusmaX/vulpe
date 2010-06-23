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

public interface VulpeBaseController extends VulpeBaseSimpleController {

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
}
