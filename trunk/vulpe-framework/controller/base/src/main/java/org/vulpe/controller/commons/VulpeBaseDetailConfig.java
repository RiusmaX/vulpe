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
package org.vulpe.controller.commons;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.vulpe.commons.VulpeConstants.View.Layout;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.commons.annotations.Quantity;
import org.vulpe.view.tags.Functions;

@SuppressWarnings( { "serial", "unchecked" })
public class VulpeBaseDetailConfig implements Serializable {

	private String name;
	private String propertyName;
	private String simpleName;
	private String titleKey;
	private boolean addNewDetailsOnTop;
	private int pageSize;
	private int newDetails;
	private int startNewDetails;
	private String[] despiseFields;
	private String viewPath;
	private Quantity quantiity;
	private VulpeBaseDetailConfig parentDetailConfig;
	private boolean showAsAccordion;
	private boolean showFilter;
	private List<VulpeBaseDetailConfig> subDetails = new LinkedList<VulpeBaseDetailConfig>();

	public VulpeBaseDetailConfig() {
		this.newDetails = 1;
	}

	public VulpeBaseDetailConfig(final String name) {
		this();
		this.name = name;
		this.propertyName = name;
		setSimpleName();
	}

	public VulpeBaseDetailConfig(final String name, final String propertyName, final int startNewDetails,
			final int newDetails, final boolean addNewDetailsOnTop, final boolean showFilter,
			final String[] despiseFields) {
		this.name = name;
		this.propertyName = propertyName;
		this.startNewDetails = startNewDetails == 0 ? 1 : startNewDetails;
		this.newDetails = newDetails == 0 ? 1 : newDetails;
		this.despiseFields = despiseFields.clone();
		this.addNewDetailsOnTop = addNewDetailsOnTop;
		this.showFilter = showFilter;
		setSimpleName();
	}

	public VulpeBaseDetailConfig(final String name, final String propertyName, final int detailNews,
			final String[] despiseFields, final Quantity quantity) {
		this.name = name;
		this.propertyName = propertyName;
		this.newDetails = detailNews;
		this.despiseFields = despiseFields.clone();
		this.quantiity = quantity;
		setSimpleName();
	}

	public String getBaseName() {
		return Functions.clearChars(Functions.replaceSequence(name, "[", "]", ""), ".");
	}

	public String getName() {
		return name;
	}

	public VulpeBaseDetailConfig getParentDetailConfig() {
		return parentDetailConfig;
	}

	public String[] getDespiseFields() {
		return despiseFields.clone();
	}

	public List<VulpeBaseDetailConfig> getSubDetails() {
		return subDetails;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public String getTitleKey() {
		return titleKey;
	}

	public void setTitleKey(final String titleKey) {
		this.titleKey = titleKey;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setupDetail(final VulpeBaseControllerConfig config, final DetailConfig detail) {
		if (detail == null) {
			return;
		}

		if (!detail.name().equals("")) {
			this.name = detail.name();
			this.propertyName = detail.name();
			setSimpleName();
		}

		if (!detail.propertyName().equals("")) {
			this.propertyName = detail.propertyName();
			setSimpleName();
		}

		this.viewPath = config.getViewPath().substring(0, StringUtils.lastIndexOf(config.getViewPath(), '/')).concat(
				"/").concat(getBaseName()).concat(Layout.SUFFIX_JSP_DETAIL);

		if (StringUtils.isEmpty(getTitleKey()) && StringUtils.isNotEmpty(getPropertyName())) {
			setTitleKey(config.getTitleKey().concat(".").concat(getBaseName()));
		}

		if (detail.despiseFields().length > 0) {
			this.despiseFields = detail.despiseFields();
		}

		if (detail.startNewDetails() > 0) {
			this.setStartNewDetails(detail.startNewDetails());
		}

		if (detail.newDetails() > 0) {
			this.setNewDetails(detail.newDetails());
		}

		this.quantiity = detail.quantity();

		if (!detail.parentDetailName().equals("")) {
			if (config.getDetail(detail.parentDetailName()) == null) {
				config.getDetails().add(new VulpeBaseDetailConfig(detail.parentDetailName()));
			}
			this.parentDetailConfig = (VulpeBaseDetailConfig) config.getDetail(detail.parentDetailName());
			this.parentDetailConfig.getSubDetails().add(this);
		}
		this.addNewDetailsOnTop = detail.addNewDetailsOnTop();
		this.showAsAccordion = detail.showAsArccodion();
		this.pageSize = detail.pageSize();
	}

	private void setSimpleName() {
		if (StringUtils.isNotEmpty(this.propertyName)) {
			if (StringUtils.lastIndexOf(this.propertyName, '.') >= 0) {
				this.simpleName = this.propertyName.substring(StringUtils.lastIndexOf(this.propertyName, '.') + 1);
			} else {
				this.simpleName = this.propertyName;
			}
		}
	}

	public void setViewPath(final String viewPath) {
		this.viewPath = viewPath;
	}

	public String getViewPath() {
		return viewPath;
	}

	public void setStartNewDetails(int startNewDetails) {
		this.startNewDetails = startNewDetails;
	}

	public int getStartNewDetails() {
		return startNewDetails;
	}

	public void setNewDetails(int newDetails) {
		this.newDetails = newDetails;
	}

	public int getNewDetails() {
		return newDetails;
	}

	public void setQuantity(Quantity quantity) {
		this.quantiity = quantity;
	}

	public Quantity getQuantity() {
		return quantiity;
	}

	public void setAddNewDetailsOnTop(boolean addNewDetailsOnTop) {
		this.addNewDetailsOnTop = addNewDetailsOnTop;
	}

	public boolean isAddNewDetailsOnTop() {
		return addNewDetailsOnTop;
	}

	public void setShowAsAccordion(boolean showAsAccordion) {
		this.showAsAccordion = showAsAccordion;
	}

	public boolean isShowAsAccordion() {
		return showAsAccordion;
	}

	public void setShowFilter(boolean showFilter) {
		this.showFilter = showFilter;
	}

	public boolean isShowFilter() {
		return showFilter;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageSize() {
		return pageSize;
	}

}