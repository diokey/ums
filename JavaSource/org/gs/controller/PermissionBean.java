package org.gs.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.gs.dao.PermissionDAO;
import org.gs.dao.RoleDAO;
import org.gs.dao.RolePermissionDAO;
import org.gs.model.Permission;
import org.gs.model.Role;
import org.gs.model.RolePermission;
import org.gs.util.FacesUtil;
import org.gs.util.RessourceBundleUtil;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DualListModel;

@ManagedBean
@ViewScoped
public class PermissionBean {

	
	
	
	public void onPermissionEdit(RowEditEvent event) {
		Permission  permission = (Permission) event.getObject();
		
		System.out.println("Permssion "+permission.getName()+" and "+permission.getDescription());
		
		PermissionDAO pdao = new PermissionDAO();
		FacesMessage message = null;
		if(pdao.update(permission)) {
			String msg = RessourceBundleUtil.getMessage("recordUpdateSuccessful");
			message = new FacesMessage(FacesMessage.SEVERITY_INFO,RessourceBundleUtil.getMessage("updateSuccessful"),msg);
		}else {
			String msg = RessourceBundleUtil.getMessage("recordUpdateFailed");
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR,RessourceBundleUtil.getMessage("updateFailed"),msg);
		}
		
		FacesUtil.addMessage(null, message);
		
		this.init();
	}
	
	public void onPermissionCancel(RowEditEvent event) {
		
	}
	
	public void onPermissionDeleted(Permission permission) {
		PermissionDAO pdao = new PermissionDAO();
		FacesMessage message = null;
		// TODO Delete user permission corresponding to this permission
		if(!pdao.revokePermissionToAllRoles(permission)) {
			String msg = RessourceBundleUtil.getMessage("recordDeleteFailed");
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR,RessourceBundleUtil.getMessage("deletionFailed"),msg);
			
			return;
		}
		
		if(pdao.delete(permission)) {
			String msg = RessourceBundleUtil.getMessage("recordDeleteSuccessful");
			message = new FacesMessage(FacesMessage.SEVERITY_INFO,RessourceBundleUtil.getMessage("deletionSuccessful"),msg);
			this.permissionList = this.permissionDao.findAll();
		}else {
			String msg = RessourceBundleUtil.getMessage("recordDeleteFailed");
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR,RessourceBundleUtil.getMessage("deletionFailed"),msg);
		}
		
		FacesUtil.addMessage(null, message);
		
		this.init();
	}
	
	/**
	 * Allow to save a new role. Function called from permissions page in new role dialog
	 * @return void
	 * @param void
	 * 
	 */
	public void saveRole() {
		String msgText = "";
		FacesMessage message = null;
		
		if(this.roleDao.create(this.newRole)) {
			msgText = RessourceBundleUtil.getMessage("newRoleSaved");
			message = new FacesMessage(FacesMessage.SEVERITY_INFO,RessourceBundleUtil.getMessage("success"), msgText);
		}else {
			msgText = RessourceBundleUtil.getMessage("errorOccured");
			message = new FacesMessage(FacesMessage.SEVERITY_INFO,RessourceBundleUtil.getMessage("error"),msgText);
		}
		
		FacesUtil.addMessage(null, message);
		this.newRole = new Role();
		
		this.init();
		
	}
	
	/**
	 * Allow to save new Permission . Function called from permissions page in new permission dialog
	 * @return void
	 * @param void
	 * 
	 */
	public void saveNewPermission() {
		String msgText = "";
		FacesMessage message = null;
		
		if(this.permissionDao.create(this.newPermission)) {
			msgText = RessourceBundleUtil.getMessage("newPermissionSaved");
			message = new FacesMessage(FacesMessage.SEVERITY_INFO,RessourceBundleUtil.getMessage("success"), msgText);
		}else {
			msgText = RessourceBundleUtil.getMessage("errorOccured");
			message = new FacesMessage(FacesMessage.SEVERITY_INFO,RessourceBundleUtil.getMessage("error"),msgText);
		}
		
		FacesUtil.addMessage(null, message);
		this.newPermission = new Permission();
		
		this.init();
	}
	
	
	/**
	 * Save permissions for a given profile
	 * @return void
	 * @param index : Index of the permission in the list
	 * 
	 **/
	public void savePermission(int index) {
		if(index<0 || this.rolePermissions.size()<=index)
			return;
		
		int nbRevoked = 0;
		int nbGranted = 0;
		boolean error = false;
		
		RolePermission rp = this.rolePermissions.get(index);
		
		DualListModel<Permission> rolePermissions = rp.getRolePermissions();
		
		List<Permission> revoked = rolePermissions.getSource();
		
		List<Permission> granted = rolePermissions.getTarget();
		
		for(Permission p : revoked) {
			if(this.permissionDao.deleteRolePermission(rp.getRole(), p)) {
				nbRevoked++;
			}
		}
		
		for(Permission p : granted) {
			
			if(!this.permissionDao.roleHasPermission(rp.getRole(),p)) {
				if(this.permissionDao.createRolePermission(rp.getRole(), p)) {
					nbGranted++;
				}else{
					error = true;
				}
			}
		}
		
		String message = "";
		FacesMessage msg = null;
		if(nbRevoked>0) {
			message+=nbRevoked+" permissions revoked ";
		}
		
		if(nbGranted>0) {
			message+=nbGranted+" permissions granted";
		}
		
		if(!error) {
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Saved",message);
		}else{
			String failMessage = RessourceBundleUtil.getMessage("errorOccured");
			
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"error",failMessage);
		}
		
		FacesUtil.addMessage(null, msg);
		
		this.rolePermissions = this.rolePermissionDAO.findAll();
		
	}
	
	@PostConstruct
	private void init() {
		this.roleList = this.roleDao.findAll();
		this.permissionList = this.permissionDao.findAll();
		this.rolePermissions = this.rolePermissionDAO.findAll();
		this.filterOptions = new ArrayList<SelectItem>();
		this.filterOptions.add(new SelectItem("","Filter status"));
		this.filterOptions.add(new SelectItem("active"));
		this.filterOptions.add(new SelectItem("Inactive"));
	}

	public PermissionBean() {
		// TODO Auto-generated constructor stub
		this.newRole = new Role();
		this.newPermission = new Permission();
		
		this.roleDao = new RoleDAO();
		this.permissionDao = new PermissionDAO();
		this.rolePermissionDAO = new RolePermissionDAO();
	}
	
	public Role getNewRole() {
		return newRole;
	}

	public void setNewRole(Role newRole) {
		this.newRole = newRole;
	}

	public Permission getNewPermission() {
		return newPermission;
	}

	public void setNewPermission(Permission newPermission) {
		this.newPermission = newPermission;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public List<Permission> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<Permission> permissionList) {
		this.permissionList = permissionList;
	}
	
	
	public List<RolePermission> getRolePermissions() {
		return rolePermissions;
	}

	public void setRolePermissions(List<RolePermission> rolePermissions) {
		this.rolePermissions = rolePermissions;
	}
		
	public List<Permission> getFilteredPermissions() {
		return filteredPermissions;
	}

	public void setFilteredPermissions(List<Permission> filteredPermissions) {
		this.filteredPermissions = filteredPermissions;
	}

	public List<SelectItem> getFilterOptions() {
		return filterOptions;
	}

	public void setFilterOptions(List<SelectItem> filterOptions) {
		this.filterOptions = filterOptions;
	}




	private Role newRole;
	private Permission newPermission;
	
	private RoleDAO roleDao;
	private PermissionDAO permissionDao;
	private RolePermissionDAO rolePermissionDAO;
	
	private List<Role> roleList;
	private List<Permission> permissionList;
	private List<Permission> filteredPermissions;
	private List<RolePermission> rolePermissions;
	private List<SelectItem> filterOptions;
	
	
}
