package org.gs.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.gs.dao.CourseDAO;
import org.gs.model.Course;
import org.gs.model.User;
import org.gs.util.Constantes;
import org.gs.util.FacesUtil;
import org.gs.util.RessourceBundleUtil;
import org.primefaces.event.RowEditEvent;

@ManagedBean
@ViewScoped
public class CourseBean {

	public void onCourseEdit(RowEditEvent event) {
		Course course = (Course) event.getObject();
		
		CourseDAO cDao = new CourseDAO();
		
		FacesMessage message = null;
		if(cDao.update(course)) {
			String msg = RessourceBundleUtil.getMessage("recordUpdateSuccessful");
			message = new FacesMessage(FacesMessage.SEVERITY_INFO,RessourceBundleUtil.getMessage("updateSuccessful"),msg);
		}else {
			String msg = RessourceBundleUtil.getMessage("recordUpdateFailed");
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR,RessourceBundleUtil.getMessage("updateFailed"),msg);
		}
		
		FacesUtil.addMessage(null, message);
		
		//this.init();
	}
	
	public void onCourseCancel(RowEditEvent event) {
		this.init();
	}
	
	public void saveCourse() {
		
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
		//First of all check if the user has access to this page.
    	// Meaning if the user is connected.
    	// TODO Should possibly check some other user right
    	
    	
		User u = (User) FacesUtil.getSessionAttribute(Constantes.CONNECTED_USER);
		if(u==null)
			FacesUtil.redirect("/",RessourceBundleUtil.getMessage("notConnected"));
		
    	//-------------------- end checking connection checkings--------------------------------
		this.newCourse = new Course();
		cDao = new CourseDAO();
		
		this.init();		
	}

	private void init() {
		this.courseList = cDao.findAll();
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
