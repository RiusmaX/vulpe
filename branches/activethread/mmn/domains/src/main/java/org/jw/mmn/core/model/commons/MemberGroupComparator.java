package org.jw.mmn.core.model.commons;

import java.util.Comparator;

import org.jw.mmn.core.model.entity.Member;

public class MemberGroupComparator implements Comparator<Member> {

	@Override
	public int compare(Member o1, Member o2) {
		return o1.getGroup().getName().compareTo(o2.getGroup().getName());
	}

}
