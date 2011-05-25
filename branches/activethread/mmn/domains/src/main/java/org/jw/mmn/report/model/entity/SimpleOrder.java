package org.jw.mmn.report.model.entity;

import org.jw.mmn.publications.model.entity.Publication;

public class SimpleOrder implements Comparable<SimpleOrder> {

	private Publication publication;

	private Integer quantity;

	public SimpleOrder() {
	}

	public SimpleOrder(final Publication publication, final Integer quantity) {
		this.publication = publication;
		this.quantity = quantity;
	}

	public Publication getPublication() {
		return publication;
	}

	public void setPublication(Publication publication) {
		this.publication = publication;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public int compareTo(SimpleOrder o) {
		return publication.getName().compareTo(o.getPublication().getName());
	}

}
