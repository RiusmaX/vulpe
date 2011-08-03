package org.jw.mmn.report.model.entity;

import lombok.Data;

import org.jw.mmn.publications.model.entity.Publication;

@Data
public class SimpleOrder implements Comparable<SimpleOrder> {

	private Publication publication;

	private Integer quantity;

	public SimpleOrder() {
	}

	public SimpleOrder(final Publication publication, final Integer quantity) {
		this.publication = publication;
		this.quantity = quantity;
	}

	@Override
	public int compareTo(SimpleOrder o) {
		return publication.getName().compareTo(o.getPublication().getName());
	}

}
