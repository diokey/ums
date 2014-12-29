package org.gs.util;

/*
 * This is a temporarily class for representing schooltype.
 * TODO May be we should move this in it's own table.
 */
public enum SchoolType {

	UNDERDRAGRUATE(1),
	GRADUATE(2);
	private final int schoolType;
	
	SchoolType(int schoolType) {
		this.schoolType = schoolType;
	}
	
	public int getSchoolType () {
		return this.schoolType;
	}
}
