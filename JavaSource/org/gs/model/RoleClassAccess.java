package org.gs.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
@ManagedBean
@ApplicationScoped
public class RoleClassAccess extends Role implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public RoleClassAccess() {
		// TODO Auto-generated constructor stub
	}

	public RoleClassAccess(Role r) {
		super(r);	
	}
	
	
	public List<UserClassAccess> getUserAccess() {
		return userAccess;
	}

	public void setUserAccess(List<UserClassAccess> userAccess) {
		this.userAccess = userAccess;
	}


	private List<UserClassAccess> userAccess = new ArrayList<UserClassAccess>();
	
}
