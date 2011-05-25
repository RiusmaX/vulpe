package org.jw.mmn.core.model.entity;

import java.util.List;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Boolean getBaptized() {
		return baptized;
	}

	public void setBaptized(Boolean baptized) {
		this.baptized = baptized;
	}

	public MinistryType getSimpleMinistryType() {
		return simpleMinistryType;
	}

	public void setSimpleMinistryType(MinistryType simpleMinistryType) {
		this.simpleMinistryType = simpleMinistryType;
	}

	public Responsibility getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(Responsibility responsibility) {
		this.responsibility = responsibility;
	}

	public List<AdditionalPrivilege> getAdditionalPrivileges() {
		return additionalPrivileges;
	}

	public void setAdditionalPrivileges(List<AdditionalPrivilege> additionalPrivileges) {
		this.additionalPrivileges = additionalPrivileges;
	}

	public Congregation getCongregation() {
		return congregation;
	}

	public void setCongregation(Congregation congregation) {
		this.congregation = congregation;
	}

	public MinistryType getMinistryType() {
		return ministryType;
	}

	public void setMinistryStatus(MinistryStatus ministryStatus) {
		this.ministryStatus = ministryStatus;
	}

	public MinistryStatus getMinistryStatus() {
		return ministryStatus;
	}

	public void setAddresses(List<MemberAddress> addresses) {
		this.addresses = addresses;
	}

	public List<MemberAddress> getAddresses() {
		return addresses;
	}

	public void setEmails(List<MemberEmail> emails) {
		this.emails = emails;
	}

	public List<MemberEmail> getEmails() {
		return emails;
	}

	public void setPhones(List<MemberPhone> phones) {
		this.phones = phones;
	}

	public List<MemberPhone> getPhones() {
		return phones;
	}

	public void setUrgentContacts(List<MemberUrgentContact> urgentContacts) {
		this.urgentContacts = urgentContacts;
	}

	public List<MemberUrgentContact> getUrgentContacts() {
		return urgentContacts;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

}
