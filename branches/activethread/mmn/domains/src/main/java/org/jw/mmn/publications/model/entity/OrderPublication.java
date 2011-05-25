package org.jw.mmn.publications.model.entity;

import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;
import org.vulpe.view.annotations.input.VulpeCheckbox;
import org.vulpe.view.annotations.input.VulpeSelectPopup;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.output.VulpeColumn;

import org.jw.mmn.publications.model.entity.Order;
import org.jw.mmn.publications.model.entity.Publication;

@SuppressWarnings("serial")
public class OrderPublication extends VulpeBaseDB4OEntity<Long> {

	private Order order;

	@VulpeText(mask = "I", size = 5, maxlength = 5, required = true)
	private Integer quantity;

	@VulpeSelectPopup(name = "publicacao", identifier = "id", description = "nome", action = "/publicacoes/Publication/select", popupWidth = 420, size = 35, required = true)
	private Publication publication;

	@VulpeColumn(align = "center")
	@VulpeCheckbox(argument = true, fieldValue = "true")
	private boolean delivered;

	@VulpeText(mask = "I", size = 5, maxlength = 5)
	private Integer quantityDelivered;

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Publication getPublication() {
		return publication;
	}

	public void setPublication(Publication publication) {
		this.publication = publication;
	}

	public boolean isDelivered() {
		return delivered;
	}

	public void setDelivered(boolean delivered) {
		this.delivered = delivered;
	}

	public Integer getQuantityDelivered() {
		return quantityDelivered;
	}

	public void setQuantityDelivered(Integer quantityDelivered) {
		this.quantityDelivered = quantityDelivered;
	}

}
