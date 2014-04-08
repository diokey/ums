package org.gs.model;

import java.util.ArrayList;
import java.util.List;

public class Faculty {

	public Faculty() {
		// TODO Auto-generated constructor stub
		this.listDepartement = new ArrayList<Department>();
	}

	
	public int getFacultyId() {
		return facultyId;
	}
	public void setFacultyId(int facultyId) {
		this.facultyId = facultyId;
	}
	public String getFacultyName() {
		return facultyName;
	}
	public void setFacultyName(String facultyName) {
		this.facultyName = facultyName;
	}
	public String getFacultyDescription() {
		return facultyDescription;
	}
	public void setFacultyDescription(String facultyDescription) {
		this.facultyDescription = facultyDescription;
	}
	
	public List<Department> getListDepartement() {
		return listDepartement;
	}

	public void setListDepartement(List<Department> listDepartement) {
		this.listDepartement = listDepartement;
	}
	
	public void addDepartement(Department newDepartement) {
		this.listDepartement.add(newDepartement);
	}
	
	public boolean removeDepartement(Department departement) {
		if(this.listDepartement!=null && !this.listDepartement.isEmpty())
			return this.listDepartement.remove(departement);
		return false;
	}



	private int facultyId;
	private String facultyName;
	private String facultyDescription;
	
	private List<Department> listDepartement;
}
