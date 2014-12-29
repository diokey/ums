package org.gs.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import org.gs.dao.RegistrationDAO;
import org.gs.layout.SchoolTreeBean;
import org.gs.model.ClassCourse;
import org.gs.model.Course;
import org.gs.model.Program;
import org.gs.model.Registration;
import org.gs.model.SchoolPeriod;
import org.gs.model.Structure;
import org.gs.model.Transcript;
import org.gs.model.User;
import org.gs.util.Constantes;
import org.gs.util.FacesUtil;
import org.gs.util.RessourceBundleUtil;

@ManagedBean
@ViewScoped
public class StudentTranscriptBean {

	public StudentTranscriptBean() {
		
		//First of all check if the user has access to this page.
    	// Meaning if the user is connected.
    	// TODO Should possibly check some other user right
    	
    	
		User u = (User) FacesUtil.getSessionAttribute(Constantes.CONNECTED_USER);
		if(u==null)
			FacesUtil.redirect("/",RessourceBundleUtil.getMessage("notConnected"));
		
    	//-------------------- end checking connection checkings--------------------------------
		
		this.registrationDao = new RegistrationDAO();
		
	}
	
	public void onNodeChanged() {
		this.selectedRegistration = null;
		
		this.init();
	}
	
	public void onStudentChanged(ValueChangeEvent event) {
		if(event==null || event.getNewValue()==null);
		
		int regId = Integer.parseInt(""+event.getNewValue());
		
		this.selectedRegistration = getRegiStration(regId);
		
		this.init();
	}
	
	private Registration getRegiStration(int id) {
		
		for(Registration r : this.registrations) {
			if(r.getRegistrationId()==id)
				return r;
		}
		
		return null;
	}

	@PostConstruct
	private void init() {
		Structure classe = this.schoolTreeBean.getSelectedNodeData();		
		SchoolPeriod sp = this.schoolTreeBean.getSelectedSchoolPeriod();
		Program program = this.schoolTreeBean.getSelectedProgram();
		this.cumulativeGrades = new ArrayList<Transcript>();
		
		registrations = registrationDao.classRegistrations(classe.getId(), sp.getSchoolPeriodId(), program.getProgramId());
		
		if(this.selectedRegistration==null) {
			if(this.registrations!=null && !this.registrations.isEmpty())
				this.selectedRegistration = this.registrations.get(0);
		}
		
		Transcript cumulativeTotals = new Transcript();
		int hoursTaken = 0;
		int grad = 0;
		int graded = 0;
		float gp = 0;
		float gpa = 0;
		
		if(this.selectedRegistration!=null) {
			this.studentRegistration = this.registrationDao.studentRegistration(this.selectedRegistration.getStudentId());
			for(Registration r : this.studentRegistration) {
				Transcript temp = this.studentMark.computeTotal(r.getNotes());
				hoursTaken+= temp.getHoursTaken();
				grad+= temp.getGrad();
				graded+= temp.getGraded();
				gp+= temp.getGp();
				
				r.getNotes().add(temp);
			}
			//avoid division by zero
			if ( grad != 0) {
				gpa = gp / grad;
			}
			
			DecimalFormat df = new DecimalFormat("###.##");
			//totalGpa in String
			String tmp = df.format(gpa);
			//after convert it back to float.
			gpa = Float.parseFloat(tmp);
					
			DecimalFormat dfa = new DecimalFormat("###.##");
			//totalGpa in String
			String temp = dfa.format(gp);
			//after convert it back to float.
			gp = Float.parseFloat(temp);
			
			Course c = new Course();
			c.setCourseName("Cumulative Totals");
			ClassCourse cc = new ClassCourse();
			cc.setCourse(c);
			cumulativeTotals.setClassCourse(cc);
			
			cumulativeTotals.setHoursTaken(hoursTaken);
			cumulativeTotals.setGrad(grad);
			cumulativeTotals.setGraded(graded);
			cumulativeTotals.setGrade("");
			cumulativeTotals.setGp(gp);
			cumulativeTotals.setGpa(gpa);
			
			this.cumulativeGrades.add(cumulativeTotals);
			
		}else{
			this.studentRegistration = new ArrayList<Registration>();
		}
	}
	
	public List<Registration> getStudentRegistration() {
		return studentRegistration;
	}

	public void setStudentRegistration(List<Registration> studentRegistration) {
		this.studentRegistration = studentRegistration;
	}

	public Registration getSelectedRegistration() {
		return selectedRegistration;
	}

	public void setSelectedRegistration(Registration selectedRegistration) {
		this.selectedRegistration = selectedRegistration;
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
	
	public StudentMarkBean getStudentMark() {
		return studentMark;
	}

	public void setStudentMark(StudentMarkBean studentMark) {
		this.studentMark = studentMark;
	}

	public List<Transcript> getCumulativeGrades() {
		return cumulativeGrades;
	}

	public void setCumulativeGrades(List<Transcript> cumulativeGrades) {
		this.cumulativeGrades = cumulativeGrades;
	}


	
	private RegistrationDAO registrationDao;
	private List<Registration> studentRegistration;
	
	
	private Registration selectedRegistration;
	
	private List<Registration> registrations;
	private List<Transcript> cumulativeGrades;
	
	@ManagedProperty(value="#{schoolTreeBean}")
	private SchoolTreeBean schoolTreeBean;
	
	@ManagedProperty(value="#{studentMarkBean}")
	private StudentMarkBean studentMark;
}
