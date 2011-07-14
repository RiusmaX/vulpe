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
	public Map<String, Object> getMap() {
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
	public void setMap(Map<String, Object> map) {

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setConditional(boolean conditional) {
		// TODO Auto-generated method stub
		
	}

}
