package org.gs.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;

import org.gs.dao.ClassCourseDAO;
import org.gs.dao.CourseDAO;
import org.gs.layout.SchoolTreeBean;
import org.gs.model.ClassCourse;
import org.gs.model.Course;
import org.gs.model.SchoolPeriod;
import org.gs.model.Structure;

@ManagedBean
@ViewScoped
public class ClassCoursesBean {

	
	
	public void addCourses() {
		System.out.println("Adding courses...");
		for(Course c : this.selectedCourses) {
			ClassCourse classCourse = new ClassCourse();
			classCourse.setCourse(c);
			classCourse.setCourseId(c.getCourseId());
			
			this.classCourses.add(classCourse);
		}
		
		this.allCourses.removeAll(this.selectedCourses);
		if(this.filteredCourses!=null)
			this.filteredCourses.clear();
		if(this.selectedCourses!=null)
			this.selectedCourses.clear();
	}
	
	public void saveSelectedCourses() {
		
		
		ClassCourseDAO ccDao = new ClassCourseDAO();
		
		this.schoolTreeBean.getSelectedNode();
		
		Structure classe = this.schoolTreeBean.getSelectedNodeData();
		
		SchoolPeriod sp = this.schoolTreeBean.getSelectedSchoolPeriod();
		
		for(ClassCourse cc : this.classCourses) {
			cc.setClassId(classe.getId());
			cc.setPeriodId(sp.getSchoolPeriodId());
			cc.setCourseId(cc.getCourse().getCourseId());
			
			ccDao.create(cc);
		}
		
	}
	
	
	public void test(String index) {
		
		
	}
	
	public ClassCoursesBean() {
		// TODO Auto-generated constructor stub
		
		this.classCourses = new ArrayList<ClassCourse>();
		this.courseDao = new CourseDAO();
		this.classCourseDao = new ClassCourseDAO();
		
		
		allCourses = this.courseDao.findAll();
	}
	
	private void removeAssignedCourses() {
		
	}
	
	@PostConstruct
	private void init() {
		
		this.schoolTreeBean.getSelectedNode();
		
		Structure classe = this.schoolTreeBean.getSelectedNodeData();
		
		SchoolPeriod sp = this.schoolTreeBean.getSelectedSchoolPeriod();
		
		this.classCourses = this.classCourseDao.findClassCourses(classe.getId(), sp.getSchoolPeriodId());
		
		System.out.println("ALl courses "+this.classCourses.size());
	}
		
	public List<Course> getAllCourses() {
		return allCourses;
	}
	public void setAllCourses(List<Course> allCourses) {
		this.allCourses = allCourses;
	}

	public List<Course> getSelectedCourses() {
		return selectedCourses;
	}

	public void setSelectedCourses(List<Course> selectedCourses) {
		this.selectedCourses = selectedCourses;
	}

	public List<Course> getFilteredCourses() {
		return filteredCourses;
	}

	public void setFilteredCourses(List<Course> filteredCourses) {
		this.filteredCourses = filteredCourses;
	}
	
	public List<ClassCourse> getClassCourses() {
		return classCourses;
	}

	public void setClassCourses(List<ClassCourse> classCourses) {
		this.classCourses = classCourses;
	}

	public List<ClassCourse> getSelectedClassCourses() {
		return selectedClassCourses;
	}

	public void setSelectedClassCourses(List<ClassCourse> selectedClassCourses) {
		this.selectedClassCourses = selectedClassCourses;
	}
	
	public ClassCourse getSelectedClassCourse() {
		return selectedClassCourse;
	}

	public void setSelectedClassCourse(ClassCourse selectedClassCourse) {
		this.selectedClassCourse = selectedClassCourse;
	}
	
	public SchoolTreeBean getSchoolTreeBean() {
		return schoolTreeBean;
	}

	public void setSchoolTreeBean(SchoolTreeBean schoolTreeBean) {
		this.schoolTreeBean = schoolTreeBean;
	}




	private List<Course> allCourses;
	private List<Course> selectedCourses;
	private List<Course> filteredCourses;
	private List<ClassCourse> classCourses;
	private List<ClassCourse> selectedClassCourses;
	private ClassCourse selectedClassCourse;
	private CourseDAO courseDao;
	private ClassCourseDAO classCourseDao;
	
	@ManagedProperty(value="#{schoolTreeBean}")
	private SchoolTreeBean schoolTreeBean;

}
