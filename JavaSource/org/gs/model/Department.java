package org.gs.model;

import java.util.ArrayList;
import java.util.List;

public class Department {

	public Department() {
		// TODO Auto-generated constructor stub
		this.levelList = new ArrayList<Level>();
	}

	
	public int getDepartementId() {
		return departementId;
	}
	public void setDepartementId(int departementId) {
		this.departementId = departementId;
	}
	public String getDepartementName() {
		return departementName;
	}
	public void setDepartementName(String departementName) {
		this.departementName = departementName;
	}
	public String getDepartementDescription() {
		return departementDescription;
	}
	public void setDepartementDescription(String departementDescription) {
		this.departementDescription = departementDescription;
	}
	
	public List<Level> getLevelList() {
		return levelList;
	}


	public void setLevelList(List<Level> levelList) {
		this.levelList = levelList;
	}
	
	public void addLevel(Level newLevel) {
		this.levelList.add(newLevel);
	}

	public boolean removeLevel(Level level) {
		if(this.levelList!=null && !this.levelList.isEmpty())
			return this.levelList.remove(level);
		return false;
	}


	private int departementId;
	private String departementName;
	private String departementDescription;
	
	private List<Level> levelList;
}
