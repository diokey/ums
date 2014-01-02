package org.gs.model;

public class Grade {

	public Grade() {
		// TODO Auto-generated constructor stub
	}

	public int getGradeId() {
		return gradeId;
	}
	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}
	public String getLetterCode() {
		return letterCode;
	}
	public void setLetterCode(String letterCode) {
		this.letterCode = letterCode;
	}
	public float getMinMarks() {
		return minMarks;
	}
	public void setMinMarks(float minMarks) {
		this.minMarks = minMarks;
	}
	public float getMaxMarks() {
		return maxMarks;
	}
	public void setMaxMarks(float maxMarks) {
		this.maxMarks = maxMarks;
	}
	public float getGradePoint() {
		return gradePoint;
	}
	public void setGradePoint(float gradePoint) {
		this.gradePoint = gradePoint;
	}
	public String getLevelAchievement() {
		return levelAchievement;
	}
	public void setLevelAchievement(String levelAchievement) {
		this.levelAchievement = levelAchievement;
	}
	public int getSchoolType() {
		return schoolType;
	}
	public void setSchoolType(int schoolType) {
		this.schoolType = schoolType;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}


	private int gradeId;
	private String letterCode;
	private float minMarks;
	private float maxMarks;
	private float gradePoint;
	private String levelAchievement;
	private int schoolType;
	private boolean deleted;
}
