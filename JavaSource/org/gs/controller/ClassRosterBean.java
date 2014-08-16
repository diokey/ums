package org.gs.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import org.gs.dao.ClassCourseDAO;
import org.gs.dao.GradeDAO;
import org.gs.dao.StaffDAO;
import org.gs.dao.StudentDAO;
import org.gs.dao.TranscriptDAO;
import org.gs.layout.SchoolTreeBean;
import org.gs.model.ClassCourse;
import org.gs.model.Grade;
import org.gs.model.Program;
import org.gs.model.SchoolPeriod;
import org.gs.model.Staff;
import org.gs.model.Structure;
import org.gs.model.Student;
import org.gs.model.Teacher;
import org.gs.model.Transcript;
import org.gs.model.User;
import org.gs.util.Constantes;
import org.gs.util.FacesUtil;
import org.gs.util.RessourceBundleUtil;

@ManagedBean
@ViewScoped
public class ClassRosterBean {

	public ClassRosterBean() {
		// TODO Auto-generated constructor stub
		//First of all check if the user has access to this page.
    	// Meaning if the user is connected.
    	// TODO Should possibly check some other user right
    	
    	
		User u = (User) FacesUtil.getSessionAttribute(Constantes.CONNECTED_USER);
		if(u==null)
			FacesUtil.redirect("/",RessourceBundleUtil.getMessage("notConnected"));
		
    	//-------------------- end checking connection checkings--------------------------------
		
		this.classCourseDao = new ClassCourseDAO();
		this.transcriptDao = new TranscriptDAO();
		this.gradeDao = new GradeDAO();
		this.studentDao = new StudentDAO();
		this.teacherDao = new StaffDAO();
	}

	public void onCourseChanged(ValueChangeEvent e) {
		if(e==null || e.getNewValue()==null)
			return;
		int classCourseId = Integer.parseInt(""+e.getNewValue());
		
		this.selectedCourse = this.getClassCourseSelected(classCourseId);
		
		this.init();
		
		
	}
	
	public void onClassChanged() {
		System.out.println("Class changed...");
		this.selectedCourse = null;
		this.init();
	}
	
	private ClassCourse getClassCourseSelected(int id) {
		for(ClassCourse cc : this.classCourses) {
			if(cc.getClassCourseId()==id)
				return cc;
		}
		return null;
	}

	@PostConstruct
	private void init() {
		
		Structure classe = this.schoolTreeBean.getSelectedNodeData();
		
		SchoolPeriod sp = this.schoolTreeBean.getSelectedSchoolPeriod();
		
		Program p = this.schoolTreeBean.getSelectedProgram();
		
		this.classCourses = this.classCourseDao.findClassCourses(classe.getId(), sp.getSchoolPeriodId());
		
		
		if(this.selectedCourse==null) {
			if(this.classCourses!=null && !this.classCourses.isEmpty())
				this.selectedCourse = this.classCourses.get(0);
		}
		
		this.students = this.studentDao.studentInClass(classe.getId(), sp.getSchoolPeriodId(), p.getProgramId());
		this.teacher = this.teacherDao.findTeacherForCourse(this.selectedCourse.getClassCourseId());
		
		
	}
	
	
	public List<ClassCourse> getClassCourses() {
		return classCourses;
	}

	public void setClassCourses(List<ClassCourse> classCourses) {
		this.classCourses = classCourses;
	}

	public SchoolTreeBean getSchoolTreeBean() {
		return schoolTreeBean;
	}

	public void setSchoolTreeBean(SchoolTreeBean schoolTreeBean) {
		this.schoolTreeBean = schoolTreeBean;
	}
	
	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}
	
	public ClassCourse getSelectedCourse() {
		return selectedCourse;
	}

	public void setSelectedCourse(ClassCourse selectedCourse) {
		this.selectedCourse = selectedCourse;
	}

	public List<Grade> getGrades() {
		return grades;
	}

	public void setGrades(List<Grade> grades) {
		this.grades = grades;
	}

	public Grade getSelectedGrade() {
		return selectedGrade;
	}

	public void setSelectedGrade(Grade selectedGrade) {
		this.selectedGrade = selectedGrade;
	}
	
	public Transcript getSelectedTranscript() {
		return selectedTranscript;
	}

	public void setSelectedTranscript(Transcript selectedTranscript) {
		this.selectedTranscript = selectedTranscript;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}





	private List<ClassCourse> classCourses;
	private List<Student> students;
	private List<Grade> grades;
	
	private ClassCourse selectedCourse;
	private Grade selectedGrade;
	private Transcript selectedTranscript;
	private Teacher teacher;
	
	private GradeDAO gradeDao;
	private ClassCourseDAO classCourseDao;
	private TranscriptDAO transcriptDao;
	private StudentDAO studentDao;
	private StaffDAO teacherDao;
	
	@ManagedProperty(value="#{schoolTreeBean}")
	private SchoolTreeBean schoolTreeBean;
}
