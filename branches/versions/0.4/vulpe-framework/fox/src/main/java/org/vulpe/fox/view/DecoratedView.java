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
package org.vulpe.fox.view;

import java.util.List;
import java.util.Map;

import org.vulpe.fox.Decorated;

@SuppressWarnings("serial")
public class DecoratedView extends Decorated {

	private String name;
	private String prefixLabelOfView;
	private String prefixLabelOfSelection;
	private String prefixLabelOfSelectionList;
	private String prefixLabelOfMaintenance;
	private String prefixLabelOfTabular;
	private String label;
	private String projectName;
	private String moduleName;
	private String popupProperties;
	private int columnSpan;
	private List<DecoratedViewDetail> details;
	private List<String> types;
	private List<DecoratedViewField> arguments;
	private List<DecoratedViewField> items;
	private List<DecoratedViewField> fields;
	private Map<String, String> labels;

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

	public void setFields(final List<DecoratedViewField> fields) {
		this.fields = fields;
	}

	public List<DecoratedViewField> getFields() {
		return fields;
	}

	public void setTypes(final List<String> types) {
		this.types = types;
	}

	public List<String> getTypes() {
		return types;
	}

	public void setItems(final List<DecoratedViewField> items) {
		this.items = items;
	}

	public List<DecoratedViewField> getItems() {
		return items;
	}

	public void setArguments(final List<DecoratedViewField> arguments) {
		this.arguments = arguments;
	}

	public List<DecoratedViewField> getArguments() {
		return arguments;
	}

	public void setColumnSpan(final int columnSpan) {
		this.columnSpan = columnSpan;
	}

	public int getColumnSpan() {
		return columnSpan;
	}

	public void setPopupProperties(final String popupProperties) {
		this.popupProperties = popupProperties;
	}

	public String getPopupProperties() {
		return popupProperties;
	}

	public void setDetails(final List<DecoratedViewDetail> details) {
		this.details = details;
	}

	public List<DecoratedViewDetail> getDetails() {
		return details;
	}

	public void setLabels(Map<String, String> labels) {
		this.labels = labels;
	}

	public Map<String, String> getLabels() {
		return labels;
	}

	public String getPrefixLabelOfSelection() {
		return prefixLabelOfSelection;
	}

	public void setPrefixLabelOfSelection(String prefixLabelOfSelection) {
		this.prefixLabelOfSelection = prefixLabelOfSelection;
	}

	public String getPrefixLabelOfSelectionList() {
		return prefixLabelOfSelectionList;
	}

	public void setPrefixLabelOfSelectionList(String prefixLabelOfSelectionList) {
		this.prefixLabelOfSelectionList = prefixLabelOfSelectionList;
	}

	public String getPrefixLabelOfMaintenance() {
		return prefixLabelOfMaintenance;
	}

	public void setPrefixLabelOfMaintenance(String prefixLabelOfMaintenance) {
		this.prefixLabelOfMaintenance = prefixLabelOfMaintenance;
	}

	public String getPrefixLabelOfTabular() {
		return prefixLabelOfTabular;
	}

	public void setPrefixLabelOfTabular(String prefixLabelOfTabular) {
		this.prefixLabelOfTabular = prefixLabelOfTabular;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setPrefixLabelOfView(String prefixLabelOfView) {
		this.prefixLabelOfView = prefixLabelOfView;
	}

	public String getPrefixLabelOfView() {
		return prefixLabelOfView;
	}

}