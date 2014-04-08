package org.gs.model;

public class Level {

	public Level() {
		// TODO Auto-generated constructor stub
	}

	
	public String getLevelId() {
		return levelId;
	}
	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getLevelDescription() {
		return levelDescription;
	}
	public void setLevelDescription(String levelDescription) {
		this.levelDescription = levelDescription;
	}
	
	public String getClassName() {
		return className;
	}


	public void setClassName(String className) {
		this.className = className;
	}



	private String levelId;
	private String levelName;
	private String levelDescription;
	private String className;
}
