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

import org.vulpe.model.services.VulpeService;

/**
 * Vulpe Controller Config Interface.
 *
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * @version 1.0
 * @since 1.0
 */
public interface VulpeControllerConfig {

	ControllerType getControllerType();

	void setControllerType(final ControllerType controllerType);

	Class<? extends VulpeService> getServiceClass();

	int getPageSize();

	boolean isRequireOneFilter();

	String[] requireOneOfFilters();

	int getTabularPageSize();

	boolean isTabularShowFilter();

	String getOwnerController();

	String getControllerName();

	String getSimpleControllerName();

	String getFormName();

	String getSelectFormName();

	String getMainFormName();

	boolean isShowInTabs();

	String getReportFormat();

	String getReportDataSourceName();

	String getReportName();

	boolean isReportDownload();

	String getReportFile();

	String getParentName(final String detail);

	String getTitleKey();

	String getReportTitleKey();

	boolean isSimple();

	void setSimple(final boolean simple);

	String getViewItemsPath();

	String getViewPath();

	String[] getSubReports();

	String getReportControllerName();

	String getModuleName();

	String getMasterTitleKey();

	void setViewSelectItemsPath(final String viewSelectItemsPath);

	String getViewSelectItemsPath();

	void setViewMainPath(final String viewMainPath);

	String getViewMainPath();

	void setViewSelectPath(final String viewSelectPath);

	String getViewSelectPath();

	String getViewBaseName();

	boolean isOnlyUpdateDetails();

	boolean isNewOnPost();

	/**
	 * Controllers type
	 *
	 * @author <a href="mailto:fabio.viana@vulpe.org">Fábio Viana</a>
	 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
	 */
	public enum ControllerType {
		MAIN, TWICE, TABULAR, SELECT, REPORT, BACKEND, FRONTEND, ALL, NONE
	}
}
