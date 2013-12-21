package org.gs.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.gs.dao.RegistrationDAO;
import org.gs.dao.StudentDAO;
import org.gs.dao.UserDAO;
import org.gs.layout.SchoolTreeBean;
import org.gs.model.Registration;
import org.gs.model.SchoolPeriod;
import org.gs.model.Structure;
import org.gs.model.Student;
import org.gs.model.User;
import org.gs.util.CommonUtils;
import org.gs.util.FacesUtil;
import org.gs.util.HashUtil;


@ManagedBean
@ViewScoped
public class RegistrationBean {

	
	public void saveStudent() {
		
		Structure selectedNode = this.schoolTreeBean.getSelectedNodeData();
		FacesMessage messages = null;
		//save student infos
		if(this.studentDao.create(this.newStudent)) {
			int studentId = CommonUtils.getLastId("student", "student_id");
			
			//if choosen credentials
			if(this.newUser.isActive()) {
				//save as user
				System.out.println("Saving user data : ");
				this.newUser.setDisplayName(this.newUser.getUsername());
				this.newUser.setUserRoleId(3);
				this.newUser.setForcePasswordReset(true);
				this.newUser.setBanned(false);
				this.newUser.setUserPassword(HashUtil.hash(this.newUser.getUserPassword()));
				this.newUser.setLanguage("en-US");
				this.newUser.setDisplayName(this.newUser.getUsername());
				this.newUser.setEmail(this.newStudent.getEmail());
				
				UserDAO userDao = new UserDAO();
				
				userDao.create(this.newUser);
			}
			
			if(enroll && selectedNode!=null) {
				
				Registration reg = new Registration();
				
								
				if(selectedNode.isLeaf() || selectedNode.is_leaf()) {
					reg.setClassId(selectedNode.getId());
					SchoolPeriod sp = this.schoolTreeBean.getSelectedSchoolPeriod();
					if(sp!=null)
						reg.setPeriodId(sp.getSchoolPeriodId());
					reg.setStudentId(studentId);
					reg.setDeleted(false);
					
					RegistrationDAO regDao = new RegistrationDAO();				
					
					if(regDao.create(reg)) {
						
							messages = new FacesMessage(FacesMessage.SEVERITY_INFO,"Saved","Saved successfully");
					}else{
						messages = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Not saved","Error occured while saving");
					}
						
					
				}else {
						String msg = ""+this.schoolTreeBean.getSelectedTreeNodeString()+" is not a class";
						
						messages = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",msg);
				}
				
			}else {
				messages = new FacesMessage(FacesMessage.SEVERITY_INFO,"saved","Saved successfully");
			}
		}else {
			messages = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Not saved","Error occured while saving student");
		}
		
		FacesUtil.addMessage(null, messages);
		
		this.newStudent = new Student();
		this.newUser = new User();
	}
	
	
	private List<Registration> getClassRegistrations() {
		
		List<Registration> registrations = new ArrayList<Registration>();
		
		RegistrationDAO rdao = new RegistrationDAO();
		SchoolPeriod sp = this.schoolTreeBean.getSelectedSchoolPeriod();
		Structure classe = this.schoolTreeBean.getSelectedNodeData();
		registrations = rdao.classRegistrations(classe.getId(), sp.getSchoolPeriodId());
		
		return registrations;
	}
	
	public void cancelSave() {
		System.out.println("Resetting....");
		this.newStudent = new Student();
		this.newUser = new User();
	}
	
	public RegistrationBean() {
		// TODO Auto-generated constructor stub
		
		this.newStudent = new Student();
		this.studentDao = new StudentDAO();
		
		this.newUser = new User();
	}

	
	public Student getNewStudent() {
		return newStudent;
	}


	public void setNewStudent(Student newStudent) {
		this.newStudent = newStudent;
	}

	
	public User getNewUser() {
		return newUser;
	}


	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}

	
	public SchoolTreeBean getSchoolTreeBean() {
		return schoolTreeBean;
	}

	public void setSchoolTreeBean(SchoolTreeBean schoolTreeBean) {
		this.schoolTreeBean = schoolTreeBean;
	}
	
	public boolean isEnroll() {
		enroll = this.schoolTreeBean.getSelectedNode()!=null;
		return enroll;
	}

	public void setEnroll(boolean enroll) {
		this.enroll = enroll;
	}

	public List<Registration> getRegistrations() {
		registrations = this.getClassRegistrations();
		
		return registrations;
	}

	public void setRegistrations(List<Registration> registrations) {
		this.registrations = registrations;
	}





	@ManagedProperty(value="#{schoolTreeBean}")
	private SchoolTreeBean schoolTreeBean;
	
	private Student newStudent;
	private StudentDAO studentDao;
	private User newUser;
	private boolean enroll;
	private List<Registration> registrations;
	
}
