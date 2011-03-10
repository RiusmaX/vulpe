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
package org.vulpe.model.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Default entity Interface
 *
 * @param <ID>
 *            Type of entity identifier
 * @author <a href="mailto:fabio.viana@vulpe.org">Fábio Viana</a>
 * @author <a href="mailto:felipe@vulpe.org">Geraldo Felipe</a>
 */
@SuppressWarnings("unchecked")
public interface VulpeEntity<ID extends Serializable & Comparable> extends VulpeSimpleEntity {
	ID getId();

	void setId(final ID id);

	boolean isFakeId();

	void setFakeId(final boolean fakeId);

	boolean isSelected();

	void setSelected(final boolean selected);

	String getOrderBy();

	void setOrderBy(final String orderBy);

	boolean isAuditable();

	boolean isHistoryAuditable();

	String toXMLAudit();

	String getAutocomplete();

	void setAutocomplete(final String autoComplete);

	VulpeEntity<ID> clone();

	Map<String, Object> getMap();

	void setMap(final Map<String, Object> map);

	void setQueryConfigurationName(final String queryConfigurationName);

	String getQueryConfigurationName();

	List<VulpeEntity<?>> getDeletedDetails();

	void setDeletedDetails(List<VulpeEntity<?>> deletedDetails);
}