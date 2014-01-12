package org.gs.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.gs.dao.ClassCourseDAO;
import org.gs.dao.StaffDAO;
import org.gs.dao.StructureDAO;
import org.gs.layout.SchoolTreeBean;
import org.gs.model.ClassCourse;
import org.gs.model.SchoolPeriod;
import org.gs.model.Structure;
import org.gs.model.Teacher;
import org.gs.util.Constantes;

@ManagedBean
@ViewScoped
public class TeacherCourseBean {

	
	public void findTeacherCourses() {
		
		ClassCourseDAO ccDao = new ClassCourseDAO();
		List<ClassCourse> classCoursesFound = ccDao.findTeacherCourses(this.currentTeacher.getStaffId());
		List<ClassCourse> tobeRemoved = new ArrayList<ClassCourse>();
		System.out.println("Course : "+this.classCourses.size());
		System.out.println("Found : "+classCoursesFound.size());
		this.currentTeacher.setLectures(classCourses);
		for(ClassCourse cc : classCoursesFound) {
			ClassCourse found = existInTargets(cc);
			
			if(found!=null)
				tobeRemoved.add(found);
		}
		
		this.classCourses.removeAll(tobeRemoved);
	}
	
	private ClassCourse existInTargets(ClassCourse classCourse) {
		//List<ClassCourse> targetsCourses = this.classCourses;
		
		if(this.classCourses==null || this.classCourses.isEmpty())
			return null;
		for(ClassCourse cc : this.classCourses) {
			if(cc.getClassCourseId()==classCourse.getClassCourseId())
				return cc;
		}
		
		return null;
	}
	
		
	@PostConstruct
	private void init() {
		this.teachers = this.teacherDAO.findAllTeachers();
		
		this.classes = this.structureDao.getClassHierarchy(Constantes.CURRENT_SCHOOL);
			
		
		if(this.classes!=null && !this.classes.isEmpty()) {
			this.selectedClass = this.classes.get(0);
		}else {
			this.selectedClass = new Structure();
		}
		
		SchoolPeriod sp = this.schoolTreeBean.getSelectedSchoolPeriod();
		
		this.classCourses = this.classCourseDao.findClassCourses(this.selectedClass.getId(), sp.getSchoolPeriodId());
	}
	
	public TeacherCourseBean() {
		// TODO Auto-generated constructor stub
		this.teacherDAO = new StaffDAO();
		this.classCourseDao = new ClassCourseDAO();
		this.structureDao = new StructureDAO();
	}

	public List<Teacher> getTeachers() {
		return teachers;
	}
	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}
	public List<ClassCourse> getClassCourses() {
		return classCourses;
	}
	public void setClassCourses(List<ClassCourse> classCourses) {
		this.classCourses = classCourses;
	}
	
	public Teacher getCurrentTeacher() {
		return currentTeacher;
	}

	public void setCurrentTeacher(Teacher currentTeacher) {
		this.currentTeacher = currentTeacher;
	}

	public Structure getSelectedClass() {
		return selectedClass;
	}

	public void setSelectedClass(Structure selectedClass) {
		this.selectedClass = selectedClass;
	}

	public List<Structure> getClasses() {
		return classes;
	}

	public void setClasses(List<Structure> classes) {
		this.classes = classes;
	}
	
	public SchoolTreeBean getSchoolTreeBean() {
		return schoolTreeBean;
	}

	public void setSchoolTreeBean(SchoolTreeBean schoolTreeBean) {
		this.schoolTreeBean = schoolTreeBean;
	}





	private StaffDAO teacherDAO;
	private StructureDAO structureDao;
	private ClassCourseDAO classCourseDao;
	
	private Teacher currentTeacher;
	private Structure selectedClass;
	
	private List<Teacher> teachers;
	private List<ClassCourse> classCourses;
	private List<Structure> classes;
	
	@ManagedProperty(value="#{schoolTreeBean}")
	private SchoolTreeBean schoolTreeBean;
	
}
