package org.jw.mmn.core.model.entity;

import org.jw.mmn.commons.model.entity.Address;

import org.jw.mmn.core.model.entity.Member;
import org.vulpe.model.annotations.db4o.Inheritance;

@Inheritance
@SuppressWarnings("serial")
public class MemberAddress extends Address {

	private Member member;

	public void setMember(Member member) {
		this.member = member;
	}

	public Member getMember() {
		return member;
	}

}
