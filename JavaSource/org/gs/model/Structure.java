package org.gs.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Structure implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Structure() {
		// TODO Auto-generated constructor stub
	}
	
	public Structure getRoot(Structure s) {
		
		return this;
	}
	
	public boolean addChild(Structure child) {
		return this.children.add(child);
	}
	
	public List<Structure> getChildren() {
		return this.children;
	}
	
	public void setChildren(List<Structure> children) {
		this.children = children;
	}
	
	public Structure getParent() {
		return this.parent;
	}
	
	public boolean is_root() {
		return this.parent==null;
	}
	
	public boolean is_leaf() {
		return this.children==null || this.children.isEmpty();
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



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}


	/*

	public int getStructureTypeId() {
		return structureTypeId;
	}



	public void setStructureTypeId(int structureTypeId) {
		this.structureTypeId = structureTypeId;
	}

	*/


	public boolean isDeleted() {
		return deleted;
	}



	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}


	/*
	public String getStructureType() {
		return structureType;
	}
	
	public void setStructureType(String structuretype) {
		this.structureType = structuretype;
	}
	*/

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
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
	
	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}
	
	public int getSchoolStructureId() {
		return schoolStructureId;
	}

	public void setSchoolStructureId(int schoolStructureId) {
		this.schoolStructureId = schoolStructureId;
	}
	
	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	private Structure parent;
	private List<Structure> children;
	private String title;
	private String description;
	private int id;
	private Integer parentId;
	//private int structureTypeId;
	//private String structureType;	
	private boolean deleted;
	private boolean isRoot;
	private boolean leaf;
	private Date createdOn;
	private int createdBy;
	private int schoolStructureId;
	
	
	
}
