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
	
	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	

	private int classAccessIid;
	private int classId;
	private int userId;
	
	
}
