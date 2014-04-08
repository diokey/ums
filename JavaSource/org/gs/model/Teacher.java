package org.gs.model;

import java.util.List;

public class Teacher extends Staff{

	public Teacher() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	
	public List<ClassCourse> getLectures() {
		return lectures;
	}

	public void setLectures(List<ClassCourse> lectures) {
		this.lectures = lectures;
	}


	private List<ClassCourse> lectures;

}
