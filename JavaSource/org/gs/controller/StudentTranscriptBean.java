package org.gs.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;

import org.gs.dao.RegistrationDAO;
import org.gs.layout.SchoolTreeBean;
import org.gs.model.Registration;
import org.gs.model.SchoolPeriod;
import org.gs.model.Structure;

@ManagedBean
@ViewScoped
public class StudentTranscriptBean {

	public StudentTranscriptBean() {
		// TODO Auto-generated constructor stub
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
		
		registrations = registrationDao.classRegistrations(classe.getId(), sp.getSchoolPeriodId());
		
		if(this.selectedRegistration==null) {
			if(this.registrations!=null && !this.registrations.isEmpty())
				this.selectedRegistration = this.registrations.get(0);
		}
		
		if(this.selectedRegistration!=null) {
			this.studentRegistration = this.registrationDao.studentRegistration(this.selectedRegistration.getStudentId());
		
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





	private RegistrationDAO registrationDao;
	private List<Registration> studentRegistration;
	
	
	private Registration selectedRegistration;
	
	private List<Registration> registrations;
	
	@ManagedProperty(value="#{schoolTreeBean}")
	private SchoolTreeBean schoolTreeBean;
}
