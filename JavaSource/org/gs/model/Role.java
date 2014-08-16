package org.gs.model;

import java.io.Serializable;

public class Role implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Role() {
		
	}
	
	public Role(Role copy) {
		this.roleId = copy.roleId;
		this.roleName = copy.roleName;
		this.roleDescription = copy.roleDescription;
		this.buildIn = copy.buildIn;
		this.canDelete = copy.canDelete;
		this.deleted = copy.deleted;
		this.loginDestination = copy.loginDestination;
	}
	
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDescription() {
		return roleDescription;
	}
	public void setRoleDescription(String roleDescription) {
		this.roleDescription = roleDescription;
	}
	public boolean isBuildIn() {
		return buildIn;
	}
	public void setBuildIn(boolean buildIn) {
		this.buildIn = buildIn;
	}
	public boolean isCanDelete() {
		return canDelete;
	}
	public void setCanDelete(boolean canDelete) {
		this.canDelete = canDelete;
	}
	public String getLoginDestination() {
		return loginDestination;
	}
	public void setLoginDestination(String loginDestination) {
		this.loginDestination = loginDestination;
	}
	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}



	private int roleId;
	private String roleName;
	private String roleDescription;
	private boolean buildIn = false;
	private boolean canDelete = true;
	private String loginDestination = "/";
	private boolean deleted;
}
