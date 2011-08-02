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
package org.vulpe.fox.all;

import java.util.List;

import org.vulpe.fox.Decorated;
import org.vulpe.fox.controller.DecoratedController;
import org.vulpe.fox.manager.DecoratedManager;
import org.vulpe.fox.view.DecoratedView;

@SuppressWarnings("serial")
public class DecoratedAll extends Decorated {

	private String name;
	private String label;
	private String projectName;
	private String moduleName;
	private DecoratedView view;
	private DecoratedController controller;
	private DecoratedManager manager;
	private List<String> types;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(final String projectName) {
		this.projectName = projectName;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(final String moduleName) {
		this.moduleName = moduleName;
	}

	public void setTypes(final List<String> types) {
		this.types = types;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public DecoratedView getView() {
		return view;
	}

	public void setView(DecoratedView view) {
		this.view = view;
	}

	public DecoratedController getController() {
		return controller;
	}

	public void setController(DecoratedController controller) {
		this.controller = controller;
	}

	public DecoratedManager getManager() {
		return manager;
	}

	public void setManager(DecoratedManager manager) {
		this.manager = manager;
	}

}