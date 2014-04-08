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
import org.gs.dao.TranscriptDAO;
import org.gs.layout.SchoolTreeBean;
import org.gs.model.ClassCourse;
import org.gs.model.Grade;
import org.gs.model.SchoolPeriod;
import org.gs.model.Staff;
import org.gs.model.Structure;
import org.gs.model.Transcript;
import org.gs.model.User;
import org.gs.util.Constantes;
import org.gs.util.FacesUtil;
import org.gs.util.RessourceBundleUtil;

@ManagedBean
@ViewScoped
public class ExaminationBean {


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
	
	
	public void saveNotes() {
		String msg = "";
		FacesMessage message = null;
		boolean error = false;
		User u = (User) FacesUtil.getSessionAttribute(Constantes.CONNECTED_USER);
		
		for(Transcript t : this.students) {
			if(t.isModified()) {
				t.setClassCourseId(this.selectedCourse.getClassCourseId());
				if(t.isNew()) {
					t.setCreatedBy(u.getUserId());
					t.setModifiedBy(u.getUserId());
					if(!this.transcriptDao.create(t)) {
						error = true;
					}
				}else{
					t.setModifiedBy(u.getUserId());
					if(!this.transcriptDao.update(t)) {
						error = true;
					}
				}
			}
	
		}
		
		if(error) {
			msg = "An error occured while saving... Try again";
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error",msg);
		}else {
			msg = "Grades saved successfully";
			message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Saved",msg);
		}
		
		FacesUtil.addMessage(null, message);
	}
	
	public void assignGrade(int index) {
		
		Transcript t = this.students.get(index);
		
		Grade grade = this.getAssignedGrade(t.getGrade());
		
		t.setGrade(grade.getLetterCode());
		t.setGp(grade.getGradePoint() * t.getGraded());
		t.setGpa(grade.getGradePoint());
		
	}
	
	private Grade getAssignedGrade(String letterGrade) {
		for(Grade g : this.grades) {
			if(g.getLetterCode().equalsIgnoreCase(letterGrade))
				return g;
		}
		
		return null;
	}
	
	private ClassCourse getClassCourseSelected(int id) {
		for(ClassCourse cc : this.classCourses) {
			if(cc.getClassCourseId()==id)
				return cc;
		}
		return null;
	}
	
	public ExaminationBean() {
		
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
	}

	@PostConstruct
	private void init() {
		
		Structure classe = this.schoolTreeBean.getSelectedNodeData();
		
		SchoolPeriod sp = this.schoolTreeBean.getSelectedSchoolPeriod();
		User user = (User) FacesUtil.getSessionAttribute(Constantes.CONNECTED_USER);
		
		if(user.getUserRoleId()==2) {
			
			Staff teacher = new StaffDAO().findByUserId(user.getUserId());
			
			this.classCourses = this.classCourseDao.findClassCourses(classe.getId(), sp.getSchoolPeriodId(),teacher.getStaffId());
		}else {
			this.classCourses = this.classCourseDao.findClassCourses(classe.getId(), sp.getSchoolPeriodId());
		}
		
		
		if(this.selectedCourse==null) {
			if(this.classCourses!=null && !this.classCourses.isEmpty())
				this.selectedCourse = this.classCourses.get(0);
		}
		
		if(this.selectedCourse!=null)
			this.students = this.transcriptDao.studentsList(classe.getId(), sp.getSchoolPeriodId(),this.selectedCourse.getClassCourseId());
		else
			this.students = new ArrayList<Transcript>();
			
		// TODO Should adapt this to be more flexible. Depends on which type of class
		this.grades = this.gradeDao.findAll(1);
		
		if(this.students!=null && this.selectedCourse!=null) {
			for(Transcript t : this.students) {
				t.setHoursTaken(this.selectedCourse.getCredits());
				t.setGrad(this.selectedCourse.getCredits());
				t.setGraded(t.getGrad());			
				
			}
		}
		
		
		
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
	
	public List<Transcript> getStudents() {
		return students;
	}

	public void setStudents(List<Transcript> students) {
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





	private List<ClassCourse> classCourses;
	private List<Transcript> students;
	private List<Grade> grades;
	
	private ClassCourse selectedCourse;
	private Grade selectedGrade;
	private Transcript selectedTranscript;
	
	private GradeDAO gradeDao;
	private ClassCourseDAO classCourseDao;
	private TranscriptDAO transcriptDao;
	
	@ManagedProperty(value="#{schoolTreeBean}")
	private SchoolTreeBean schoolTreeBean;
}
