package org.gs.model;

import java.util.Date;

public class TeacherCourse {

	public TeacherCourse() {
		// TODO Auto-generated constructor stub
	}

	public int getTeacherCourseId() {
		return teacherCourseId;
	}
	public void setTeacherCourseId(int teacherCourseId) {
		this.teacherCourseId = teacherCourseId;
	}
	public int getClassCourseId() {
		return classCourseId;
	}
	public void setClassCourseId(int classCourseId) {
		this.classCourseId = classCourseId;
	}
	public int getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public Date getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public int getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}


	private int teacherCourseId;
	private int classCourseId;
	private int teacherId;
	private Date createdOn;
	private int createdBy;
	private Date modifiedOn;
	private int modifiedBy;
	private boolean deleted;
	
}
