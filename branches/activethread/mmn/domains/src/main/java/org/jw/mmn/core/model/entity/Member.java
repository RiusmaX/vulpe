package org.jw.mmn.core.model.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.jw.mmn.commons.model.entity.Gender;
import org.jw.mmn.commons.model.entity.MinistryStatus;
import org.jw.mmn.commons.model.entity.MinistryType;
import org.vulpe.commons.util.VulpeStringUtil;
import org.vulpe.model.annotations.Autocomplete;
import org.vulpe.model.annotations.Like;
import org.vulpe.model.annotations.NotExistEquals;
import org.vulpe.model.annotations.OrderBy;
import org.vulpe.model.annotations.Parameter;
import org.vulpe.model.annotations.QueryParameter;
import org.vulpe.model.entity.VulpeEntity;
import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;
import org.vulpe.security.model.entity.User;
import org.vulpe.view.annotations.input.VulpeCheckbox;
import org.vulpe.view.annotations.input.VulpeSelect;
import org.vulpe.view.annotations.input.VulpeText;
import org.vulpe.view.annotations.output.VulpeColumn;

@NotExistEquals(parameters = { @QueryParameter(equals = @Parameter(name = "name")) })
@SuppressWarnings("serial")
@Getter
@Setter
public class Member extends VulpeBaseDB4OEntity<Long> {

	@VulpeColumn(sortable = true)
	@VulpeText(maxlength = 60, size = 50, required = true, argument = true)
	@OrderBy
	@Like
	private String name;

	@VulpeSelect(required = true)
	private Gender gender;

	@VulpeColumn(sortable = true, attribute = "name")
	@VulpeSelect(items = "Group", itemKey = "id", itemLabel = "name", autoLoad = true, argument = true)
	private Group group;

	@VulpeColumn(booleanTo = "{Yes}|{No}")
	@VulpeCheckbox(argument = true, fieldValue = "true")
	private Boolean baptized;

	@Autocomplete
	@VulpeSelect(required = true)
	private MinistryType ministryType;

	private transient MinistryType simpleMinistryType;

	@VulpeSelect(required = true)
	private Responsibility responsibility;

	private User user;

	private List<AdditionalPrivilege> additionalPrivileges;

	private Congregation congregation;

	private MinistryStatus ministryStatus;

	private List<MemberAddress> addresses;

	private List<MemberEmail> emails;

	private List<MemberPhone> phones;

	private List<MemberUrgentContact> urgentContacts;

	public void setMinistryType(MinistryType tipoMinisterio) {
		this.ministryType = tipoMinisterio;
		if (this.simpleMinistryType != null) {
			this.ministryType = this.simpleMinistryType;
		}
	}

	@Override
	public int compareTo(VulpeEntity<Long> entity) {
		final Member member = (Member) entity;
		return VulpeStringUtil.normalize(name).compareTo(VulpeStringUtil.normalize(member.getName()));
	}

}
