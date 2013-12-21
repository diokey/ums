package org.gs.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.gs.model.User;
import org.gs.util.Constantes;
import org.gs.util.FacesUtil;

@ManagedBean
@ViewScoped
public class UserBean {

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
	
	public UserBean() {
		// TODO Auto-generated constructor stub
	}

	
	public User getUser() {
		if(user==null)
			user = getConnectedUser();
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	private User user;
}
