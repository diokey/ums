package org.gs.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.gs.model.AccessRight;
import org.gs.model.User;
import org.gs.util.Constantes;
import org.gs.util.FacesUtil;

@ManagedBean
@ViewScoped
public class RenderBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -145093704774205816L;

	public RenderBean() {
		// TODO Auto-generated constructor stub
	}
	
	@PostConstruct
	public void init() {
		this.connectedUser = (User) FacesUtil.getSessionAttribute(Constantes.CONNECTED_USER);
		
		// The user seems to not be connected. 
		// TODO Do something to alert and block the user to do other operations
		if(this.connectedUser==null)
			return;
		
	}
	
	public boolean hasAccess(String permission) {
		
		AccessRight userRight = new AccessRight();
		
		return userRight.hasAccess(permission);
	}
	
	private User connectedUser = null;
	

}
