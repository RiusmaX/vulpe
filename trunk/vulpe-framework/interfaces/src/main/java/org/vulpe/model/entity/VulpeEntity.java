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
@SuppressWarnings("rawtypes")
public interface VulpeEntity<ID extends Serializable & Comparable> extends Serializable, Comparable<VulpeEntity<ID>> {

	Object clone();

	ID getId();

	void setId(final ID id);

	boolean isFakeId();

	void setFakeId(final boolean fakeId);

	boolean isSelected();

	void setSelected(final boolean selected);

	boolean isUsed();

	void setUsed(final boolean used);

	boolean isConditional();

	void setConditional(final boolean conditional);

	Integer getRowNumber();

	void setRowNumber(final Integer row);

	String getOrderBy();

	void setOrderBy(final String orderBy);

	boolean isAuditable();

	boolean isHistoryAuditable();

	String toXMLAudit();

	String getAutocomplete();

	void setAutocomplete(final String autoComplete);

	String getAutocompleteTerm();

	void setAutocompleteTerm(final String autoCompleteTerm);

	Map<String, Object> map();

	void map(final Map<String, Object> map);

	void setQueryConfigurationName(final String queryConfigurationName);

	String getQueryConfigurationName();

	Map<String, String> fieldColumnMap();

	List<VulpeEntity<?>> getDeletedDetails();

	void setDeletedDetails(List<VulpeEntity<?>> deletedDetails);
}