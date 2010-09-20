package org.vulpe.commons.enumeration;

public enum DaysOfWeek {
	SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY;

	public int getValue() {
		int dayOfWeek = 0;
		switch (this) {
		case SUNDAY:
			dayOfWeek = 1;
			break;
		case MONDAY:
			dayOfWeek = 2;
			break;
		case TUESDAY:
			dayOfWeek = 3;
			break;
		case WEDNESDAY:
			dayOfWeek = 4;
			break;
		case THURSDAY:
			dayOfWeek = 5;
			break;
		case FRIDAY:
			dayOfWeek = 6;
			break;
		case SATURDAY:
			dayOfWeek = 7;
			break;
		default:
			dayOfWeek = 0;
			break;
		}
		return dayOfWeek;
	}
}
