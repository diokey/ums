package org.gs.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import org.gs.dao.GradeDAO;
import org.gs.dao.RegistrationDAO;
import org.gs.dao.TranscriptDAO;
import org.gs.layout.SchoolTreeBean;
import org.gs.model.ClassCourse;
import org.gs.model.Course;
import org.gs.model.Grade;
import org.gs.model.Registration;
import org.gs.model.SchoolPeriod;
import org.gs.model.Structure;
import org.gs.model.Transcript;
import org.gs.model.User;
import org.gs.util.Constantes;
import org.gs.util.FacesUtil;

@ManagedBean
@ViewScoped
public class StudentMarkBean {

	
	public void generateTranscript() {
		
		if(this.isTranscriptGenerated()) {
			return;
		}
		
		Transcript total = this.computeTotal(this.studentMarks);
		
		this.transcriptDao.saveTotal(total);
		
		this.studentMarks.add(total);
		
		
	}
	
	private Transcript computeTotal(List<Transcript> marks) {
		float totalGp = 0;
		float totalGpa = 0;
		int totalHours = 0;
		int totalGrad = 0;
		int totalGraded = 0;
		
		for(Transcript t : marks) {
			totalHours+=t.getHoursTaken();
			totalGrad+= t.getGrad();
			totalGraded+= t.getGraded();
			
			totalGp+= t.getGp();
		}
		
		totalGpa+= totalGp/totalGrad;
		
		Transcript total = new Transcript();
		Course c = new Course();
		c.setCourseName("Totals ");
		ClassCourse cc = new ClassCourse();
		cc.setCourse(c);
		User u = (User) FacesUtil.getSessionAttribute(Constantes.CONNECTED_USER);
		total.setClassCourse(cc);
		total.setHoursTaken(totalHours);
		total.setGrad(totalGrad);
		total.setGraded(totalGraded);
		total.setGp(totalGp);
		total.setGpa(totalGpa);
		total.setRegistrationId(this.selectedRegistration.getRegistrationId());
		total.setCreatedBy(u.getUserId());
		total.setModifiedBy(u.getUserId());
		
		return total;
	}
	
	public void saveNotes() {
		String msg = "";
		FacesMessage message = null;
		boolean error = false;
		User u = (User) FacesUtil.getSessionAttribute(Constantes.CONNECTED_USER);
		
		for(Transcript t : this.studentMarks) {
			
			if(t.isModified()) {
				t.setRegistrationId(this.selectedRegistration.getRegistrationId());
				if(t.isNew()) {
					t.setCreatedBy(u.getUserId());
					t.setModifiedBy(u.getUserId());
					if(!this.transcriptDao.create(t)) {
						error = true;
					}
				}else {				
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
	
	public void onClassChanged() {
		this.selectedRegistration = null;
		
		this.init();
	}
	
	
	public void onStudentChanged(ValueChangeEvent event) {
		if(event==null || event.getNewValue()==null)
			return;
		
		int registrationId = Integer.parseInt(""+event.getNewValue());
		
		this.selectedRegistration = getRegiStration(registrationId);
		
		this.init();
	}
	
	private Registration getRegiStration(int id) {
		
		for(Registration r : this.registrations) {
			if(r.getRegistrationId()==id)
				return r;
		}
		
		return null;
	}
	
	public void assignGrade(int index) {
		
		Transcript t = this.studentMarks.get(index);
		t.setModified(true);
		Grade grade = this.getAssignedGrade(t.getGrade());
		
		t.setGrade(grade.getLetterCode());
		t.setGp(grade.getGradePoint() * t.getGraded());
		t.setGpa(grade.getGradePoint());
		
		if(!t.isNew()) {
			t.setModified(true);
		}
	}
	
	private Grade getAssignedGrade(String letterGrade) {
		for(Grade g : this.grades) {
			if(g.getLetterCode().equalsIgnoreCase(letterGrade))
				return g;
		}
		
		return null;
	}
	
	
	@PostConstruct
	private void init() {
		
		Structure classe = this.schoolTreeBean.getSelectedNodeData();		
		SchoolPeriod sp = this.schoolTreeBean.getSelectedSchoolPeriod();
		
		registrations = registrationDao.classRegistrations(classe.getId(), sp.getSchoolPeriodId());
		
		if(this.selectedRegistration==null) {
			if(this.registrations!=null && !this.registrations.isEmpty())
				this.selectedRegistration = this.registrations.get(0);
		}
		
		if(this.selectedRegistration!=null)
			this.studentMarks = this.transcriptDao.studentGrades(classe.getId(), sp.getSchoolPeriodId(),this.selectedRegistration.getRegistrationId());
		else
			this.studentMarks = new ArrayList<Transcript>();
		
		this.grades = this.gradeDao.findAll(1);
		
		//if transcript already generated, go ahead and compute the total
		if(this.isTranscriptGenerated()) {
			this.studentMarks.add(this.computeTotal(this.studentMarks));
		}
	}
	
	public StudentMarkBean() {
		// TODO Auto-generated constructor stub
		this.registrationDao = new RegistrationDAO();
		this.transcriptDao = new TranscriptDAO();
		this.gradeDao = new GradeDAO();
	}

	
	
	public List<Registration> getRegistrations() {
		return registrations;
	}

	public void setRegistrations(List<Registration> registrations) {
		this.registrations = registrations;
	}

	public SchoolTreeBean getSchoolTreeBean() {
		return schoolTreeBean;
	}

	public void setSchoolTreeBean(SchoolTreeBean schoolTreeBean) {
		this.schoolTreeBean = schoolTreeBean;
	}

	public List<Transcript> getStudentMarks() {
		return studentMarks;
	}

	public void setStudentMarks(List<Transcript> studentMarks) {
		this.studentMarks = studentMarks;
	}
	
	public List<Grade> getGrades() {
		return grades;
	}

	public void setGrades(List<Grade> grades) {
		this.grades = grades;
	}

	public Registration getSelectedRegistration() {
		return selectedRegistration;
	}

	public void setSelectedRegistration(Registration selectedRegistration) {
		this.selectedRegistration = selectedRegistration;
	}
	
	public boolean isTranscriptGenerated() {
		transcriptGenerated = this.transcriptDao.transcriptGenerated(
				this.selectedRegistration!=null?this.selectedRegistration.getRegistrationId():0);
		
		return transcriptGenerated;
	}

	public void setTranscriptGenerated(boolean transcriptGenerated) {
		this.transcriptGenerated = transcriptGenerated;
	}




	private boolean transcriptGenerated;

	private RegistrationDAO registrationDao;
	private TranscriptDAO transcriptDao;
	private GradeDAO gradeDao;

	private Registration selectedRegistration;
	
	private List<Registration> registrations;
	private List<Transcript> studentMarks;
	private List<Grade> grades;
	
	@ManagedProperty(value="#{schoolTreeBean}")
	private SchoolTreeBean schoolTreeBean;
}
