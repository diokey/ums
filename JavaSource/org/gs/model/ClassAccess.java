package org.gs.model;

import java.sql.Date;

public class ClassAccess {

	public ClassAccess() {
		// TODO Auto-generated constructor stub
	}

	public int getClassAccessIid() {
		return classAccessIid;
	}
	public void setClassAccessIid(int classAccessIid) {
		this.classAccessIid = classAccessIid;
	}
	public int getSchoolStructureId() {
		return schoolStructureId;
	}
	public void setSchoolStructureId(int schoolStructureId) {
		this.schoolStructureId = schoolStructureId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreateOn() {
		return createOn;
	}
	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}
	public int getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(int modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}


	private int classAccessIid;
	private int schoolStructureId;
	private int userId;
	private int createdBy;
	private Date createOn;
	private int modifiedBy;
	private Date modifiedOn;
}
