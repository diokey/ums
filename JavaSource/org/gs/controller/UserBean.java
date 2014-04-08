package org.gs.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.gs.dao.RoleDAO;
import org.gs.dao.UserDAO;
import org.gs.model.Role;
import org.gs.model.User;
import org.gs.util.Constantes;
import org.gs.util.FacesUtil;
import org.gs.util.HashUtil;
import org.gs.util.RessourceBundleUtil;

@ManagedBean
@ViewScoped
public class UserBean {

	public void saveNewUser() {
		
		UserDAO udao = new UserDAO();
		this.newUser.setUserPassword(HashUtil.hash(this.user.getUserPassword()));
		System.out.println("Crypted password is ; "+this.newUser.getUserPassword());
		this.newUser.setForcePasswordReset(true);
		FacesMessage message = null;
		
		if(udao.create(this.newUser)) {
			String summaryMsg = RessourceBundleUtil.getMessage("success");
			String detailedMsg = RessourceBundleUtil.getMessage("saveSuccess");
			
			message = new FacesMessage(FacesMessage.SEVERITY_INFO, summaryMsg, detailedMsg);
			
			this.allUsers.add(newUser);
			
			this.newUser= new User();
			
		}else{
			String summaryMsg = RessourceBundleUtil.getMessage("error");
			String detailedMsg = RessourceBundleUtil.getMessage("saveFailed");
			
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summaryMsg, detailedMsg);
		}
		
		FacesUtil.addMessage(null, message);
	}
	
	
	private User getConnectedUser() {
		
		if(this.isConnected()) {
		
			return (User) FacesUtil.getSessionAttribute(Constantes.CONNECTED_USER);
		}
		
		return null;
	}
	
	public void logOut() {
		
		User u = (User) FacesUtil.getSessionAttribute(Constantes.CONNECTED_USER);
		
		FacesUtil.setSessionAttribute(Constantes.CONNECTED_USER, null);
		
		AppLoger.log(Constantes.INFO,u.getUsername()+" just logged out");
		
		FacesUtil.logOut();
		
		FacesUtil.redirect("/");
		
		
	}
	
	public boolean isConnected() {
		return FacesUtil.memberConnected();
	}
	
	@PostConstruct
	private void init() {
		this.appLanguages = RessourceBundleUtil.getLanguages();
		
		if(this.appLanguages==null)
			this.appLanguages = new ArrayList<SelectItem>();
		
		RoleDAO roleDao = new RoleDAO();
		UserDAO userDao = new UserDAO();
		
		this.roles = roleDao.findAll();
		this.allUsers = userDao.findAll();
	}
	
	public UserBean() {
		
		//First of all check if the user has access to this page.
    	// Meaning if the user is connected.
    	// TODO Should possibly check some other user right    	
    	
		User u = (User) FacesUtil.getSessionAttribute(Constantes.CONNECTED_USER);
		if(u==null)
			FacesUtil.redirect("/",RessourceBundleUtil.getMessage("notConnected"));
		
    	//-------------------- end checking connection checkings--------------------------------
		
		this.newUser = new User();
	}
	
	public User getNewUser() {
		return newUser;
	}

	public void setNewUser(User newUser) {
		this.newUser = newUser;
	}

	public User getUser() {
		if(user==null)
			user = getConnectedUser();
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}
	

	public List<SelectItem> getAppLanguages() {
		return appLanguages;
	}


	public void setAppLanguages(List<SelectItem> appLanguages) {
		this.appLanguages = appLanguages;
	}

	public List<Role> getRoles() {
		return roles;
	}


	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<User> getAllUsers() {
		return allUsers;
	}


	public void setAllUsers(List<User> allUsers) {
		this.allUsers = allUsers;
	}




	private User user;
	
	//used to store the new user data in add new user form
	private User newUser;
	
	private List<SelectItem> appLanguages;
	private List<Role> roles;
	private List<User> allUsers;
}
