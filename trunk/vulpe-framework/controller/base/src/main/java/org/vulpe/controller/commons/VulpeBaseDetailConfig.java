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
package org.vulpe.controller.commons;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.vulpe.commons.VulpeConstants.View.Layout;
import org.vulpe.commons.annotations.DetailConfig;
import org.vulpe.commons.annotations.Quantity;
import org.vulpe.commons.helper.VulpeConfigHelper;
import org.vulpe.config.annotations.VulpeView;
import org.vulpe.view.tags.Functions;

@SuppressWarnings( { "serial", "unchecked", "rawtypes" })
public class VulpeBaseDetailConfig implements Serializable {

	private String name;
	private String propertyName;
	private String simpleName;
	private String titleKey;
	private boolean addNewDetailsOnTop;
	private boolean notControlView;
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
			final int newDetails, final boolean reverse, final boolean showFilter,
			final String[] despiseFields) {
		this.name = name;
		this.propertyName = propertyName;
		this.startNewDetails = startNewDetails == 0 ? 1 : startNewDetails;
		this.newDetails = newDetails == 0 ? 1 : newDetails;
		this.despiseFields = despiseFields.clone();
		final VulpeView view = VulpeConfigHelper.getApplicationConfiguration().view();
		this.addNewDetailsOnTop = reverse ? !view.addNewDetailsOnTop() : view.addNewDetailsOnTop();
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
		final VulpeView view = VulpeConfigHelper.getApplicationConfiguration().view();
		this.addNewDetailsOnTop = detail.reverse() ? !view.addNewDetailsOnTop() : view.addNewDetailsOnTop();
		this.notControlView = detail.notControlView();
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

	public void setNotControlView(boolean notControlView) {
		this.notControlView = notControlView;
	}

	public boolean isNotControlView() {
		return notControlView;
	}

}