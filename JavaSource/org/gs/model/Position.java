package org.gs.model;

import java.io.Serializable;
import java.sql.Date;

public class Position implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public Position() {
		// TODO Auto-generated constructor stub
	}
	
	public int getPositionId() {
		return positionId;
	}

	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}

	public String getPositionTitle() {
		return positionTitle;
	}
	public void setPositionTitle(String positionTitle) {
		this.positionTitle = positionTitle;
	}

	public String getPositionDescription() {
		return positionDescription;
	}
	public void setPositionDescription(String positionDescription) {
		this.positionDescription = positionDescription;
	}

	public int getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public int getModifedBy() {
		return modifedBy;
	}
	public void setModifedBy(int modifedBy) {
		this.modifedBy = modifedBy;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}  

	public int getDeleted() {
		return deleted;
	}
	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	
	private int positionId;
	private String positionTitle;// The title of position
	private String positionDescription;// The description of position
	private int createdBy;//user who created the position 
	private Date createdOn; //the date of creation of the position
	private int modifedBy; //Id of the user who changes the position
	private Date modifiedOn; //the date of change of position
	private int deleted; //variable that checks if the position is deleted or not
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
