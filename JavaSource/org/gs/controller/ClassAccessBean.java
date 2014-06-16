package org.gs.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.gs.dao.RoleDAO;
import org.gs.dao.UserDAO;
import org.gs.model.Role;
import org.gs.model.User;

@ManagedBean
@ViewScoped
public class ClassAccessBean {

	public List<User> users(int roleId) {
		List<User> userList = new ArrayList<User>();
		UserDAO userDao = new UserDAO();
		userList = userDao.getUsersByRole(roleId);
		
		return userList;
	}
	
	@PostConstruct
	private void init() {
		roleDao = new RoleDAO();
		this.roles = roleDao.findAll();
		System.out.println("Roles " +this.roles.size());
	}
	
	public ClassAccessBean() {
		// TODO Auto-generated constructor stub
		System.out.println("Called");
		this.init();
	}
	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}


	List<Role> roles;
	RoleDAO roleDao;
}
