package org.jw.mmn.ministry.model.entity;

import org.jw.mmn.ministry.model.entity.Month;

public enum Month {

	JANUARY, FEBRUARY, MARCH, APRIL, MAY, JUNE, JULY, AUGUST, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER;

	public static Month getMonth(int value) {
		for (Month month : values()) {
			if (month.ordinal() == value) {
				return month;
			}
		}
		return JANUARY;
	}
}
