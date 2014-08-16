package org.gs.model;

import java.io.Serializable;

public class Program implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Program() {
		
	}
	
	public int getProgramId() {
		return programId;
	}
	public void setProgramId(int programId) {
		this.programId = programId;
	}
	public String getProgramName() {
		return programName;
	}
	public void setProgramName(String programName) {
		this.programName = programName;
	}
	public String getProgramDescription() {
		return programDescription;
	}
	public void setProgramDescription(String programDescription) {
		this.programDescription = programDescription;
	}
	private int programId;
	private String programName;
	private String programDescription;
}
