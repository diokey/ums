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
import org.gs.util.SchoolType;

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
		 * The work around that i found for this was to use ArrayList. I'm shamefully putting value into an array 
		 * (which doesn't make any sense) and retrieving those value into the variables and saving them. 
		 */
		
		ClassRosterDAO classrosterDao = new ClassRosterDAO();
		FacesMessage message = null;
		String errorMsgText = RessourceBundleUtil.getMessage("errorSavingClassRoster");
		String successMsgText = RessourceBundleUtil.getMessage("successSavingClassRoster");
		String errorText = RessourceBundleUtil.getMessage("error");
		String successText = RessourceBundleUtil.getMessage("success");
		
		for(AssessmentGrade assg : this.studentsAssementGrades) {
			
			assg.setClassCourse(this.selectedCourse);
			
			List<Float> notes =assg.getAssessmentGrades();
			
			float sum = 0;
			int count = 0;
			for(Float f : notes) {
				if (f != null) {
					sum+=f;
					count++;
				}
			}
			
			Float totalContiniousAssessment = null;
			if (count != 0) {
				totalContiniousAssessment = (sum * 100) / (count * 20);
				assg.setTotalContiniousAssessement(totalContiniousAssessment);
			} else {
				assg.setTotalContiniousAssessement(assg.getOtherGrades().get(1)==null?null:(Float)assg.getOtherGrades().get(0));
			}
			
			//final exam marks is at index 1
			assg.setFinalExamMarks(assg.getOtherGrades().get(1)==null?null:(Float)assg.getOtherGrades().get(1));
			
			if(totalContiniousAssessment != null && assg.getFinalExamMarks() != null) {
				float totalAssessmentPlusGrade = (assg.getFinalExamMarks() + totalContiniousAssessment) / 2;
				assg.setTotalAssessmentPlusExam(totalAssessmentPlusGrade);
			} else {
				if (totalContiniousAssessment == null && assg.getFinalExamMarks() != null) {
					assg.setTotalAssessmentPlusExam(assg.getFinalExamMarks());
				} else {
					assg.setTotalAssessmentPlusExam(assg.getOtherGrades().get(1)==null?null:(Float)assg.getOtherGrades().get(2));
				}
			}
			
			if(assg.getTotalAssessmentPlusExam() != null) {
				GradeDAO gradeDao = new GradeDAO();
				//TODO Make sure not to hard code this value.
				SchoolType type = SchoolType.UNDERDRAGRUATE;
				Grade g = gradeDao.findByNote(type.getSchoolType(), (int)(float)assg.getTotalAssessmentPlusExam());
				assg.setFinalGrade(g.getGradePoint());
				assg.setLetterCode(g.getLetterCode());
			} else {
				//final grade : index 3
				assg.setFinalGrade(assg.getOtherGrades().get(3)==null?null:(Float)assg.getOtherGrades().get(3));
				//letter code :  index 4
				assg.setLetterCode(assg.getOtherGrades().get(4)==null?null:""+assg.getOtherGrades().get(4));
			}
			
			
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
			
			saveTranscript(assg);
		}
		
		this.tempNotes.clear();
		this.studentsAssementGrades.clear();
		
		message = new FacesMessage(FacesMessage.SEVERITY_INFO,successText,successMsgText);
		
		FacesUtil.addMessage(null, message);
		
		
		
		this.init();
	}
	
	private void saveTranscript(AssessmentGrade ass) {
		if(ass == null)
			return;
		Transcript transcript = new Transcript();
		transcript.setRegistrationId(ass.getRegistration().getRegistrationId());
		transcript.setClassCourseId(ass.getClassCourse().getClassCourseId());
		transcript.setHoursTaken(ass.getClassCourse().getCredits());
		transcript.setGrad(transcript.getHoursTaken());
		transcript.setGraded(transcript.getGrad());
		
		String grade = ass.getLetterCode();
		float gpa = ass.getFinalGrade() ==null?0:(Float) ass.getFinalGrade();
		float gp = gpa * transcript.getGraded();
		
		transcript.setGp(gp);
		transcript.setGpa(gpa);
		transcript.setGrade(grade);
		
		TranscriptDAO transcriptDao = new TranscriptDAO();
		
		if(ass.isNew()) {
			if(!transcriptDao.create(transcript)) {
				System.out.println("ERROR : Unable to save student transcript");
			}
		} else {
			if(!transcriptDao.update(transcript)) {
				System.out.println("ERROR : Unable to update student transcript");
			}
		}
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
		
		if (this.classCourses == null & ! this.classCourses.isEmpty())
			return;
		
		if(this.selectedCourse==null) {
			if(this.classCourses!=null && !this.classCourses.isEmpty())
				this.selectedCourse = this.classCourses.get(0);
		}
		
		if (this.selectedCourse == null) {
			this.teacher = new Teacher();
			this.studentsAssementGrades.clear();
			return;
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
