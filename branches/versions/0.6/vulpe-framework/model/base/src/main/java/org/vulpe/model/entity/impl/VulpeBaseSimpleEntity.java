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
package org.vulpe.model.entity.impl;

import java.util.List;
import java.util.Map;

import org.jfree.util.Log;
import org.vulpe.model.entity.VulpeEntity;

/**
 * 
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 * 
 */
@SuppressWarnings( { "serial" })
public class VulpeBaseSimpleEntity implements VulpeEntity<Long>, Cloneable {

	@Override
	public String getAutocomplete() {
		return null;
	}
	
	@Override
	public String getAutocompleteTerm() {
		return null;
	}

	@Override
	public List<VulpeEntity<?>> getDeletedDetails() {
		return null;
	}

	@Override
	public Long getId() {
		return null;
	}

	@Override
	public Map<String, Object> map() {
		return null;
	}

	@Override
	public String getOrderBy() {
		return null;
	}

	@Override
	public String getQueryConfigurationName() {
		return null;
	}

	@Override
	public boolean isAuditable() {
		return false;
	}

	@Override
	public boolean isFakeId() {
		return false;
	}

	@Override
	public boolean isHistoryAuditable() {
		return false;
	}

	@Override
	public boolean isSelected() {
		return false;
	}

	@Override
	public void setAutocomplete(String autoComplete) {

	}
	
	@Override
	public void setAutocompleteTerm(String autoCompleteTerm) {

	}

	@Override
	public void setDeletedDetails(List<VulpeEntity<?>> deletedDetails) {

	}

	@Override
	public void setFakeId(boolean fakeId) {

	}

	@Override
	public void map(Map<String, Object> map) {

	}

	@Override
	public void setOrderBy(String orderBy) {
	}

	@Override
	public void setQueryConfigurationName(String queryConfigurationName) {

	}

	@Override
	public void setSelected(boolean selected) {

	}

	@Override
	public String toXMLAudit() {
		return null;
	}

	@Override
	public void setId(Long id) {
	}

	@Override
	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			Log.error(e);
		}
		return null;
	}

	@Override
	public int compareTo(VulpeEntity<Long> o) {
		return 0;
	}

	@Override
	public Integer getRowNumber() {
		return null;
	}

	@Override
	public void setRowNumber(Integer rowNumber) {
	}

	@Override
	public boolean isUsed() {
		return false;
	}

	@Override
	public void setUsed(boolean used) {
	}

	@Override
	public boolean isConditional() {
		return false;
	}

	@Override
	public void setConditional(boolean conditional) {
	}

	@Override
	public Map<String, String> fieldColumnMap() {
		return null;
	}

}
