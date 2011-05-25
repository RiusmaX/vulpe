package org.jw.mmn.core.model.commons;

import java.util.Comparator;

import org.jw.mmn.ministry.model.entity.MemberReport;

public class MemberReportGroupComparator implements Comparator<MemberReport> {

	@Override
	public int compare(MemberReport o1, MemberReport o2) {
		return o1.getMember().getGroup().getName().compareTo(o2.getMember().getGroup().getName());
	}

}
