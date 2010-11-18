package org.vulpe.resources.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.vulpe.model.annotations.Like;
import org.vulpe.model.annotations.NotExistEqual;
import org.vulpe.model.annotations.QueryParameter;
import org.vulpe.model.annotations.QueryParameter.OperatorType;
import org.vulpe.model.entity.impl.AbstractVulpeBaseEntity;

@Entity
@Table(name = "VulpeMenu")
@NotExistEqual(parameters = { @QueryParameter(name = "name", operator = OperatorType.EQUAL) }, message = "vulpe.error.menu.exists")
@SuppressWarnings("serial")
public class Menu extends AbstractVulpeBaseEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Like
	private String name;

	private String description;

	private Menu parent;

	private Boolean backendOnly;

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(final Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	public Menu getParent() {
		return parent;
	}

	public void setBackendOnly(Boolean backendOnly) {
		this.backendOnly = backendOnly;
	}

	public Boolean getBackendOnly() {
		return backendOnly;
	}

}
