package org.gs.model;

import java.util.Date;

public class Transcript {

	public Transcript() {
		// TODO Auto-generated constructor stub
	}

	public int getTranscriptId() {
		return transcriptId;
	}
	public void setTranscriptId(int transcriptId) {
		this.transcriptId = transcriptId;
	}
	public int getRegistrationId() {
		return registrationId;
	}
	public void setRegistrationId(int registrationId) {
		this.registrationId = registrationId;
	}
	public int getClassCourseId() {
		return classCourseId;
	}
	public void setClassCourseId(int classCourseId) {
		this.classCourseId = classCourseId;
	}
	
	public int getTranscriptRef() {
		return transcriptRef;
	}
	public void setTranscriptRef(int transcriptRef) {
		this.transcriptRef = transcriptRef;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
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

	public int getHoursTaken() {
		return hoursTaken;
	}

	public void setHoursTaken(int hoursTaken) {
		this.hoursTaken = hoursTaken;
	}

	public int getGrad() {
		return grad;
	}

	public void setGrad(int grad) {
		this.grad = grad;
	}

	public int getGraded() {
		return graded;
	}

	public void setGraded(int graded) {
		this.graded = graded;
	}

	public float getGp() {
		return gp;
	}

	public void setGp(float gp) {
		this.gp = gp;
	}

	public float getGpa() {
		return gpa;
	}

	public void setGpa(float gpa) {
		this.gpa = gpa;
	}

	public Registration getRegistration() {
		return registration;
	}

	public void setRegistration(Registration registration) {
		this.registration = registration;
	}

	public ClassCourse getClassCourse() {
		return classCourse;
	}

	public void setClassCourse(ClassCourse classCourse) {
		this.classCourse = classCourse;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	
	public int getGradeId() {
		return gradeId;
	}

	public void setGradeId(int gradeId) {
		this.gradeId = gradeId;
	}

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}
	
	public boolean isNew() {
		return this.transcriptId==0;
	}



	private int transcriptId;
	private int registrationId;
	private Registration registration;
	private int classCourseId;
	private ClassCourse classCourse;
	private int gradeId;
	private int hoursTaken;
	private int grad;
	private int graded;
	private String grade;
	private float gp;
	private float gpa;
	private Integer transcriptRef = null;
	private boolean modified;
	private String comment;
	private Date createdOn;
	private int createdBy;
	private Date modifiedOn;
	private int modifiedBy;
	private boolean deleted;
}
