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
import java.util.Date;

/**
 * Default entity Interface
 *
 * @param <ID>
 *            Type of entity identifier
 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
 * @author <a href="mailto:geraldo.matos@activethread.com.br">Geraldo Matos</a>
 */
@SuppressWarnings("unchecked")
public interface VulpeBaseEntity<ID extends Serializable & Comparable> extends Serializable,
		Comparable<VulpeBaseEntity<ID>>, Cloneable {
	ID getId();

	void setId(final ID id);

	boolean isSelected();

	void setSelected(final boolean selected);

	String getOrderBy();

	void setOrderBy(final String orderBy);

	boolean isAuditable();

	boolean isHistoryAuditable();

	String toXMLAudit();

	String getUserOfLastUpdate();

	void setUserOfLastUpdate(final String username);

	Date getDateOfLastUpdate();

	void setDateOfLastUpdate(final Date date);

	VulpeBaseEntity<ID> clone();
}