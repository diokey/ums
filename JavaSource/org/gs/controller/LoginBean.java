package org.gs.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.gs.dao.PermissionDAO;
import org.gs.dao.UserDAO;
import org.gs.model.Role;
import org.gs.model.User;
import org.gs.util.Constantes;
import org.gs.util.FacesUtil;
import org.gs.util.HashUtil;
import org.gs.util.RessourceBundleUtil;

@ManagedBean
@ViewScoped
public class LoginBean implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public void chechkLogin() {
		
		UserDAO uDao = new UserDAO();
		
		User u = uDao.checkLogin(this.user.getUsername(), HashUtil.hash(this.user.getUserPassword()));
		
		if(u==null) {
			String messageText = RessourceBundleUtil.getMessage("loginFailed");
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,messageText,messageText);
			
			FacesUtil.addMessage("loginForm:loginBtn", message);
			
			AppLoger.log(Constantes.ERROR,"Login failed using username "+this.user.getUsername());
			
			System.out.println("Login failed");
		}else{
			
			this.user = u;
			//if user is banned
			if(user.isBanned()) {
				String messageText = "";
				if(user.getBannedMessage().isEmpty())
					messageText = RessourceBundleUtil.getMessage("userBanned");
				else
					messageText = user.getBannedMessage();
				
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN,messageText,messageText);
				
				FacesUtil.addMessage("loginForm:loginBtn", message);
				
				AppLoger.log(Constantes.WARNING,"Login attempt for user "+this.user.getUsername()+" failed because his account is blocked");
			}else {
				//if user is not banned.
				//redirect him according to his profil;
				System.out.println("role : "+this.user.getUserRole());
				if(user.getUserRole()==null) {
					
					String msgText = RessourceBundleUtil.getMessage("unkonwRedirectPath");
					
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,msgText,msgText);
					
					FacesUtil.addMessage("loginForm:loginBtn", message);
					
					AppLoger.log(Constantes.CRITICAL,"Unable to find where to direct user "+this.user.getUsername());
					
					return;
				}
				
				String destination = user.getUserRole().getLoginDestination();
				if(destination.isEmpty()) {
					switch(user.getUserRole().getRoleId()) {
					case 1 : destination = "/admin";
					break;
					case 2 : destination = "/teacher";
					break;
					case 3 : destination = "/student";
					break;
					default : destination = "/admin";
					
					}
				}
				
				if(destination.isEmpty()) {
					String msgText = RessourceBundleUtil.getMessage("unkonwRedirectPath");
					
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,msgText,msgText);
					
					FacesUtil.addMessage("loginForm:loginBtn", message);
					
					AppLoger.log(Constantes.CRITICAL,"Unable to find where to direct user "+this.user.getUsername()+" with role "+this.user.getUserRole().getRoleName());
					
				}else {
					String msgText = "Login Successful for user "+this.user.getUsername();
					
					AppLoger.log(Constantes.INFO,msgText);
					
					// keep user permissions in the session
					PermissionDAO permissionDao = new PermissionDAO();
					this.user.setPermissions(permissionDao.findRolePermission(user.getUserRoleId()));
					
					FacesUtil.setSessionAttribute(Constantes.CONNECTED_USER, this.user);
					
					FacesUtil.redirect(destination);
				}
			}
		}
	}
	
	public LoginBean() {
		this.user = new User();
		
		if(FacesUtil.memberConnected()) {
			User u = (User) FacesUtil.getSessionAttribute(Constantes.CONNECTED_USER);
			if(u==null)
				return;
			Role r = u.getUserRole();
			
			if(r!=null && ! r.getLoginDestination().isEmpty())
				FacesUtil.redirect(r.getLoginDestination());
		}
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}


	private User user;
}
