package org.jw.mmn.core.model.entity;

import org.vulpe.model.entity.impl.VulpeBaseDB4OEntity;

import org.jw.mmn.core.model.entity.Member;

@SuppressWarnings("serial")
public class MemberUrgentContact extends VulpeBaseDB4OEntity<Long> {

	private Member member;

	private String name;

	private String phone;

	public void setMember(Member member) {
		this.member = member;
	}

	public Member getMember() {
		return member;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return phone;
	}

}
