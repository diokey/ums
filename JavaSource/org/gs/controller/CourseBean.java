package org.gs.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.gs.dao.CourseDAO;
import org.gs.model.Course;

@ManagedBean
@ViewScoped
public class CourseBean {

	
	public void saveCourse() {
		System.out.println("Saving now...");
		this.newCourse.setSchoolId(1);
		this.newCourse.setCreatedBy(1);
		this.filteredCourses = new ArrayList<Course>();
		this.selectedCourses = new ArrayList<Course>();
		CourseDAO cDao = new CourseDAO();
		FacesMessage messages = null;
		if(cDao.create(this.newCourse)) {
			this.courseList.add(this.newCourse);
			this.newCourse = new Course();
			messages = new FacesMessage(FacesMessage.SEVERITY_INFO,"Saved","Course saved successfully");
		}else {
			messages = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Not saved","An error occured while saving");
		}
		
		FacesContext.getCurrentInstance().addMessage(null, messages);
	}
	
	
	public CourseBean() {
		// TODO Auto-generated constructor stub
		
		this.newCourse = new Course();
		cDao = new CourseDAO();
		this.courseList = cDao.findAll();
		System.out.println("SIze : "+this.courseList.size());
	}

	
	public Course getNewCourse() {
		return newCourse;
	}


	public void setNewCourse(Course newCourse) {
		this.newCourse = newCourse;
	}
	
	public List<Course> getCourseList() {
		return courseList;
	}


	public void setCourseList(List<Course> courseList) {
		this.courseList = courseList;
	}
	
	
	public List<Course> getFilteredCourses() {
		return filteredCourses;
	}


	public void setFilteredCourses(List<Course> filteredCourses) {
		this.filteredCourses = filteredCourses;
	}


	public List<Course> getSelectedCourses() {
		return selectedCourses;
	}


	public void setSelectedCourses(List<Course> selectedCourses) {
		this.selectedCourses = selectedCourses;
	}





	private Course newCourse;
	private CourseDAO cDao;
	private List<Course> courseList;
	private List<Course> filteredCourses;
	private List<Course> selectedCourses;
}
