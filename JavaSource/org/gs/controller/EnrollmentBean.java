package org.gs.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.gs.dao.RegistrationDAO;
import org.gs.dao.StructureDAO;
import org.gs.layout.SchoolTreeBean;
import org.gs.model.Registration;
import org.gs.model.School;
import org.gs.model.SchoolPeriod;
import org.gs.model.SchoolYear;
import org.gs.model.Structure;
import org.gs.model.User;
import org.gs.util.Constantes;
import org.gs.util.FacesUtil;

@ManagedBean
@ViewScoped
public class EnrollmentBean {

	
	public void enrollStudents() {
		
		FacesMessage messages = null;
		String msg="";
		User u = (User) FacesUtil.getSessionAttribute(Constantes.CONNECTED_USER);
		int count = 0;
		for(Registration r : this.selectedRegistrations) {
			r.setCreatedBy(u.getUserId());
			r.setModifiedBy(u.getUserId());
			
			r.setClassId(this.enrollClass.getId());
			r.setPeriodId(this.selectedSchoolPeriod.getSchoolPeriodId());		
						
			if(this.registrationDao.create(r))
				count++;
		}
		
		if(count>0)
			msg=count+"Students has been enrolled";
		else
			msg="No student has been enrolled";
		messages = new FacesMessage(FacesMessage.SEVERITY_INFO,"Saved",msg);
		
		FacesUtil.addMessage(null, messages);
	}
	
	public EnrollmentBean() {
		// TODO Auto-generated constructor stub
		this.selectedSchool = new School();
		this.selectedSchoolPeriod = new SchoolPeriod();
		this.selectedSchoolYear = new SchoolYear();
		this.enrollClass = new Structure();
		
		this.registrationDao = new RegistrationDAO();
		this.structureDao = new StructureDAO();
		
	}

	@PostConstruct
	public void init() {
		
		Structure s = this.schoolTreeBean.getSelectedNodeData();
		SchoolPeriod sp = this.schoolTreeBean.getSelectedSchoolPeriod();
		
		this.enrollClass = s;
		
		this.registeredStudents = this.registrationDao.classRegistrations(s.getId(), sp.getSchoolPeriodId());
		this.classes = this.structureDao.getClassHierarchy(Constantes.CURRENT_SCHOOL);
	}
	
	
	
	public List<Registration> getRegisteredStudents() {
		return registeredStudents;
	}

	public void setRegisteredStudents(List<Registration> registeredStudents) {
		this.registeredStudents = registeredStudents;
	}

	public SchoolTreeBean getSchoolTreeBean() {
		return schoolTreeBean;
	}

	public void setSchoolTreeBean(SchoolTreeBean schoolTreeBean) {
		this.schoolTreeBean = schoolTreeBean;
	}
	
	public List<Registration> getSelectedRegistrations() {
		return selectedRegistrations;
	}

	public void setSelectedRegistrations(List<Registration> selectedRegistrations) {
		this.selectedRegistrations = selectedRegistrations;
	}

	public School getSelectedSchool() {
		return selectedSchool;
	}

	public void setSelectedSchool(School selectedSchool) {
		this.selectedSchool = selectedSchool;
	}

	public SchoolYear getSelectedSchoolYear() {
		return selectedSchoolYear;
	}

	public void setSelectedSchoolYear(SchoolYear selectedSchoolYear) {
		this.selectedSchoolYear = selectedSchoolYear;
	}

	public SchoolPeriod getSelectedSchoolPeriod() {
		return selectedSchoolPeriod;
	}

	public void setSelectedSchoolPeriod(SchoolPeriod selectedSchoolPeriod) {
		this.selectedSchoolPeriod = selectedSchoolPeriod;
	}

	public List<Structure> getClasses() {
		return classes;
	}

	public void setClasses(List<Structure> classes) {
		this.classes = classes;
	}

	public Structure getEnrollClass() {
		return enrollClass;
	}

	public void setEnrollClass(Structure enrollClass) {
		this.enrollClass = enrollClass;
	}





	private RegistrationDAO registrationDao;
	private StructureDAO structureDao;
	private Structure enrollClass;
	
	private School selectedSchool;
	private SchoolYear selectedSchoolYear;
	private SchoolPeriod selectedSchoolPeriod;

	private List<Registration> registeredStudents;
	private List<Registration> selectedRegistrations;
	private List<Structure> classes;
	
	@ManagedProperty(value="#{schoolTreeBean}")
	private SchoolTreeBean schoolTreeBean;
}
