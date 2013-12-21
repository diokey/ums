package org.gs.model;

import java.util.Date;

public class StructureType {

	public StructureType() {
		// TODO Auto-generated constructor stub
	}

	
	public int getStructureTypeId() {
		return structureTypeId;
	}
	public void setStructureTypeId(int structureTypeId) {
		this.structureTypeId = structureTypeId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	public int isCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}
	
	public int getLevel() {
		return level;
	}


	public void setLevel(int level) {
		this.level = level;
	}


	public int getCreatedBy() {
		return createdBy;
	}




	private int structureTypeId;
	private String title;
	private String description;
	private int level;
	private Date createdOn;
	private boolean deleted;
	private int createdBy;
}
