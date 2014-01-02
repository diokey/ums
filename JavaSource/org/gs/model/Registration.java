package org.gs.model;

import java.util.Date;
import java.util.List;

public class Registration {

	public Registration() {
		// TODO Auto-generated constructor stub
	}
	
	public int getRegistrationId() {
		return registrationId;
	}
	public void setRegistrationId(int registrationId) {
		this.registrationId = registrationId;
	}
	public int getClassId() {
		return classId;
	}
	public void setClassId(int classId) {
		this.classId = classId;
	}
	public int getPeriodId() {
		return periodId;
	}
	public void setPeriodId(int periodId) {
		this.periodId = periodId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	
	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public List<Transcript> getNotes() {
		return notes;
	}

	public void setNotes(List<Transcript> notes) {
		this.notes = notes;
	}

	public SchoolPeriod getPeriod() {
		return period;
	}

	public void setPeriod(SchoolPeriod period) {
		this.period = period;
	}





	private int registrationId;
	private int classId;
	private int periodId;
	private SchoolPeriod period;
	private int studentId;
	private Student student;
	private String comment;
	private Date createdOn;
	private Date modifiedOn;
	private boolean deleted;
	private List<Transcript> notes;
}
