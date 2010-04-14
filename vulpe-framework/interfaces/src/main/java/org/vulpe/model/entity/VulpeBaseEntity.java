package org.vulpe.model.entity;

import java.io.Serializable;

/**
 * Default entity Interface
 *
 * @param <ID>
 *            Type of entity identifier
 * @author <a href="mailto:fabio.viana@activethread.com.br">Fábio Viana</a>
 * @author <a href="mailto:geraldo.matos@activethread.com.br">Geraldo Matos</a>
 */
@SuppressWarnings("unchecked")
public interface VulpeBaseEntity<ID extends Serializable & Comparable> extends
		Serializable, Comparable<VulpeBaseEntity<ID>> {
	ID getId();

	void setId(final ID id);

	boolean isSelected();

	void setSelected(final boolean selected);

	String getOrderBy();

	void setOrderBy(final String orderBy);

	boolean isAuditable();

	boolean isHistoryAuditable();

	String toXMLAudit();
}