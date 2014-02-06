package org.gs.model;

import java.util.List;

import javax.annotation.PostConstruct;

import org.gs.util.Constantes;
import org.gs.util.FacesUtil;

public class AccessRight{

	public AccessRight() {
		// TODO Auto-generated constructor stub
		this.init();
	}
	
	@PostConstruct
	private void init() {
		this.connectedUser = (User) FacesUtil.getSessionAttribute(Constantes.CONNECTED_USER);
		
		// TODO Handler user not connected case
		if(this.connectedUser==null) {
			System.out.println("Error : User not connected");
			return;
		}
	}
	
	/**
	 * Function that checks if a user has a permission
	 * @param permission : Name of the permission to check if a user has
	 * @return returns true if the user has the supplied permission in argument. Return false otherwise
	 */
	public boolean hasAccess(String permission) {
		
		if(this.connectedUser==null)
			return false;
		
		List<Permission> userPermissions = this.connectedUser.getPermissions();
		
		// Don't continue if no user permission is set.
		// TODO find a way to handle this situation
		if(userPermissions==null)
			return false;
		
		System.out.println("Size : "+userPermissions.size());
		
		for(Permission p : userPermissions) {
			System.out.println("Permission : "+p);
			if(permission.equalsIgnoreCase(p.getName()) && "active".equalsIgnoreCase(p.getStatus())) {
				
				return true;
			}
		}
		
		return false;
	}

	private User connectedUser;
}
