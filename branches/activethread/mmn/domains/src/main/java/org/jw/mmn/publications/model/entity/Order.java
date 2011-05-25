package org.jw.mmn.publications.model.entity;

import java.util.Date;
import java.util.List;

import org.jw.mmn.core.model.entity.Congregation;
import org.jw.mmn.core.model.entity.Member;
import org.vulpe.model.annotations.Parameter;
import org.vulpe.model.annotations.QueryParameter;
import org.vulpe.model.annotations.Parameter.OperatorType;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;
import org.vulpe.view.annotations.input.VulpeCheckbox;
import org.vulpe.view.annotations.input.VulpeDate;
import org.vulpe.view.annotations.input.VulpeSelectPopup;
import org.vulpe.view.annotations.logic.main.Detail;
import org.vulpe.view.annotations.output.VulpeColumn;

@SuppressWarnings("serial")
public class Order extends VulpeBaseDB4OEntity<Long> {

	@VulpeColumn(sortable = true, attribute = "nome")
	@VulpeSelectPopup(identifier = "id", description = "nome", action = "/core/Member/select", popupWidth = 420, argument = true, required = true, autocomplete = true)
	private Member member;

	@Detail(target = OrderPublication.class)
	private List<OrderPublication> publications;

	@VulpeColumn
	@VulpeDate(required = true)
	private Date date;

	@QueryParameter(equals = @Parameter(name = "date", operator = OperatorType.GREATER_OR_EQUAL))
	private transient Date initialDate;

	@QueryParameter(equals = @Parameter(name = "date", operator = OperatorType.SMALLER_OR_EQUAL))
	private transient Date finalDate;

	@VulpeColumn
	private Date validityDate;

	@VulpeColumn(booleanTo = "{Yes}|{No}")
	@VulpeCheckbox(argument = true, fieldValue = "true")
	private boolean delivered;

	@VulpeColumn
	private Date deliveryDate;

	private Congregation congregation;

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public List<OrderPublication> getPublications() {
		return publications;
	}

	public void setPublications(List<OrderPublication> publications) {
		this.publications = publications;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getInitialDate() {
		return initialDate;
	}

	public void setInitialDate(Date initialDate) {
		this.initialDate = initialDate;
	}

	public Date getFinalDate() {
		return finalDate;
	}

	public void setFinalDate(Date finalDate) {
		this.finalDate = finalDate;
	}

	public Date getValidityDate() {
		return validityDate;
	}

	public void setValidityDate(Date validityDate) {
		this.validityDate = validityDate;
	}

	public boolean isDelivered() {
		return delivered;
	}

	public void setDelivered(boolean delivered) {
		this.delivered = delivered;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Congregation getCongregation() {
		return congregation;
	}

	public void setCongregation(Congregation congregation) {
		this.congregation = congregation;
	}

}
