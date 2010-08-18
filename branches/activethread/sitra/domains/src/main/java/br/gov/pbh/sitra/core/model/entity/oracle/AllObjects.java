package br.gov.pbh.sitra.core.model.entity.oracle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.vulpe.model.annotations.OrderBy;
import org.vulpe.model.annotations.Query;
import org.vulpe.model.annotations.QueryReplacement;
import org.vulpe.model.entity.impl.AbstractVulpeBaseEntity;

@Query(
	replacement = @QueryReplacement(select = "new AllObjects(obj.name)")
)
@Table(name = "ALL_OBJECTS")
@Entity
@SuppressWarnings("serial")
public class AllObjects extends AbstractVulpeBaseEntity<Long> {

	@Id
	@Column(name = "OBJECT_ID")
	private Long id;

	@OrderBy
	@Column(name = "OBJECT_NAME")
	private String name;

	@Column(name = "OBJECT_TYPE")
	private String type;

	public AllObjects() {
	}

	public AllObjects(final String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		if (StringUtils.isNotEmpty(this.name)) {
			return this.name;
		}
		return super.toString();
	}
}
