package org.gs.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import org.gs.dao.ClassCourseDAO;
import org.gs.dao.ClassRosterDAO;
import org.gs.dao.GradeDAO;
import org.gs.dao.StaffDAO;
import org.gs.dao.StudentDAO;
import org.gs.dao.TranscriptDAO;
import org.gs.layout.SchoolTreeBean;
import org.gs.model.AssessmentGrade;
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
public class ClassRosterBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -224517532371113906L;


	public ClassRosterBean() {
		// TODO Auto-generated constructor stub
		//First of all check if the user has access to this page.
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
		this.classRosterDao = new ClassRosterDAO();
		this.studentsAssementGrades = new ArrayList<AssessmentGrade>();
		this.tempNotes = new HashMap<Integer,AssessmentGrade>();

	}

	/*
	 * Method that saves all grades filled in the classroster
	 */
	public void saveGrades() {
		ClassRosterDAO classrosterDao = new ClassRosterDAO();
		FacesMessage message = null;
		String errorMsgText = RessourceBundleUtil.getMessage("errorSavingClassRoster");
		String successMsgText = RessourceBundleUtil.getMessage("successSavingClassRoster");
		String errorText = RessourceBundleUtil.getMessage("error");
		String successText = RessourceBundleUtil.getMessage("success");
		
		/** 
		 * I'm manually assigning/replacing notes from tempNotes.
		 * See examMarksChanged method for motives of doing this.
		 */
		for (int k : this.tempNotes.keySet()) {
			AssessmentGrade ag = this.tempNotes.get(k);
			this.studentsAssementGrades.set(k, ag);
		}
		
		for(AssessmentGrade assg : this.studentsAssementGrades) {
			
			assg.setClassCourse(this.selectedCourse);
			//total continious assessment : index 0
			assg.setTotalContiniousAssessement(assg.getOtherGrades().get(0)==null?null:(Float)assg.getOtherGrades().get(0));
			// final exam : index 1
			assg.setFinalExamMarks(assg.getOtherGrades().get(1)==null?null:(Float)assg.getOtherGrades().get(1));
			//total assessment and exam index 2
			assg.setTotalAssessmentPlusExam(assg.getOtherGrades().get(2)==null?null:(Float)assg.getOtherGrades().get(2));
			//final grade : index 3
			assg.setFinalGrade(assg.getOtherGrades().get(3)==null?null:(Float)assg.getOtherGrades().get(3));
			//letter code :  index 4
			assg.setLetterCode(assg.getOtherGrades().get(4)==null?null:""+assg.getOtherGrades().get(4));
			
			if(assg.isNew()) {				
				if (!classrosterDao.create(assg)) {
					message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorText,errorMsgText);
					FacesUtil.addMessage(null, message);
					System.out.print("An error occured while Saving class roaster");
					return; //stop if an error occured
				}
			} else {
				//update an existing record
				if (!classrosterDao.update(assg)) {
					message = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorText,errorMsgText);
					FacesUtil.addMessage(null, message);
					System.out.print("An error occured while Saving class roaster");
					return; //stop if an error occured
				}
			}
		}
		
		this.tempNotes.clear();
		this.studentsAssementGrades.clear();
		
		message = new FacesMessage(FacesMessage.SEVERITY_INFO,successText,successMsgText);
		
		FacesUtil.addMessage(null, message);
		
		this.init();
	}
	
	public void examMarksChanged(ValueChangeEvent e) {
		/**
		 * For some reasons (that i don't know yet), the value stored in final exam grades never make it
		 * to the server. I guess this is related to the process , update attribute and primefaces scope.
		 * Right now i am using process="@this" and update="@form". Although i can the value changes in
		 * this method, the new value somehow manages to disappear in the save method.
		 * It's weird because when i change process="@form" and update="@this", the values are kept but 
		 * with some extraneous value o.o even when the field was not filled. This is not the desired
		 * behavior as the user may intentionally leave this field blank to mean that the grade are not 
		 * attributed to the student yet.
		 * 
		 * The following code is a work around for the above problem. I'm basically storing the changed values
		 * in a map, and manually retrieve/replacing those value where they were supposed to be.
		 * Check saveNote Method to see how it is used.
		 */
		if(e.getNewValue()==null)
			return;
		String newValueString = ""+e.getNewValue();
		Float newValue;
		if(newValueString.isEmpty()) {
			newValue = null;
		} else {
			newValue = Float.parseFloat(newValueString);
		}
		int index = Integer.parseInt(""+e.getComponent().getAttributes().get("alt"));
		
		AssessmentGrade assg = this.studentsAssementGrades.get(index);
		assg.setFinalExamMarks(newValue);
		
		this.tempNotes.put(index, assg);
		
	}
	
	public static void main(String args[]) {
		System.out.println(Float.parseFloat("0"));
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
		
		if (this.classCourses == null)
			return;
		
		if(this.selectedCourse==null) {
			if(this.classCourses!=null && !this.classCourses.isEmpty())
				this.selectedCourse = this.classCourses.get(0);
		}
		
		this.teacher = this.teacherDao.findTeacherForCourse(this.selectedCourse.getClassCourseId());
		this.studentsAssementGrades.clear();
		this.studentsAssementGrades = this.classRosterDao.studentsList(classe.getId(), sp.getSchoolPeriodId(),this.selectedCourse.getClassCourseId());
		
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
	
	public List<AssessmentGrade> getStudentsAssementGrades() {
		return studentsAssementGrades;
	}

	public void setStudentsAssementGrades(
			List<AssessmentGrade> studentsAssementGrades) {
		this.studentsAssementGrades = studentsAssementGrades;
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

	public Map<Integer, AssessmentGrade> getTempNotes() {
		return tempNotes;
	}

	public void setTempNotes(Map<Integer, AssessmentGrade> tempNotes) {
		this.tempNotes = tempNotes;
	}










	private List<ClassCourse> classCourses;
	private List<AssessmentGrade> studentsAssementGrades;
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
	private ClassRosterDAO classRosterDao;
	private Map<Integer, AssessmentGrade> tempNotes;
	
	
	@ManagedProperty(value="#{schoolTreeBean}")
	private SchoolTreeBean schoolTreeBean;
}
