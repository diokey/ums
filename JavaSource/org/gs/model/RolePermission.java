package org.gs.model;

import java.util.List;

import org.gs.dao.PermissionDAO;
import org.primefaces.model.DualListModel;

public class RolePermission {

	public RolePermission() {
		// TODO Auto-generated constructor stub
	}
		
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public List<Permission> getPermissions() {
		if(permissions==null) {
			PermissionDAO pdao = new PermissionDAO();
			this.permissions = pdao.findRolePermission(this.role.getRoleId());
		}
		return permissions;
	}
	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public List<Permission> getAllPermission() {
		if(allPermission==null) {
			PermissionDAO pdao = new PermissionDAO();
			
			allPermission = pdao.findAll();
		}
		return allPermission;
	}

	public void setAllPermission(List<Permission> allPermission) {
		this.allPermission = allPermission;
	}
	

	public DualListModel<Permission> getRolePermissions() {
		if(rolePermissions==null) {
			this.getAllPermission().removeAll(this.getPermissions());
			
			this.rolePermissions = new DualListModel<Permission>(this.getAllPermission(),this.getPermissions());
		}
		return rolePermissions;
	}

	public void setRolePermissions(DualListModel<Permission> rolePermissions) {
		this.rolePermissions = rolePermissions;
	}




	private Role role;
	private List<Permission> permissions;
	private List<Permission> allPermission;
	private DualListModel<Permission> rolePermissions;

}
